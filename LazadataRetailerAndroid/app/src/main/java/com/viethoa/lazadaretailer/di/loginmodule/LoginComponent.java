package com.viethoa.lazadaretailer.di.loginmodule;

import com.viethoa.lazadaretailer.di.ActivityScope;
import com.viethoa.lazadaretailer.di.ApplicationComponent;
import com.viethoa.lazadaretailer.di.BaseComponent;
import com.viethoa.lazadaretailer.screens.login.LoginActivity;

import dagger.Component;

/**
 * Created by VietHoa on 22/01/2017.
 */
@ActivityScope
@Component(
        dependencies = {ApplicationComponent.class},
        modules = {LoginModule.class}
)
public interface LoginComponent extends BaseComponent {

    void inject(LoginActivity loginActivity);
}
