
package ir.developer_boy.mstore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CartItem {

    @SerializedName("cart_item_id")
    private int cartItemId;
    @Expose
    private int count;
    @Expose
    private Product product;

    private boolean isRemvoing;
    private boolean isChangingCount;

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean isRemvoing() {
        return isRemvoing;
    }

    public void setRemvoing(boolean remvoing) {
        isRemvoing = remvoing;
    }

    public boolean isChangingCount() {
        return isChangingCount;
    }

    public void setChangingCount(boolean changingCount) {
        isChangingCount = changingCount;
    }
}
