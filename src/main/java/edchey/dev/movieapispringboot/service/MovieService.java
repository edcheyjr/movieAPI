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
    public Map<String, Optional<Movie>> createMovie(String imdbId, String title, String releaseDate, String trailerLink, String poster, List<String> genres, List<String> backdrops) {
        Map<String, Optional<Movie>> response = new HashMap<>();
        Optional<Movie> movie = movieRepository.findMovieByImdbId(imdbId);
        if (movie.isEmpty()) {
            ObjectId id = new ObjectId();
            List<Review> reviews = new ArrayList<>();
            System.out.println("New Object Id:" + id);
            Movie newMovie = new Movie(id, imdbId, title, releaseDate, trailerLink, poster, genres, backdrops, reviews);
            movieRepository.insert(newMovie);
            response.put(Movie.TABLE_NAME, Optional.of(newMovie));
            return response;
        }
        return response;

    }

    /**
     * List all the movie
     */
    public List<Movie> allMovies(){
        return movieRepository.findAll();
    }

    /**
     * Gets a single movie
     *
     * @param imdbId unique id
     */
    public Optional<Movie> singleMovie(String imdbId) {
        return  movieRepository.findMovieByImdbId(imdbId);
    }

    /**
     * Updates Movie based on the params
     *
     * @param imdbId      unique imdb id
     * @param title       title of the movie
     * @param releaseDate Date release
     * @param poster      poster link
     * @param trailerLink trailer link
     * @param backdrops   backdrop list
     * @param genres      genres list
     * @return Movie Updated movie
     */
    public Movie updateMovie(String imdbId, String title, String releaseDate, String trailerLink, String poster, List<String> genres, List<String> backdrops) {

        Query query = new Query().addCriteria(Criteria.where(Movie.IMDB_ID).is(imdbId));
        Optional<Movie> movie = movieRepository.findMovieByImdbId(imdbId);
        FindAndModifyOptions findAndModifyOptions = new FindAndModifyOptions().returnNew(true).upsert(true);
        Update update = new Update();

        if (!title.isBlank()) {
            update.set(Movie.TITLE, title);
        }
        if (!releaseDate.isBlank()) {
            update.set(Movie.RELEASE_DATE, releaseDate);
        }
        if (!trailerLink.isBlank()) {
            update.set(Movie.TRAILER_LINK, trailerLink);
        }
        if (!poster.isBlank()) {
            update.set(Movie.POSTER, poster);
        }
        if (movie.isPresent() && !genres.isEmpty()) {
            update.set(Movie.GENRES, genres);
        }
        if (movie.isPresent() && !backdrops.isEmpty()) {
            update.set(Movie.BACKDROPS, backdrops);
        }
        return mongoTemplate.findAndModify(query, update, findAndModifyOptions, Movie.class);
    }

    /**
     * Deletes a single movie
     *
     * @param imdbId This the unique imdb id for the movie
     * @return {null or String} if delete returns the id of the movie deleted else return null
     */
    public Map<String, String> deleteMovie(String imdbId) {
        Map<String, String> idMap = new HashMap<>();
//        find the movie
        Query query = new Query().addCriteria(Criteria.where(Movie.IMDB_ID).is(imdbId));
        idMap.put(Movie.IMDB_ID, imdbId); //add and return od the movie deleted
        mongoTemplate.findAndRemove(query, Movie.class, Movie.TABLE_NAME);
//        delete the movie
        return idMap;
    }


}
