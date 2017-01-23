package com.viethoa.lazadaretailer.screens.login;

import com.viethoa.lazadaretailer.models.User;
import com.viethoa.lazadaretailer.network.responses.NetworkResponse;
import com.viethoa.lazadaretailer.network.services.UserService.UserService;
import com.viethoa.lazadaretailer.screens.BriefObserver;
import com.viethoa.lazadaretailer.screens.baseviews.BaseViewModel;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by VietHoa on 22/01/2017.
 */

public class LoginViewModelImpl extends BaseViewModel<LoginViewModel.Listener> implements LoginViewModel {

    @Inject
    UserService userService;

    @Inject
    public LoginViewModelImpl() {
        // Requirement
    }

    @Override
    public void login(String email, String password) {
        if (listener == null) {
            return;
        }

        manageSubscription(userService.login(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BriefObserver<NetworkResponse<User>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (listener != null) {
                            listener.onError(e);
                        }
                    }

                    @Override
                    public void onNext(NetworkResponse<User> response) {
                        if (listener == null) {
                            return;
                        }
                        if (response.getData() == null) {
                            listener.onError(new Exception("data is null"));
                        } else {
                            listener.onLoginSuccess();
                        }
                    }
                })
        );
    }
}
