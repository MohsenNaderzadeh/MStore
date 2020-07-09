
package ir.developer_boy.mstore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Product {

    public static final int SORT_LATEST=0;
    public static final int SORT_POPULAR=1;
    public static final int SORT_PRICE_HIGH_TO_LOW=2;
    public static final int SORT_PRICE_LOW_TO_HIGH=3;
    @Expose
    private Long discount;
    @Expose
    private Long id;
    @Expose
    private String image;
    @SerializedName("previous_price")
    private Long previousPrice;
    @Expose
    private Long price;
    @Expose
    private Long status;
    @Expose
    private String title;

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getPreviousPrice() {
        return previousPrice;
    }

    public void setPreviousPrice(Long previousPrice) {
        this.previousPrice = previousPrice;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
