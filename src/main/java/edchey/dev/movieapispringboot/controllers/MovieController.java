package edchey.dev.movieapispringboot.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.MongoException;
import edchey.dev.movieapispringboot.models.Movie;
import edchey.dev.movieapispringboot.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<Map<String, Optional<Movie>>> postAMovie(@RequestBody ObjectNode payload) {
        final Iterable<JsonNode> b = payload.withArray(Movie.BACKDROPS);
        final Iterable<JsonNode> n = payload.withArray(Movie.GENRES);
        final List<String> genres = StreamSupport.stream(n.spliterator(), false)
                .map(JsonNode::asText)
                .toList();
        final List<String> backdrop = StreamSupport.stream(b.spliterator(), false)
                .map(JsonNode::asText)
                .toList();
        Map<String, Optional<Movie>> response = movieService.createMovie(payload.get(Movie.IMDB_ID).asText(), payload.get(Movie.TITLE).asText(), payload.get(Movie.RELEASE_DATE).asText(), payload.get(Movie.TRAILER_LINK).asText(), payload.get(Movie.POSTER).asText(), genres, backdrop);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies(){
        return new ResponseEntity<List<Movie>>(movieService.allMovies(), HttpStatus.OK) ;
    }

    @GetMapping("/{imdbId}")
    public ResponseEntity<Optional<Movie>>getSingleMovie(@PathVariable String imdbId){
        return new ResponseEntity<Optional<Movie>>(movieService.singleMovie(imdbId), HttpStatus.OK);
    }

    @PutMapping("/{imdbId}")
    public ResponseEntity<Movie> putAMovie(@PathVariable String imdbId, @RequestBody ObjectNode payload) {
        System.out.println("payload " + payload);
        final List<String> backdrops = new ArrayList<>();
        final List<String> genres = new ArrayList<>();

        if (payload.has(Movie.BACKDROPS)) {
            final Iterable<JsonNode> b = payload.withArray(Movie.BACKDROPS);
            backdrops.addAll(StreamSupport.stream(b.spliterator(), false)
                    .map(JsonNode::asText)
                    .toList());
        }
        if (payload.has(Movie.GENRES)) {
            final Iterable<JsonNode> n = payload.withArray(Movie.GENRES);
            genres.addAll(StreamSupport.stream(n.spliterator(), false)
                    .map(JsonNode::asText)
                    .toList());
        }
        final String title = payload.has(Movie.TITLE) ? payload.get(Movie.TITLE).asText() : "";
        final String releaseDate = payload.has(Movie.RELEASE_DATE) ? payload.get(Movie.RELEASE_DATE).asText() : "";
        final String trailer = payload.has(Movie.TRAILER_LINK) ? payload.get(Movie.TRAILER_LINK).asText() : "";
        final String poster = payload.has(Movie.POSTER) ? payload.get(Movie.POSTER).asText() : "";

        Movie movie = movieService.updateMovie(imdbId, title, releaseDate, trailer, poster, genres, backdrops);
        return new ResponseEntity<Movie>(movie, HttpStatus.OK);
    }

    @DeleteMapping("/{imdbId}")
    public ResponseEntity<Map<String, String>> deleteAMovies(@PathVariable String imdbId) {
        try {
            return new ResponseEntity<>(movieService.deleteMovie(imdbId), HttpStatus.OK);
        } catch (MongoException me) {
            System.out.println("Unable to delete due to an error: " + me);
        }
        return null;
    }
}
