package edchey.dev.movieapispringboot.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edchey.dev.movieapispringboot.models.Movie;
import edchey.dev.movieapispringboot.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<Movie> postAMovie(@RequestBody ObjectNode payload) {
        final Iterable<JsonNode> b = payload.withArray(Movie.BACKDROPS);
        final Iterable<JsonNode> n = payload.withArray(Movie.GENRES);
        final List<String> genres = StreamSupport.stream(n.spliterator(), false)
                .map(JsonNode::asText)
                .toList();
        final List<String> backdrops = StreamSupport.stream(b.spliterator(), false)
                .map(JsonNode::asText)
                .toList();
        Movie movie = movieService.createMovie(payload.get(Movie.IMDB_ID).asText(), payload.get(Movie.TITLE).asText(), payload.get(Movie.RELEASE_DATE).asText(), payload.get(Movie.TRAILER_LINK).asText(), payload.get(Movie.POSTER).asText(), genres, backdrops);
        return new ResponseEntity<Movie>(movie, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies(){
        return new ResponseEntity<List<Movie>>(movieService.allMovies(), HttpStatus.OK) ;
    }

    @GetMapping("/{imdbId}")
    public ResponseEntity<Optional<Movie>>getSingleMovie(@PathVariable String imdbId){
        return new ResponseEntity<Optional<Movie>>(movieService.singleMovie(imdbId), HttpStatus.OK);
    }
}
