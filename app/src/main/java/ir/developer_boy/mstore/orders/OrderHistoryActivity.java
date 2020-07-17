package ir.developer_boy.mstore.orders;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ir.developer_boy.mstore.R;
import ir.developer_boy.mstore.base.BaseActivity;
import ir.developer_boy.mstore.model.Order;
import ir.developer_boy.mstore.model.api.MsSingleObserver;

public class OrderHistoryActivity extends BaseActivity {


    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ImageView iv_order_history_back;
    private OrderHistoryViewModel orderHistoryViewModel;
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        setUpViews();
        observe();
    }

    private void observe() {
        compositeDisposable.add(orderHistoryViewModel.getProgressbarstatus()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    progressBar.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
                }));
        orderHistoryViewModel.getOrderList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MsSingleObserver<List<Order>>(compositeDisposable) {
                    @Override
                    public void onSuccess(List<Order> orders) {
                        orderAdapter = new OrderAdapter(orders);
                        recyclerView.setLayoutManager(new LinearLayoutManager(OrderHistoryActivity.this, LinearLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(orderAdapter);
                    }
                });
        iv_order_history_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setUpViews() {
        recyclerView = findViewById(R.id.rv_orders_history);
        progressBar = findViewById(R.id.pb_orders_history);
        iv_order_history_back = findViewById(R.id.iv_order_history_back);
        orderHistoryViewModel = new OrderHistoryViewModel();
    }

    @Override
    public int getCoordinatetorLayoutId() {
        return R.id.cordinate_order_history;
    }
}
