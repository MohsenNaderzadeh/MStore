package ir.developer_boy.mstore.cart;

import com.google.gson.JsonObject;

import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;
import ir.developer_boy.mstore.base.BaseViewModel;
import ir.developer_boy.mstore.model.AddToCartResponse;
import ir.developer_boy.mstore.model.CartItem;
import ir.developer_boy.mstore.model.CartModel;
import ir.developer_boy.mstore.model.SuccessResponse;

public class CartViewModel extends BaseViewModel {

    //Final Variables
    private static final String EXTRA_KEY_CART_ITEM_ID = "cart_item_id";
    private static final String EXTRA_KEY_NEW_COUNT = "count";
    private BehaviorSubject<Boolean> progressbarStat = BehaviorSubject.create();

    public Single<CartModel> getCart(boolean mustShowProgressbar) {
        if (mustShowProgressbar)
            progressbarStat.onNext(true);
        return apiService.getCart().doOnEvent((cartModel, throwable) -> {
            progressbarStat.onNext(false);
        });
    }

    public Single<SuccessResponse> removeCartItem(CartItem cartItem) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(EXTRA_KEY_CART_ITEM_ID, cartItem.getCartItemId());
        return apiService.removeCartItem(jsonObject).doOnEvent((successResponse, throwable) -> {
            progressbarStat.onNext(false);
        });
    }

    public Single<AddToCartResponse> ChangeCartItemCount(CartItem cartItem, int newCount) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(EXTRA_KEY_CART_ITEM_ID, cartItem.getCartItemId());
        jsonObject.addProperty(EXTRA_KEY_NEW_COUNT, newCount);
        return apiService.changeCartItemCount(jsonObject).doOnEvent((addToCartResponse, throwable) -> {
            progressbarStat.onNext(false
            );
        });
    }


    public BehaviorSubject<Boolean> getProgressbarStat() {
        return progressbarStat;
    }
}
