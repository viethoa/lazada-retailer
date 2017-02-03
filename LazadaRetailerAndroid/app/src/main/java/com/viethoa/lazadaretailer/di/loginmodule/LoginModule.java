package com.viethoa.lazadaretailer.di.loginmodule;

import com.viethoa.lazadaretailer.di.ActivityScope;
import com.viethoa.lazadaretailer.screens.login.LoginViewModel;
import com.viethoa.lazadaretailer.screens.login.LoginViewModelImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by VietHoa on 22/01/2017.
 */
@Module
public class LoginModule {

    @Provides
    @ActivityScope
    LoginViewModel provideLoginViewModel(LoginViewModelImpl viewModel) {
        return viewModel;
    }
}
