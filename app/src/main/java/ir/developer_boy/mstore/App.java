package ir.developer_boy.mstore;

import android.app.Application;

import ir.developer_boy.mstore.auth.TokenContainer;
import ir.developer_boy.mstore.auth.UserInfoManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UserInfoManager userInfoManager = new UserInfoManager(this);
        TokenContainer.updateToken(userInfoManager.getToken());
    }
}
