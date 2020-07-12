package ir.developer_boy.mstore.auth;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ir.developer_boy.mstore.R;
import ir.developer_boy.mstore.base.BaseActivity;
import ir.developer_boy.mstore.model.api.MsCompeletableObserver;
import ir.developer_boy.mstore.utils.KeyboardUtil;

public class AuthenticationActivity extends BaseActivity {

    private EditText ed_auth_email;
    private EditText ed_auth_password;
    private Button btn_auth_authentication;
    private TextView tv_auth_change_auth;
    private AuthenticationViewModel authenticationViewModel;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private View Progressbar;

    @Override
    public int getCoordinatetorLayoutId() {
        return R.id.rltv_auth_root;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        setupViews();
        observe();

    }

    private void observe() {
        compositeDisposable
                .add(authenticationViewModel.getIsInLoginMode()
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread()).subscribe(isInLoginMode -> {
                            if (isInLoginMode) {
                                tv_auth_change_auth.setText("ثبت نام");
                                btn_auth_authentication.setText("ورود به حساب کاربری");
                            } else {
                                tv_auth_change_auth.setText("ورود به حساب کاربری");
                                btn_auth_authentication.setText("ثبت نام");
                            }
                        }));
        compositeDisposable.add(authenticationViewModel.getProgressBarVisibility().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(progressbarstatus -> {
                    Progressbar.setVisibility(progressbarstatus ? View.VISIBLE : View.GONE);
                }
        ));
    }


    private void setupViews() {
        ed_auth_email = findViewById(R.id.ed_auth_email);
        ed_auth_password = findViewById(R.id.ed_auth_password);
        btn_auth_authentication = findViewById(R.id.iv_auth_button_authentication);
        tv_auth_change_auth = findViewById(R.id.tv_auth_changeAuthenticationMode);
        authenticationViewModel = new AuthenticationViewModel();
        Progressbar = findViewById(R.id.ll_auth_progressbar);

        ImageView iv_auth_back = findViewById(R.id.iv_auth_back);

        tv_auth_change_auth.setOnClickListener(v -> authenticationViewModel.OnAuthenticationChangeModeBtnClicked());
        btn_auth_authentication.setOnClickListener(v ->
        {
            KeyboardUtil.closeKeyboard(getCurrentFocus());
            authenticationViewModel.authenticate(AuthenticationActivity.this, ed_auth_email.getText().toString(), ed_auth_password.getText().toString())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new MsCompeletableObserver(compositeDisposable) {
                        @Override
                        public void onComplete() {
                            Toast.makeText(AuthenticationActivity.this, "خوش آمدید", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
        });
        iv_auth_back.setOnClickListener(v -> finish());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
