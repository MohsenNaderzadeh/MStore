
package ir.developer_boy.mstore.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CheckOutResponse {

    @SerializedName("payable_price")
    private Long payablePrice;
    @SerializedName("payment_status")
    private String paymentStatus;
    @SerializedName("purchase_success")
    private Boolean purchaseSuccess;

    public Long getPayablePrice() {
        return payablePrice;
    }

    public void setPayablePrice(Long payablePrice) {
        this.payablePrice = payablePrice;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Boolean getPurchaseSuccess() {
        return purchaseSuccess;
    }

    public void setPurchaseSuccess(Boolean purchaseSuccess) {
        this.purchaseSuccess = purchaseSuccess;
    }

}
