package gibran.com.br.dribbleservice.comment;

import java.util.ArrayList;

import gibran.com.br.dribbleservice.DribbleApiModule;
import gibran.com.br.dribbleservice.model.Comment;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import timber.log.Timber;

/**
 * Created by gibran.lyra on 13/09/2017.
 */
public class CommentApi implements CommentDataSource {
    private static final int ITEMS_PER_PAGE = 30;
    private static CommentApi instance;
    private final CommentService commentService;

    private CommentApi() {
        Retrofit retrofit = DribbleApiModule.getRetrofit();
        commentService = retrofit.create(CommentService.class);
    }

    public static CommentApi getInstance() {
        if (instance == null) {
            instance = new CommentApi();
        }
        return instance;
    }

    public static void renewInstance() {
        instance = new CommentApi();
    }


    public Observable<ArrayList<Comment>> getComments() {
        return commentService.getComments()
                .doOnError(e -> Timber.e(e, "getComments: %s", e.getMessage()));
    }

    public Observable<ArrayList<Comment>> getComments(int page) {
        return commentService.getComments(page, ITEMS_PER_PAGE)
                .doOnError(e -> Timber.e(e, "getComments: %s", e.getMessage()));
    }

    public Observable<Comment> getComment(int id) {
        return commentService.getComment(id)
                .doOnError(e -> Timber.e(e, "getComment: %s", e.getMessage()));
    }
}