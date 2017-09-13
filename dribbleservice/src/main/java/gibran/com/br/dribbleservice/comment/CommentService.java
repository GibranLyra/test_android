package gibran.com.br.dribbleservice.comment;

import java.util.ArrayList;

import gibran.com.br.dribbleservice.model.Comment;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by gibranlyra on 13/09/17.
 */

public interface CommentService {

    @GET("comments")
    Observable<ArrayList<Comment>> getComments();

    @GET("comments")
    Observable<ArrayList<Comment>> getComments(@Query("page") int page, @Query("per_page") int perPage);

    @GET("comments/{id}")
    Observable<Comment> getComment(@Path("id") int id);

}
