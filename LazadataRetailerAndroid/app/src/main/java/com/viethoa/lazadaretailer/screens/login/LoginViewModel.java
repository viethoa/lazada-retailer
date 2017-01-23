package com.viethoa.lazadaretailer.screens.login;

import com.viethoa.lazadaretailer.screens.baseviews.BaseViewModel;

/**
 * Created by VietHoa on 22/01/2017.
 */

public interface LoginViewModel {

    interface Listener extends BaseViewModel.Listener {
        void onError(Throwable e);

        void showLoading();

        void onLoginSuccess();
    }

    void initialize(Listener listener);

    void destroy();

    void login(String email, String password);
}
