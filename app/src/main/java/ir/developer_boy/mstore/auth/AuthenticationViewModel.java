package ir.developer_boy.mstore.auth;

import android.content.Context;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;
import ir.developer_boy.mstore.base.BaseViewModel;
import ir.developer_boy.mstore.model.Token;

public class AuthenticationViewModel extends BaseViewModel {
    private BehaviorSubject<Boolean> isInLoginMode = BehaviorSubject.create();
    private BehaviorSubject<Boolean> ProgressBarVisibility = BehaviorSubject.create();


    public Completable authenticate(Context context, String username, String password) {
        Single<Token> tokensingle;
        ProgressBarVisibility.onNext(true);
        if (isInLoginMode.getValue() == null || isInLoginMode.getValue()) {
            tokensingle = apiService.getToken("password", 2, "kyj1c9sVcksqGU4scMX7nLDalkjp2WoqQEf8PKAC", username, password);
        } else {
            tokensingle = apiService.registerUser(username, password).flatMap(successResponse ->
                    apiService.getToken("password", 2, "kyj1c9sVcksqGU4scMX7nLDalkjp2WoqQEf8PKAC", username, password)
            );
        }
        UserInfoManager userInfoManager = new UserInfoManager(context);
        return tokensingle.doOnSuccess(
                (token) -> {
                    userInfoManager.saveToken(token.getAccessToken(), token.getExpiresIn().toString(), token.getRefreshToken());
                    TokenContainer.updateToken(token.getAccessToken());
                }
        ).doOnEvent((token, throwable) -> {
            ProgressBarVisibility.onNext(false);
        }).toCompletable();
    }

    public void OnAuthenticationChangeModeBtnClicked() {
        isInLoginMode.onNext(isInLoginMode.getValue() != null && !isInLoginMode.getValue());

    }

    public BehaviorSubject<Boolean> getIsInLoginMode() {
        return isInLoginMode;
    }

    public BehaviorSubject<Boolean> getProgressBarVisibility() {
        return ProgressBarVisibility;
    }
}
