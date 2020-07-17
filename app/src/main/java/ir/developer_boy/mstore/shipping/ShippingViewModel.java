package ir.developer_boy.mstore.shipping;

import com.google.gson.JsonObject;

import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;
import ir.developer_boy.mstore.base.BaseViewModel;
import ir.developer_boy.mstore.model.OrderSubmitResponse;

public class ShippingViewModel extends BaseViewModel {

    private static final String EXTRA_KEY_FIRST_NAME = "first_name";
    private static final String EXTRA_KEY_LAST_NAME = "last_name";
    private static final String EXTRA_KEY_POSTAL_CODE = "postal_code";
    private static final String EXTRA_KEY_MOBILE_PHONE_NUMBER = "mobile";
    private static final String EXTRA_KEY_ADRESS = "address";
    private static final String EXTRA_KEY_PAYMENT_METHOD = "payment_method";
    private BehaviorSubject<Boolean> proressbarVisibiltyStatus = BehaviorSubject.create();

    public Single<OrderSubmitResponse> orderSubmit(String firstName,
                                                   String lastName,
                                                   String postalCode,
                                                   String mobilePhoneNumber,
                                                   String address,
                                                   PayMentMethod paymentMethod) {
        proressbarVisibiltyStatus.onNext(true);
        JsonObject orderInfo = new JsonObject();
        orderInfo.addProperty(EXTRA_KEY_FIRST_NAME, firstName);
        orderInfo.addProperty(EXTRA_KEY_LAST_NAME, lastName);
        orderInfo.addProperty(EXTRA_KEY_POSTAL_CODE, postalCode);
        orderInfo.addProperty(EXTRA_KEY_MOBILE_PHONE_NUMBER, mobilePhoneNumber);
        orderInfo.addProperty(EXTRA_KEY_ADRESS, address);
        orderInfo.addProperty(EXTRA_KEY_PAYMENT_METHOD, paymentMethod.getValue());
        return apiService.orderSubmit(orderInfo).doOnEvent((orderSubmitResponse, throwable) -> {
            proressbarVisibiltyStatus.onNext(false);
        });
    }


    public BehaviorSubject<Boolean> getProressbarVisibiltyStatus() {
        return proressbarVisibiltyStatus;
    }
}
