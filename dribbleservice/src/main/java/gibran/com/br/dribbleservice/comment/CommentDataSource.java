package gibran.com.br.dribbleservice.comment;

import java.util.ArrayList;

import gibran.com.br.dribbleservice.model.Comment;
import io.reactivex.Observable;

/**
 * Created by gibranlyra on 13/09/17.
 */

public interface CommentDataSource {
    Observable<ArrayList<Comment>> getComments();
    Observable<ArrayList<Comment>> getComments(int page);
    Observable<Comment> getComment(int id);
}
