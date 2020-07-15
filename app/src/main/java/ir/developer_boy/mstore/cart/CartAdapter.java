package ir.developer_boy.mstore.cart;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ir.developer_boy.mstore.R;
import ir.developer_boy.mstore.model.CartItem;
import ir.developer_boy.mstore.model.CartModel;
import ir.developer_boy.mstore.model.Product;
import ir.developer_boy.mstore.utils.PriceConverter;
import ir.developer_boy.mstore.views.CartItemCountChanger;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int CART_ITEM_VIEW_HOLDER = 0;
    private static final int PURCHASE_ITEM_VIEW_HOLDER = 1;

    private CartModel cartModel;
    private CartItemEventListener OnCartItemEventListener;

    public CartAdapter(CartModel cartModel, CartItemEventListener onCartItemEventListener) {
        this.cartModel = cartModel;
        OnCartItemEventListener = onCartItemEventListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == CART_ITEM_VIEW_HOLDER) {
            return new CartItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false));

        } else {
            return new PurhchaseDetailsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_purchases_details, parent, false));

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof CartItemViewHolder) {
            ((CartItemViewHolder) holder).bindCartItem(cartModel.getCartItems().get(position));
        } else if (holder instanceof PurhchaseDetailsViewHolder) {
            ((PurhchaseDetailsViewHolder) holder).bindDetails(cartModel.getTotalPrice(), cartModel.getShippingCost(), cartModel.getPayablePrice());
        }


    }

    @Override
    public int getItemViewType(int position) {

        if (position < cartModel.getCartItems().size()) {
            return CART_ITEM_VIEW_HOLDER;
        }
        return PURCHASE_ITEM_VIEW_HOLDER;
    }

    @Override
    public int getItemCount() {
        return cartModel.getCartItems().size() + 1;
    }


    public void updateCartModel(CartModel cartModel) {
        this.cartModel = cartModel;
        notifyDataSetChanged();

    }

    public void removeCartItem(CartItem cartItem) {
        int index = cartModel.getCartItems().indexOf(cartItem);
        cartModel.getCartItems().remove(cartItem);
        notifyItemRemoved(index);
    }

    public void notifyItemChanged(CartItem cartItem) {
        int index = cartModel.getCartItems().indexOf(cartItem);
        notifyItemChanged(index);
    }


    public interface CartItemEventListener {
        void OnProductClicked(Product product);

        void OnRemoveBtnClicked(CartItem cartItem);

        void OnCartItemCountChange(CartItem cartItem, int requestCount);
    }

    public class CartItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productTitle;
        private CartItemCountChanger cartItemCountChanger;
        private TextView productPrevPrice;
        private TextView productPrice;
        private TextView DeleteCartItem;
        private ProgressBar progressbar_cart_item_remove;
        private ProgressBar cart_item_count_changer_progressbar;

        public CartItemViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.iv_cart_item_product_image);
            productTitle = itemView.findViewById(R.id.tv_cart_product_title);
            cartItemCountChanger = itemView.findViewById(R.id.cart_item_count_changer_cartItem);
            productPrevPrice = itemView.findViewById(R.id.tv_cart_item_prev_price);
            productPrice = itemView.findViewById(R.id.tv_cart_item_product_price);
            DeleteCartItem = itemView.findViewById(R.id.tv_cart_item_remove);
            progressbar_cart_item_remove = itemView.findViewById(R.id.progressbar_cart_item_remove);
            cart_item_count_changer_progressbar = itemView.findViewById(R.id.cart_item_count_changer_progressbar);
        }

        public void bindCartItem(CartItem cartItem) {
            String productImageurl = cartItem.getProduct().getImage();
            if (productImageurl.startsWith("http://expertdevelopers.ir/storage/")) {
                productImageurl = productImageurl.replace("http://expertdevelopers.ir/storage/", "");
            }
            Picasso.get().load(productImageurl).into(productImage);
            productTitle.setText(cartItem.getProduct().getTitle());
            cartItemCountChanger.setCount(cartItem.getCount());
            if (cartItem.getProduct().getPreviousPrice() != null)
                productPrevPrice.setText(PriceConverter.convert(cartItem.getProduct().getPreviousPrice()));
            productPrevPrice.setPaintFlags(productPrevPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            productPrice.setText(PriceConverter.convert(cartItem.getProduct().getPrice()));
            DeleteCartItem.setOnClickListener(v -> {
                cartItem.setRemvoing(true);
                OnCartItemEventListener.OnRemoveBtnClicked(cartItem);
                notifyItemChanged(getAdapterPosition());
            });
            if (cartItem.isRemvoing()) {
                DeleteCartItem.setVisibility(View.INVISIBLE);
                progressbar_cart_item_remove.setVisibility(View.VISIBLE);
                DeleteCartItem.setEnabled(false);
            } else {
                DeleteCartItem.setVisibility(View.VISIBLE);
                progressbar_cart_item_remove.setVisibility(View.GONE);
                DeleteCartItem.setEnabled(true);
            }
            cartItemCountChanger.setOnCartItemCountChangedListener(count -> {
                OnCartItemEventListener.OnCartItemCountChange(cartItem, count);
                cartItem.setChangingCount(true);
                notifyItemChanged(getAdapterPosition());
            });
            if (cartItem.isChangingCount()) {
                cartItemCountChanger.setVisibility(View.INVISIBLE);
                cart_item_count_changer_progressbar.setVisibility(View.VISIBLE);
            } else {
                cartItemCountChanger.setVisibility(View.VISIBLE);
                cart_item_count_changer_progressbar.setVisibility(View.GONE);
            }

            productImage.setOnClickListener(v -> OnCartItemEventListener.OnProductClicked(cartItem.getProduct()));
        }

    }

    public class PurhchaseDetailsViewHolder extends RecyclerView.ViewHolder {
        private TextView TotalPrice;
        private TextView ShippingCost;
        private TextView payableCost;

        public PurhchaseDetailsViewHolder(View itemView) {
            super(itemView);
            TotalPrice = itemView.findViewById(R.id.tv_purchase_details_totalPrice);
            ShippingCost = itemView.findViewById(R.id.tv_purchase_details_shippingCost);
            payableCost = itemView.findViewById(R.id.tv_purchase_details_payable);
        }

        public void bindDetails(long totalPrice, long shippingcost, long PayableCost) {
            TotalPrice.setText(PriceConverter.convert(totalPrice));
            if (shippingcost > 0) {
                ShippingCost.setText(PriceConverter.convert(shippingcost));
            } else {
                ShippingCost.setText("رایگان");
            }
            payableCost.setText(PriceConverter.convert(PayableCost));


        }
    }
}
