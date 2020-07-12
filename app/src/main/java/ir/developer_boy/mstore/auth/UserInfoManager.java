package ir.developer_boy.mstore.auth;

import android.content.Context;
import android.content.SharedPreferences;

import io.reactivex.annotations.Nullable;

public class UserInfoManager {
    private static final String EXTRA_KEY_TOKEN = "token";
    private static final String EXTRA_KEY_TOKEN_EXPIRE_TIME = "token_expire_time";
    private static final String EXTRA_KEY_REFRESH_TOKEN = "refresh_token";
    private static final String EXTRA_KEY_EMAIL = "email";
    private SharedPreferences sharedPreferences;


    public UserInfoManager(Context context) {
        sharedPreferences = context.getSharedPreferences("User_Info", Context.MODE_PRIVATE);
    }

    public void saveToken(String token, String tokenexpiretime, String refreshtoken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EXTRA_KEY_TOKEN, token);
        editor.putString(EXTRA_KEY_TOKEN_EXPIRE_TIME, tokenexpiretime);
        editor.putString(EXTRA_KEY_REFRESH_TOKEN, refreshtoken);
        editor.apply();
    }

    @Nullable
    public String getToken() {
        return sharedPreferences.getString(EXTRA_KEY_TOKEN, null);
    }

    public String getTokenExpireTime() {
        return sharedPreferences.getString(EXTRA_KEY_TOKEN_EXPIRE_TIME, "");
    }

    @Nullable
    public String getRefreshToken() {
        return sharedPreferences.getString(EXTRA_KEY_REFRESH_TOKEN, null);
    }

    @Nullable
    public String getEmail() {
        return sharedPreferences.getString(EXTRA_KEY_EMAIL, null);
    }
}
