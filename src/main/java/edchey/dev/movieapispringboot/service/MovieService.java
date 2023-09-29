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

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> allMovies(){
        return movieRepository.findAll();
    }

    public Optional<Movie> singleMovie(String imdbId) {
        return  movieRepository.findMovieByImdbId(imdbId);
    }
}
