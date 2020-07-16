
package ir.developer_boy.mstore.model;

import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class CartItemsCount {

    @Expose
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
