
package ir.developer_boy.mstore.model;

import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class SuccessResponse {

    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
