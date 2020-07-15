package ir.developer_boy.mstore.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ir.developer_boy.mstore.R;
import ir.developer_boy.mstore.base.BaseActivity;
import ir.developer_boy.mstore.cart.CartActivity;
import ir.developer_boy.mstore.model.Banner;
import ir.developer_boy.mstore.model.Product;
import ir.developer_boy.mstore.model.api.MsSingleObserver;
import ir.developer_boy.mstore.productlist.ProductListActivity;

public class MainActivity extends BaseActivity {

    private MainViewModel mainViewModel;
    private BannerAdapter bannerAdapter;
    private ProductAdapter latestproductAdapter;
    private ProductAdapter popularproductAdapter;
    private ImageView iv_main_cart;

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

    }

    private void setUpViews() {

        TextView viewAllLatestProducts=findViewById(R.id.tv_main_viewAllLatestProducts);
        TextView viewAllPopularProducts=findViewById(R.id.tv_main_viewAllPopularProducts);
        ImageView iv_main_cart = findViewById(R.id.iv_main_cart);
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

        viewAllLatestProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ProductListActivity.class);
                intent.putExtra(ProductListActivity.EXTRA_KEY_SORT_TYPE,0);
                startActivity(intent);
            }
        });
        viewAllPopularProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ProductListActivity.class);
                intent.putExtra(ProductListActivity.EXTRA_KEY_SORT_TYPE,1);
                startActivity(intent);
            }
        });
        iv_main_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public int getCoordinatetorLayoutId() {
        return R.id.main_CoordinatorLayout;
    }


}
