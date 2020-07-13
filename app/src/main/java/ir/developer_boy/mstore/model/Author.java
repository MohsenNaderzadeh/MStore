
package ir.developer_boy.mstore.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Author {

    @SerializedName("email")
    private String mEmail;

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

}
