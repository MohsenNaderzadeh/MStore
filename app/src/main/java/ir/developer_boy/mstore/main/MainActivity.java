package ir.developer_boy.mstore.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.developer_boy.mstore.R;
import ir.developer_boy.mstore.base.BaseActivity;
import ir.developer_boy.mstore.model.Product;
import ir.developer_boy.mstore.model.api.MsSingleObserver;

public class MainActivity extends BaseActivity {

    private MainViewModel mainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel=new MainViewModel();
        setUpViews();
        observe();
    }

    private void observe() {
        mainViewModel.latestProducts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MsSingleObserver<List<Product>>(compositeDisposable) {
                    @Override
                    public void onSuccess(List<Product> products) {

                    }
                });
    }

    private void setUpViews() {
    }

    @Override
    public int getCoordinatetorLayoutId() {
        return R.id.main_CoordinatorLayout;
    }



}
