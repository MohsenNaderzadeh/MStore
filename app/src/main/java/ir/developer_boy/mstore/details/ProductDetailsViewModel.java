package ir.developer_boy.mstore.details;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;
import ir.developer_boy.mstore.base.BaseViewModel;
import ir.developer_boy.mstore.model.Comment;

public class ProductDetailsViewModel extends BaseViewModel {

    private BehaviorSubject<Boolean> progressbarStatus = BehaviorSubject.create();

    public Single<List<Comment>> getComments(int productId) {
        progressbarStatus.onNext(true);
        return apiService.getComments(productId).doOnEvent((comments, throwable) -> {
            progressbarStatus.onNext(false);
        });
    }

    public BehaviorSubject<Boolean> getProgressbarStatus() {
        return progressbarStatus;
    }
}
