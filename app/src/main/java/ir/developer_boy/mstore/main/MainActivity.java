package ir.developer_boy.mstore.main;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ir.developer_boy.mstore.R;
import ir.developer_boy.mstore.auth.AuthenticationActivity;
import ir.developer_boy.mstore.auth.OnUserAuthenticated;
import ir.developer_boy.mstore.auth.TokenContainer;
import ir.developer_boy.mstore.auth.UserInfoManager;
import ir.developer_boy.mstore.base.BaseActivity;
import ir.developer_boy.mstore.cart.CartActivity;
import ir.developer_boy.mstore.cart.CartItemCountContainer;
import ir.developer_boy.mstore.cart.OnCartItemCountChanged;
import ir.developer_boy.mstore.model.Banner;
import ir.developer_boy.mstore.model.CartItemsCount;
import ir.developer_boy.mstore.model.Product;
import ir.developer_boy.mstore.model.api.MsSingleObserver;
import ir.developer_boy.mstore.orders.OrderHistoryActivity;
import ir.developer_boy.mstore.productlist.ProductListActivity;

public class MainActivity extends BaseActivity {

    private static final int ID_CART = 1;
    private static final int ID_ORDER_HISTORY = 2;
    private static final int ID_AUTH = 3;
    private static final int ID_PROFILE_ITEM = 4;


