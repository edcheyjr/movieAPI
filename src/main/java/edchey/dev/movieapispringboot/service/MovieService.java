package edchey.dev.movieapispringboot.service;

import edchey.dev.movieapispringboot.models.Movie;
import edchey.dev.movieapispringboot.models.Review;
import edchey.dev.movieapispringboot.repositories.MovieRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MovieService {

    //TODO: change this to utilize autowire instead
    private final MovieRepository movieRepository;
    private final MongoTemplate mongoTemplate;

    public MovieService(MovieRepository movieRepository, MongoTemplate mongoTemplate) {
        this.movieRepository = movieRepository;
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Creates and inserts a new Movie
     *
     * @param imdbId      unique imdb id
     * @param title       title of the movie
     * @param releaseDate Date release
     * @param poster      poster link
     * @param trailerLink trailer link
     * @param backdrops   backdrop list
     * @param genres      genres list
     * @return Movie
     */
    public Movie createMovie(String imdbId, String title, String releaseDate, String trailerLink, String poster, List<String> genres, List<String> backdrops) {
        ObjectId id = new ObjectId();
        List<String> emptyList = new ArrayList<>();
        List<Review> reviews = new ArrayList<>();
        System.out.println("New Object Id:" + id);
        Movie movie = new Movie(id, imdbId, title, releaseDate, trailerLink, poster, genres, backdrops, reviews);
        return movieRepository.insert(movie);
    }

    /**
     * List all the movie
     */
    public List<Movie> allMovies(){
        return movieRepository.findAll();
    }

    public Optional<Movie> singleMovie(String imdbId) {
        return  movieRepository.findMovieByImdbId(imdbId);
    }
}
