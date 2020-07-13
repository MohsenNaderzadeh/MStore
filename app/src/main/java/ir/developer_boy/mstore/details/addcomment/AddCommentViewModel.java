package ir.developer_boy.mstore.details.addcomment;

import com.google.gson.JsonObject;

import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;
import ir.developer_boy.mstore.base.BaseViewModel;
import ir.developer_boy.mstore.model.Comment;

public class AddCommentViewModel extends BaseViewModel {

    private static final String EXTRA_KEY_COMMENT_TITLE = "title";
    private static final String EXTRA_KEY_COMMENT_CONTENT = "content";
    private static final String EXTRA_KEY_COMMENT_PRODUCT_ID = "product_id";
    private BehaviorSubject<Boolean> ProgressBarStatus = BehaviorSubject.create();

    Single<Comment> submitNewComment(String title, String content, int productId) {
        ProgressBarStatus.onNext(true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(EXTRA_KEY_COMMENT_TITLE, title);
        jsonObject.addProperty(EXTRA_KEY_COMMENT_CONTENT, content);
        jsonObject.addProperty(EXTRA_KEY_COMMENT_PRODUCT_ID, productId);
        return apiService.submitNewComment(jsonObject).doOnEvent((comment, throwable) -> {
            ProgressBarStatus.onNext(false);
        });
    }

    public BehaviorSubject<Boolean> getProgressBarStatus() {
        return ProgressBarStatus;
    }
}
