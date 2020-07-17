package ir.developer_boy.mstore.checkout;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ir.developer_boy.mstore.R;
import ir.developer_boy.mstore.base.BaseActivity;
import ir.developer_boy.mstore.model.CheckOutResponse;
import ir.developer_boy.mstore.model.api.MsSingleObserver;
import ir.developer_boy.mstore.utils.PriceConverter;

public class CheckOutActvity extends BaseActivity {

    String order_Id;
    private CheckOutViewModel checkOutViewModel;
    private ProgressBar progressBar;
    private TextView CheckOutMessage;
    private TextView PaymentStatus;
    private TextView PayablePrice;

    private Button gotoHomePage;
    private Button OrdersHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_actvity);
        setUpViews();
        observe();

    }

    private void observe() {
        checkOutViewModel.checkOut(Integer.parseInt(order_Id))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MsSingleObserver<CheckOutResponse>(compositeDisposable) {
                    @Override
                    public void onSuccess(CheckOutResponse checkOutResponse) {
                        showData(checkOutResponse);
                    }
                });

        compositeDisposable.add(checkOutViewModel.getProgressbarStatus()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    progressBar.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
                }));
    }

    private void setUpViews() {
        Uri uri = getIntent().getData();
        if (uri != null) {
            order_Id = uri.getQueryParameter("order_id");
        } else {
            order_Id = getIntent().getStringExtra("order_id");
        }

        checkOutViewModel = new CheckOutViewModel();
        progressBar = findViewById(R.id.pb_checkout);
        CheckOutMessage = findViewById(R.id.tv_checkout_message);
        PaymentStatus = findViewById(R.id.tv_checkout_payment_Status);
        PayablePrice = findViewById(R.id.tv_checkout_order_payable_money);
        gotoHomePage = findViewById(R.id.btn_checkout_gotohomepage);
        OrdersHistory = findViewById(R.id.btn_checkout_orderhistory);
    }

    @Override
    public int getCoordinatetorLayoutId() {
        return R.id.coordinate_checkout;
    }

    private void showData(CheckOutResponse checkOutResponse) {
        CheckOutMessage.setText(checkOutResponse.getPurchaseSuccess() ? "خرید با موفقیت انجام شد" : "خرید ناموفق");
        PaymentStatus.setText(checkOutResponse.getPaymentStatus());
        PayablePrice.setText(PriceConverter.convert(checkOutResponse.getPayablePrice()));
        gotoHomePage.setOnClickListener(v -> finish());
    }
}
