package ir.developer_boy.mstore.details;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ir.developer_boy.mstore.R;
import ir.developer_boy.mstore.base.BaseActivity;
import ir.developer_boy.mstore.details.addcomment.AddCommentDialog;
import ir.developer_boy.mstore.model.Comment;
import ir.developer_boy.mstore.model.Product;
import ir.developer_boy.mstore.model.api.MsSingleObserver;
import ir.developer_boy.mstore.utils.PriceConverter;

public class ProductDetails extends BaseActivity {

    public static final String EXTRA_PRODUCT_KEY = "product_key";
    private static final String TAG = "ProductDetails";
    private ImageView ivBack;
    private TextView productTitleToolbar;
    private ImageView productImage;
    private TextView productTitle;
    private TextView productPrevPrice;
    private TextView productPrice;
    private Button productAddToCartBtn;
    private TextView addCommentTv;
    private RecyclerView commentsList;
    private ProgressBar progressBar;
    private Product product;
    private CommentsAdapter commentsAdapter;

    private ProductDetailsViewModel productDetailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        product = getIntent().getParcelableExtra(EXTRA_PRODUCT_KEY);
        if (product == null) {
            finish();
        }
        setUpViews();
        observe();
    }

    private void observe() {
        productDetailsViewModel.getComments(product.getId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MsSingleObserver<List<Comment>>(compositeDisposable) {
                    @Override
                    public void onSuccess(List<Comment> comments) {
                        commentsAdapter.setCommentList(comments);

                    }
                });

        compositeDisposable.add(productDetailsViewModel.getProgressbarStatus()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isBoolean -> {
                    progressBar.setVisibility((isBoolean ? View.VISIBLE : View.GONE));
                }));
        addCommentTv.setOnClickListener(v -> {
            AddCommentDialog addCommentDialog = AddCommentDialog.newInstance(product.getId());
            addCommentDialog.show(getSupportFragmentManager(), null);
        });
    }


    private void setUpViews() {
        ivBack = findViewById(R.id.iv_details_back);
        productTitleToolbar = findViewById(R.id.tv_details_product_title_toolbar);
        productImage = findViewById(R.id.iv_details_product_Image);
        productTitle = findViewById(R.id.tv_details_product_title);
        productPrevPrice = findViewById(R.id.tv_details_prevprice);
        productPrice = findViewById(R.id.tv_details_price);
        productAddToCartBtn = findViewById(R.id.btn_details_add_to_cart);
        addCommentTv = findViewById(R.id.tv_details_add_comment);
        commentsList = findViewById(R.id.rv_details_comments_list);
        productDetailsViewModel = new ProductDetailsViewModel();
        progressBar = findViewById(R.id.pb_details_comment);
        commentsAdapter = new CommentsAdapter();

        productTitleToolbar.setText(product.getTitle());
        Picasso.get().load(product.getImage()).into(productImage);
        productTitle.setText(product.getTitle());
        productPrevPrice.setText(PriceConverter.convert(product.getPreviousPrice()));
        productPrevPrice.setPaintFlags(productPrevPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        productPrice.setText(PriceConverter.convert(product.getPrice()));

        commentsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        commentsList.setAdapter(commentsAdapter);

        ivBack.setOnClickListener(v -> finish());

    }

    @Override
    public int getCoordinatetorLayoutId() {
        return R.id.coordinate_details;
    }


}
