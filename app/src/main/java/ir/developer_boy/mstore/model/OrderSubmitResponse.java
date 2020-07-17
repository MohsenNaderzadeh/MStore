
package ir.developer_boy.mstore.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class OrderSubmitResponse {

    @SerializedName("order_id")
    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

}
