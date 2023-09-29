package edchey.dev.movieapispringboot.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = Movie.TABLE_NAME)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    //    TABLE DEFINITIONStt0111161
    public static final String TABLE_NAME = "movies";
    public static final String ID = "id";
    public static final String IMDB_ID = "imdbId";
    public static final String TITLE = "title";
    public static final String RELEASE_DATE = "releaseDate";
    public static final String TRAILER_LINK = "trailerLink";
    public static final String POSTER = "poster";
    public static final String GENRES = "genres";
    public static final String BACKDROPS = "backdrops";
    public static final String REVIEWS_IDS = "reviewIds";

    //  Table blueprint
    @Id
    private ObjectId id;
    private String imdbId;
    private String title;
    private String releaseDate;
    private String trailerLink;
    private String poster;
    private List<String> genres;
    private List<String> backdrops;
    @DocumentReference
    private List<Review> reviewIds;
}

