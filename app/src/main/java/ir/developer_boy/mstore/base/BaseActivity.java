package ir.developer_boy.mstore.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.disposables.CompositeDisposable;
import ir.developer_boy.mstore.auth.AuthenticationActivity;
import ir.developer_boy.mstore.exception.UnAthuorized;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    public abstract int getCoordinatetorLayoutId();
    protected CompositeDisposable compositeDisposable=new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ShowSnackBarMessage(String message){

        Snackbar.make(findViewById(getCoordinatetorLayoutId()), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
        compositeDisposable.clear();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleUnAuthorizedException(UnAthuorized unAthuorized) {
        if (!(this instanceof AuthenticationActivity)) {
            startActivity(new Intent(this, AuthenticationActivity.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
