package ir.developer_boy.mstore.productlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ir.developer_boy.mstore.R;
import ir.developer_boy.mstore.base.BaseActivity;
import ir.developer_boy.mstore.main.ProductAdapter;
import ir.developer_boy.mstore.model.Product;
import ir.developer_boy.mstore.model.api.MsSingleObserver;

public class ProductListActivity extends BaseActivity implements SortAdapter.SortItemBtnClick {

    private ProductListViewModel viewModel;
    private ProductFullAdapter productAdapter;
    private int SortType;
    public static final String EXTRA_KEY_SORT_TYPE="SORT_TYPE";
    private SortAdapter sortAdapter;
    ImageView backbutton;
    private MsSingleObserver<List<Product>> productobserver=new MsSingleObserver<List<Product>>(compositeDisposable) {
        @Override
        public void onSuccess(List<Product> products) {
            productAdapter.setProductList(products);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        setUpViews();
        observe();
    }

    private void observe() {
        viewModel.products(SortType)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productobserver);
    }

    private void setUpViews() {
        viewModel =new ProductListViewModel();
        SortType=getIntent().getIntExtra(EXTRA_KEY_SORT_TYPE,0);
        productAdapter=new ProductFullAdapter();
        sortAdapter=new SortAdapter(SortType,this);
        backbutton=findViewById(R.id.iv_product_back);

        RecyclerView productRecyclerview=findViewById(R.id.rv_list_product);
        productRecyclerview.setLayoutManager(new GridLayoutManager(this,2));
        productRecyclerview.setAdapter(productAdapter);

        RecyclerView sortRecyclerview=findViewById(R.id.rv_list_sort);
        sortRecyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true));
        sortRecyclerview.setAdapter(sortAdapter);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public int getCoordinatetorLayoutId() {
        return R.id.cordinate_list;
    }

    @Override
    public void OnSortBtnClicked(int selectedsorttype) {
        SortType=selectedsorttype;
        observe();
    }
}
