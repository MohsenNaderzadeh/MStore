package ir.developer_boy.mstore.productlist;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ir.developer_boy.mstore.R;
import ir.developer_boy.mstore.model.Product;
import ir.developer_boy.mstore.utils.PriceConverter;

public class ProductFullAdapter extends RecyclerView.Adapter<ProductFullAdapter.ProductViewHolder> {

    private List<Product> productList=new ArrayList<>();
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_full,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bindProduct(productList.get(position));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private ImageView productImage;
        private TextView productTitle;
        private TextView productPrevPrice;
        private TextView productPrice;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.iv_product_Image);
            productTitle=itemView.findViewById(R.id.tv_product_title);
            productPrevPrice=itemView.findViewById(R.id.tv_product_prev_price);
            productPrice=itemView.findViewById(R.id.tv_product_price);
        }

        public void bindProduct(Product product)
        {
            Picasso.get().load(product.getImage()).into(productImage);
            productTitle.setText(product.getTitle());
            productPrevPrice.setText(PriceConverter.convert(product.getPreviousPrice()));
            productPrevPrice.setPaintFlags(productPrevPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            productPrice.setText(PriceConverter.convert(product.getPrice()));
        }
    }
    public void setProductList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }
}
