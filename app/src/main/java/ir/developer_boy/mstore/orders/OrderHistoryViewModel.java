package ir.developer_boy.mstore.orders;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;
import ir.developer_boy.mstore.base.BaseViewModel;
import ir.developer_boy.mstore.model.Order;

public class OrderHistoryViewModel extends BaseViewModel {
    private BehaviorSubject<Boolean> progressbarstatus = BehaviorSubject.create();

    public Single<List<Order>> getOrderList() {
        progressbarstatus.onNext(true);
        return apiService.getOrderList().doOnEvent((orders, throwable) -> {
            progressbarstatus.onNext(false);
        });
    }

    public BehaviorSubject<Boolean> getProgressbarstatus() {
        return progressbarstatus;
    }
}
