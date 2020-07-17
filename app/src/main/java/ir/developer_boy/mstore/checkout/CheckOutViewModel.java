package ir.developer_boy.mstore.checkout;

import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;
import ir.developer_boy.mstore.base.BaseViewModel;
import ir.developer_boy.mstore.model.CheckOutResponse;

public class CheckOutViewModel extends BaseViewModel {
    private BehaviorSubject<Boolean> progressbarStatus = BehaviorSubject.create();

    public BehaviorSubject<Boolean> getProgressbarStatus() {
        return progressbarStatus;
    }

    public Single<CheckOutResponse> checkOut(int orderId) {
        progressbarStatus.onNext(true);
        return apiService.getCheckout(orderId).doOnEvent((checkOutResponse, throwable) -> {
            progressbarStatus.onNext(false);
        });
    }

}
