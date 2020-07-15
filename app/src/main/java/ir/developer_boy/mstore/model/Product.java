
package ir.developer_boy.mstore.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Product implements Parcelable {
    public static final int SORT_LATEST=0;
    public static final int SORT_POPULAR=1;
    public static final int SORT_PRICE_HIGH_TO_LOW=2;
    public static final int SORT_PRICE_LOW_TO_HIGH=3;

    @Expose
    private Long discount;
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
    @Expose
    private int id;

    public Product() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

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

    public Long getPreviousPrice() {
        return price + discount;
    }

    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    protected Product(Parcel in) {
        this.discount = (Long) in.readValue(Long.class.getClassLoader());
        this.id = (int) in.readValue(Long.class.getClassLoader());
        this.image = in.readString();
        this.previousPrice = (Long) in.readValue(Long.class.getClassLoader());
        this.price = (Long) in.readValue(Long.class.getClassLoader());
        this.status = (Long) in.readValue(Long.class.getClassLoader());
        this.title = in.readString();
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.discount);
        dest.writeValue(this.id);
        dest.writeString(this.image);
        dest.writeValue(this.previousPrice);
        dest.writeValue(this.price);
        dest.writeValue(this.status);
        dest.writeString(this.title);
    }

    public void setId(int id) {
        this.id = id;
    }
}
