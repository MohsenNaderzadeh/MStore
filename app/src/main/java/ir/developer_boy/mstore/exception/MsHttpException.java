
package ir.developer_boy.mstore.exception;

import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class MsHttpException {

    @Expose
    private String error;
    @Expose
    private String message;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
