package edchey.dev.movieapispringboot.service;

import edchey.dev.movieapispringboot.models.Movie;
import edchey.dev.movieapispringboot.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
