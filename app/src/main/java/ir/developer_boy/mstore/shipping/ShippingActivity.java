package ir.developer_boy.mstore.shipping;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ir.developer_boy.mstore.R;
import ir.developer_boy.mstore.base.BaseActivity;
import ir.developer_boy.mstore.cart.CartAdapter;
import ir.developer_boy.mstore.checkout.CheckOutActvity;
import ir.developer_boy.mstore.model.OrderSubmitResponse;
import ir.developer_boy.mstore.model.api.MsSingleObserver;

public class ShippingActivity extends BaseActivity {


    public static final String EXTRA_KEY_TOTAL_PRICE = "totalprice";
    public static final String EXTRA_KEY_PAYABLE_PRICE = "payableprice";
    public static final String EXTRA_KEY_SHIPPING_COST = "shippingcost";
    private EditText ed_shipping_firstname;
    private EditText ed_shipping_lastname;
    private EditText ed_shipping_postalCode;
    private EditText ed_shipping_phoneNumber;
    private EditText ed_shipping_address;
    private Button btn_shipping_OnlinePayment;
    private Button btn_shipping_CashOnDelivery;
    private View iv_shipping_back;
    private View paymentButtonContainer;
    private ShippingViewModel viewModel;
    private ProgressBar progressbar;
    private long totalprice;
    private long payableprice;
    private long shippingCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
        setUpViews();
        observe();
    }

    private void observe() {
        compositeDisposable.add(viewModel.getProressbarVisibiltyStatus().subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    progressbar.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
                    paymentButtonContainer.setVisibility(aBoolean ? View.GONE : View.VISIBLE);
                }));

    }

    private void setUpViews() {
        ed_shipping_firstname = findViewById(R.id.ed_shipping_firstname);
        ed_shipping_lastname = findViewById(R.id.ed_shipping_lastname);
        ed_shipping_postalCode = findViewById(R.id.ed_shipping_postalCode);
        ed_shipping_phoneNumber = findViewById(R.id.ed_shipping_phoneNumber);
        ed_shipping_address = findViewById(R.id.ed_shipping_address);
        btn_shipping_OnlinePayment = findViewById(R.id.btn_shipping_OnlinePayment);
        btn_shipping_CashOnDelivery = findViewById(R.id.btn_shipping_CashOnDelivery);
        paymentButtonContainer = findViewById(R.id.ll_shipping_paymentsmethodsbtncontainers);
        progressbar = findViewById(R.id.pb_shipping_ordersubmit);
        iv_shipping_back = findViewById(R.id.iv_shipping_back);

        totalprice = getIntent().getLongExtra(EXTRA_KEY_TOTAL_PRICE, -1);
        payableprice = getIntent().getLongExtra(EXTRA_KEY_PAYABLE_PRICE, -1);
        shippingCost = getIntent().getLongExtra(EXTRA_KEY_SHIPPING_COST, -1);

        if (totalprice == -1 ||
                payableprice == -1 ||
                shippingCost == -1) {
            finish();
        } else {
            View view = findViewById(R.id.purchase_details_layout);
            CartAdapter.PurhchaseDetailsViewHolder purhchaseDetailsViewHolder = new CartAdapter.PurhchaseDetailsViewHolder(view);
            purhchaseDetailsViewHolder.bindDetails(totalprice, shippingCost, payableprice);
        }

        iv_shipping_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btn_shipping_OnlinePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderSubmit(PayMentMethod.ONLINE_METHOD);
            }
        });
        btn_shipping_CashOnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderSubmit(PayMentMethod.CASH_ON_DELIVERY);
            }
        });
        viewModel = new ShippingViewModel();


    }

    @Override
    public int getCoordinatetorLayoutId() {
        return R.id.cordinate_shipping;
    }


    private void OrderSubmit(PayMentMethod payMentMethod) {

        if (ed_shipping_firstname.getText().length() > 0 && ed_shipping_lastname.getText().length() > 0 && ed_shipping_postalCode.getText().length() > 0
                && ed_shipping_phoneNumber.getText().length() > 0 && ed_shipping_address.getText().length() > 0) {
            viewModel.orderSubmit(
                    ed_shipping_firstname.getText().toString(),
                    ed_shipping_lastname.getText().toString(),
                    ed_shipping_postalCode.getText().toString(),
                    ed_shipping_phoneNumber.getText().toString(),
                    ed_shipping_address.getText().toString(), payMentMethod)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new MsSingleObserver<OrderSubmitResponse>(compositeDisposable) {
                        @Override
                        public void onSuccess(OrderSubmitResponse orderSubmitResponse) {
                            switch (payMentMethod) {
                                case ONLINE_METHOD:
                                    Intent backgateway = new Intent(Intent.ACTION_VIEW, Uri.parse("http://expertdevelopers.ir/payment?order_id=" + orderSubmitResponse.getOrderId()));
                                    startActivity(Intent.createChooser(backgateway, "choose your browser..."));
                                    break;
                                case CASH_ON_DELIVERY:
                                    startActivity(new Intent(ShippingActivity.this, CheckOutActvity.class));
                                    break;
                            }
                        }
                    });
        } else {
            Toast.makeText(ShippingActivity.this, "لطفا تمامی فیلدها را پر کنید", Toast.LENGTH_LONG).show();
        }

    }
}