    private MainViewModel mainViewModel;
    private BannerAdapter bannerAdapter;
    private ProductAdapter latestproductAdapter;
    private ProductAdapter popularproductAdapter;
    private ImageView iv_main_cart;
    private ImageView iv_main_menu;
    private Drawer drawer;
    private UserInfoManager userInfoManager;
    private PrimaryDrawerItem authDrawerItem;
    private ProfileDrawerItem profileDrawerItem;
    private AccountHeader accountHeader;
    private Typeface drawerTypeface;
    private TextView cartItemCountBadge;
    private PrimaryDrawerItem cartDrawerItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();
        observe();
        setUpDrawer();

    }

    public void setUpDrawer() {
        ProfileItemHeaderUpdate();
        accountHeader = new AccountHeaderBuilder()
                .addProfiles(profileDrawerItem)
                .withOnAccountHeaderListener((view, profile, current) -> {
                    if (TokenContainer.getToken() == null) {
                        startActivity(new Intent(MainActivity.this, AuthenticationActivity.class));
                    }
                    return false;
                })
                .withHeaderBackground(ContextCompat.getDrawable(MainActivity.this, R.color.primary))
                .withActivity(this)
                .withTypeface(drawerTypeface)
                .build();

        cartDrawerItem = new PrimaryDrawerItem()
                .withName("سبد خرید")
                .withIdentifier(ID_CART)
                .withTypeface(drawerTypeface)
                .withBadge("0")
                .withBadgeStyle(new BadgeStyle()
                        .withBadgeBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.shape_badge))
                        .withTextColor(ContextCompat.getColor(MainActivity.this, R.color.white)))
                .withSelectable(false)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    if (TokenContainer.getToken() == null) {
                        Toast.makeText(MainActivity.this, "ابتدا وارد حساب کاربری خود شوید", Toast.LENGTH_LONG).show();
                    } else {
                        startActivity(new Intent(MainActivity.this, CartActivity.class));
                    }

                    return false;
                });
        PrimaryDrawerItem OrderHistoryDrawerItem = new PrimaryDrawerItem().withName("سوابق سفارش")
                .withIdentifier(ID_ORDER_HISTORY)
                .withTypeface(drawerTypeface)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (TokenContainer.getToken() == null) {
                            Toast.makeText(MainActivity.this, "ابتدا وارد حساب کاربری خود شوید", Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(MainActivity.this, OrderHistoryActivity.class);
                            startActivity(intent);
                        }

                        return false;
                    }
                })
                .withSelectable(false);
        OnAuthDrawerItemUpdate();
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withSelectedItem(-1)
                .addDrawerItems(cartDrawerItem, OrderHistoryDrawerItem, authDrawerItem)
                .withDrawerGravity(Gravity.RIGHT)
                .withAccountHeader(accountHeader)
                .build();
    }


    private void ProfileItemHeaderUpdate() {
        String name = TokenContainer.getToken() != null ? "" : "کاربر میهمان";
        String email = TokenContainer.getToken() != null ? userInfoManager.getEmail() : "ورود به حساب کاربری یا ثبت نام";
        if (drawer == null) {
            profileDrawerItem = new ProfileDrawerItem()
                    .withName(name)
                    .withEmail(email)
                    .withIdentifier(ID_PROFILE_ITEM)
                    .withTypeface(drawerTypeface)
                    .withIcon(ContextCompat.getDrawable(MainActivity.this, R.drawable.profile_img));
        } else {
            profileDrawerItem
                    .withName(name)
                    .withEmail(email)
                    .withIdentifier(ID_PROFILE_ITEM)
                    .withTypeface(drawerTypeface);
            accountHeader.updateProfile(profileDrawerItem);
        }
    }

    private void OnAuthDrawerItemUpdate() {
        String authDrawerItemTitle = TokenContainer.getToken() != null ? "خروج از حساب کاربری" : "ورود به حساب کاربری";
        if (drawer == null) {
            authDrawerItem = new PrimaryDrawerItem()
                    .withName(authDrawerItemTitle)
                    .withIdentifier(ID_AUTH)
                    .withSelectable(false)
                    .withTypeface(drawerTypeface)
                    .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                        if (TokenContainer.getToken() != null) {
                            userInfoManager.clear();
                            TokenContainer.updateToken(null);
                            OnAuthDrawerItemUpdate();
                            ProfileItemHeaderUpdate();
                            CartItemCountContainer.update(0);
                            EventBus.getDefault().post(new OnCartItemCountChanged(0));
                        } else {
                            startActivity(new Intent(this, AuthenticationActivity.class));
                        }
                        return false;
                    });
        } else {
            authDrawerItem.withName(authDrawerItemTitle)
                    .withTypeface(drawerTypeface);
            drawer.updateItem(authDrawerItem);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        OnAuthDrawerItemUpdate();
        ProfileItemHeaderUpdate();
        updateCartItemCountBadge(CartItemCountContainer.getCount());


    }

    private void observe() {
        mainViewModel.latestProducts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MsSingleObserver<List<Product>>(compositeDisposable) {
                    @Override
                    public void onSuccess(List<Product> products) {
                        latestproductAdapter.setProductList(products);
                    }
                });
        mainViewModel.popularProducts()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MsSingleObserver<List<Product>>(compositeDisposable) {
                    @Override
                    public void onSuccess(List<Product> products) {
                        popularproductAdapter.setProductList(products);
                    }
                });

        mainViewModel.banners()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MsSingleObserver<List<Banner>>(compositeDisposable) {
                    @Override
                    public void onSuccess(List<Banner> banners) {
                        bannerAdapter.setBannerList(banners);
                    }
                });

        getCartItemCounts();

        iv_main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer != null) {
                    drawer.openDrawer();
                }
            }
        });

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnCartItemCountChangedSubscriber(OnCartItemCountChanged oncartItemCountddChanged) {
        updateCartItemCountBadge(OnCartItemCountChanged.getCount());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void OnCartItemCountChangedSubscriber(OnUserAuthenticated onUserAuthenticated) {
        getCartItemCounts();
    }

    public void getCartItemCounts() {
        mainViewModel.getCartItemsCount()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MsSingleObserver<CartItemsCount>(compositeDisposable) {
                    @Override
                    public void onSuccess(CartItemsCount cartItemsCount) {
                        CartItemCountContainer.update(cartItemsCount.getCount());
                        updateCartItemCountBadge(cartItemsCount.getCount());
                        EventBus.getDefault().post(new OnCartItemCountChanged(cartItemsCount.getCount()));
                    }
                });
    }


    private void updateCartItemCountBadge(int count) {
        if (count > 0) {
            cartItemCountBadge.setVisibility(View.VISIBLE);
            cartItemCountBadge.setText(String.valueOf(count));
        } else {
            cartItemCountBadge.setVisibility(View.GONE);

        }
        cartDrawerItem.withBadge(String.valueOf(count));
        drawer.updateItem(cartDrawerItem);
    }

    private void setUpViews() {

        mainViewModel = new MainViewModel();
        userInfoManager = new UserInfoManager(this);
        TextView viewAllLatestProducts=findViewById(R.id.tv_main_viewAllLatestProducts);
        TextView viewAllPopularProducts=findViewById(R.id.tv_main_viewAllPopularProducts);
        iv_main_cart = findViewById(R.id.iv_main_cart);
        drawerTypeface = ResourcesCompat.getFont(this, R.font.regular);
        cartItemCountBadge = findViewById(R.id.tv_main_cartItemCount_badge);
        iv_main_menu = findViewById(R.id.iv_main_menu);

        RecyclerView latestProductsRv=findViewById(R.id.rv_main_latest_products);
        latestProductsRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        latestproductAdapter=new ProductAdapter();
        latestProductsRv.setAdapter(latestproductAdapter);

        RecyclerView popularProductsRv=findViewById(R.id.rv_main_popular_products);
        popularProductsRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        popularproductAdapter=new ProductAdapter();
        popularProductsRv.setAdapter(popularproductAdapter);

        RecyclerView bannersRv=findViewById(R.id.rv_main_banners);
        bannersRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        SnapHelper snapHelper=new PagerSnapHelper();
        snapHelper.attachToRecyclerView(bannersRv);
        bannerAdapter=new BannerAdapter();
        bannersRv.setAdapter(bannerAdapter);

        viewAllLatestProducts.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
            intent.putExtra(ProductListActivity.EXTRA_KEY_SORT_TYPE, 0);
            startActivity(intent);
        });
        viewAllPopularProducts.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
            intent.putExtra(ProductListActivity.EXTRA_KEY_SORT_TYPE, 1);
            startActivity(intent);
        });
        iv_main_cart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        });

    }


    @Override
    public int getCoordinatetorLayoutId() {
        return R.id.main_CoordinatorLayout;
    }


}
