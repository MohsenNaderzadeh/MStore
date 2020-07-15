package ir.developer_boy.mstore.details;

import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;
import ir.developer_boy.mstore.base.BaseViewModel;
import ir.developer_boy.mstore.model.AddToCartResponse;
import ir.developer_boy.mstore.model.Comment;

public class ProductDetailsViewModel extends BaseViewModel {

    private static final String EXTRA_KEY_PRODUCT_ID = "product_id";

    private BehaviorSubject<Boolean> progressbarStatus = BehaviorSubject.create();
    private BehaviorSubject<Boolean> progressbarAddTocart = BehaviorSubject.create();
    public Single<List<Comment>> getComments(int productId) {
        progressbarStatus.onNext(true);
        return apiService.getComments(productId).doOnEvent((comments, throwable) -> {
            progressbarStatus.onNext(false);
        });
    }

    public Single<AddToCartResponse> AddProductToCart(int productId) {
        progressbarAddTocart.onNext(true);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(EXTRA_KEY_PRODUCT_ID, productId);
        return apiService.addToCart(jsonObject).doOnEvent((addToCartResponse, throwable) -> {
            progressbarAddTocart.onNext(false);
        });
    }

    public BehaviorSubject<Boolean> getProgressbarStatus() {
        return progressbarStatus;
    }

    public BehaviorSubject<Boolean> getProgressbarAddTocart() {
        return progressbarAddTocart;
    }
}
