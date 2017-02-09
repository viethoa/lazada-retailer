package com.viethoa.lazadaretailer.di.homemodule;

import com.viethoa.lazadaretailer.di.ActivityScope;
import com.viethoa.lazadaretailer.di.ApplicationComponent;
import com.viethoa.lazadaretailer.di.BaseComponent;
import com.viethoa.lazadaretailer.screens.home.HomeActivity;
import com.viethoa.lazadaretailer.screens.scan.scanbarcodefragment.ScannerFragment;
import com.viethoa.lazadaretailer.screens.home.storefragment.StoreFragment;

import dagger.Component;

/**
 * Created by VietHoa on 23/01/2017.
 */
@ActivityScope
@Component(
        dependencies = {ApplicationComponent.class},
        modules = {HomeModule.class}
)
public interface HomeComponent extends BaseComponent {

    void inject(HomeActivity homeActivity);

    void inject(StoreFragment storeFragment);

    void inject(ScannerFragment scanBarcodeFragment);
}
