package com.viethoa.lazadaretailer.screens.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.viethoa.lazadaretailer.R;
import com.viethoa.lazadaretailer.caches.UserMemoryCache;
import com.viethoa.lazadaretailer.di.ApplicationComponent;
import com.viethoa.lazadaretailer.di.BaseComponent;
import com.viethoa.lazadaretailer.di.loginmodule.DaggerLoginComponent;
import com.viethoa.lazadaretailer.di.loginmodule.LoginComponent;
import com.viethoa.lazadaretailer.di.loginmodule.LoginModule;
import com.viethoa.lazadaretailer.screens.baseviews.BaseSnackBarActivity;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class LoginActivity extends BaseSnackBarActivity implements LoginViewModel.Listener {

    private LoginRouter loginRouter = new LoginRouter();

    @Inject
    LoginViewModel loginViewModel;
    @Inject
    UserMemoryCache userMemoryCache;

    @Bind(R.id.et_email)
    EditText etEmail;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.btn_log_in)
    Button btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remember logged in
        if (userMemoryCache.get() != null) {
            loginRouter.navigateToHomeActivity(this);
            return;
        }

        // Content view
        setContentView(R.layout.activity_login);

        // Init view model
        loginViewModel.initialize(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginViewModel.destroy();
    }

    //----------------------------------------------------------------------------------------------
    // Dependence
    //----------------------------------------------------------------------------------------------

    @Override
    protected void injectModule(ApplicationComponent appComponent) {
        LoginComponent loginComponent = DaggerLoginComponent.builder()
                .applicationComponent(appComponent)
                .loginModule(new LoginModule())
                .build();
        loginComponent.inject(this);
    }

    //----------------------------------------------------------------------------------------------
    // Event
    //----------------------------------------------------------------------------------------------

    @OnTextChanged({R.id.et_email, R.id.et_password})
    protected void onTextChangeListener() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            btnLogIn.setEnabled(false);
        } else {
            btnLogIn.setEnabled(true);
        }
    }

    @OnClick(R.id.btn_log_in)
    protected void btnLoginClicked() {
        showLoadingDialog();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        loginViewModel.login(email, password);
    }

    @Override
    public void onError(Throwable e) {
        dismissLoadingDialog();
        if (e != null) {
            showTopErrorMessage(e.getMessage());
        }
    }

    @Override
    public void onLoginSuccess() {
        dismissLoadingDialog();
        loginRouter.navigateToHomeActivity(this);
    }
}
