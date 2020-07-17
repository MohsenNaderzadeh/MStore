package ir.developer_boy.mstore.cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ir.developer_boy.mstore.R;
import ir.developer_boy.mstore.base.BaseActivity;
import ir.developer_boy.mstore.details.ProductDetails;
import ir.developer_boy.mstore.model.AddToCartResponse;
import ir.developer_boy.mstore.model.CartItem;
import ir.developer_boy.mstore.model.CartModel;
import ir.developer_boy.mstore.model.Product;
import ir.developer_boy.mstore.model.SuccessResponse;
import ir.developer_boy.mstore.model.api.MsSingleObserver;
import ir.developer_boy.mstore.shipping.ShippingActivity;
import ir.developer_boy.mstore.utils.PriceConverter;

public class CartActivity extends BaseActivity implements CartAdapter.CartItemEventListener {
    private CartViewModel cartViewModel;
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private View ProgressBar;
    private View EmptyState;
    private TextView tv_cart_payable_cart;
    private TextView gotoPurhchaseDetails;
    private TextView tv_cart_cartItemCount_badge;
    public static CartModel cartModel;
    private Button btn_cart_order_submit;
    private View iv_cart_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setUpViews();
        getCartItems(true);
        observe();
    }

    private void getCartItems(boolean mustshowprogressbar) {

        cartViewModel.getCart(mustshowprogressbar)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MsSingleObserver<CartModel>(compositeDisposable) {
                    @Override
                    public void onSuccess(CartModel cartModel) {
                        CartActivity.cartModel = cartModel;
                        CartItemCountContainer.update(cartModel.getCartItems().size());
                        EventBus.getDefault().post(new OnCartItemCountChanged(cartModel.getCartItems().size()));
                        if (cartModel.getCartItems().isEmpty()) {
                            EmptyState.setVisibility(View.VISIBLE);
                        } else {
                            btn_cart_order_submit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent shippingActivity = new Intent(CartActivity.this, ShippingActivity.class);
                                    shippingActivity.putExtra(ShippingActivity.EXTRA_KEY_TOTAL_PRICE, cartModel.getTotalPrice());
                                    shippingActivity.putExtra(ShippingActivity.EXTRA_KEY_SHIPPING_COST, cartModel.getShippingCost());
                                    shippingActivity.putExtra(ShippingActivity.EXTRA_KEY_PAYABLE_PRICE, cartModel.getPayablePrice());
                                    startActivity(shippingActivity);
                                    finish();
                                }
                            });
                            EmptyState.setVisibility(View.GONE);
                            tv_cart_payable_cart.setText(PriceConverter.convert(cartModel.getPayablePrice()));
                        }
                        if (cartAdapter == null) {
                            cartAdapter = new CartAdapter(cartModel, CartActivity.this);
                            recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false));
                            recyclerView.setAdapter(cartAdapter);
                        }
                        cartAdapter.updateCartModel(cartModel);
                    }
                });
    }

    public void observe() {
        compositeDisposable.add(cartViewModel.getProgressbarStat()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    ProgressBar.setVisibility((aBoolean ? View.VISIBLE : View.GONE));
                }));

        gotoPurhchaseDetails.setOnClickListener(v -> recyclerView.smoothScrollToPosition(cartAdapter.getItemCount()));
    }

    private void setUpViews() {
        recyclerView = findViewById(R.id.rc_cart_items);
        ProgressBar = findViewById(R.id.frame_cart_progressbar);
        EmptyState = findViewById(R.id.frame_cart_empty_State);
        cartViewModel = new CartViewModel();
        tv_cart_payable_cart = findViewById(R.id.tv_cart_payable_cart);
        gotoPurhchaseDetails = findViewById(R.id.tv_purchase_details);
        tv_cart_cartItemCount_badge = findViewById(R.id.tv_cart_cartItemCount_badge);
        btn_cart_order_submit = findViewById(R.id.btn_cart_order_submit);
        iv_cart_back = findViewById(R.id.iv_cart_back);


        iv_cart_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public int getCoordinatetorLayoutId() {
        return R.id.cordinate_cart;
    }

    @Override
    public void OnProductClicked(Product product) {
        Intent intent = new Intent();
        intent.putExtra(ProductDetails.EXTRA_PRODUCT_KEY, product);
        startActivity(intent);
    }

    @Override
    public void OnRemoveBtnClicked(CartItem cartItem) {
        cartViewModel.removeCartItem(cartItem)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MsSingleObserver<SuccessResponse>(compositeDisposable) {
                    @Override
                    public void onSuccess(SuccessResponse successResponse) {
                        cartAdapter.removeCartItem(cartItem);
                        getCartItems(false);
                        int count = CartItemCountContainer.getCount();
                        count -= 1;
                        EventBus.getDefault().post(new OnCartItemCountChanged(count));

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        cartItem.setRemvoing(false);
                        cartAdapter.notifyItemChanged(cartItem);
                    }
                });
    }


    @Override
    public void OnCartItemCountChange(CartItem cartItem, int requestCount) {
        cartViewModel.ChangeCartItemCount(cartItem, requestCount)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MsSingleObserver<AddToCartResponse>(compositeDisposable) {
                    @Override
                    public void onSuccess(AddToCartResponse addToCartResponse) {
                        cartItem.setCount(requestCount);
                        cartItem.setChangingCount(false);
                        cartAdapter.notifyItemChanged(cartItem);
                        getCartItems(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        cartItem.setChangingCount(false);
                        cartAdapter.notifyItemChanged(cartItem);
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnCartItemCountChangedSubscriber(OnCartItemCountChanged oncartItemCountddChanged) {
        updateCartItemCountBadge(OnCartItemCountChanged.getCount());
    }


    private void updateCartItemCountBadge(int count) {
        if (count > 0) {
            tv_cart_cartItemCount_badge.setVisibility(View.VISIBLE);
            tv_cart_cartItemCount_badge.setText(String.valueOf(count));
        } else {
            tv_cart_cartItemCount_badge.setVisibility(View.GONE);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartItemCountBadge(CartItemCountContainer.getCount());
    }
}
