
package ir.developer_boy.mstore.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Banner {
//vf
    @Expose
    private Long id;
    @Expose
    private String image;
    @SerializedName("link_type")
    private Long linkType;
    @SerializedName("link_value")
    private String linkValue;

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

    public Long getLinkType() {
        return linkType;
    }

    public void setLinkType(Long linkType) {
        this.linkType = linkType;
    }

    public String getLinkValue() {
        return linkValue;
    }

    public void setLinkValue(String linkValue) {
        this.linkValue = linkValue;
    }

}
