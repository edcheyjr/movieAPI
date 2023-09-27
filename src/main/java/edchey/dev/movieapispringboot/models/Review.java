package edchey.dev.movieapispringboot.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    private ObjectId id;
    private String body;
    // takes only body as the constructor
    /**
     * This constructor takes only the body
     * @param body this is the review itself
     *
     */
    public Review(String body) {
        this.body = body;
    }
}
