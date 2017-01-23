package com.viethoa.lazadaretailer.screens.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.viethoa.lazadaretailer.R;
import com.viethoa.lazadaretailer.di.ApplicationComponent;
import com.viethoa.lazadaretailer.di.BaseComponent;
import com.viethoa.lazadaretailer.di.homemodule.DaggerHomeComponent;
import com.viethoa.lazadaretailer.di.homemodule.HomeComponent;
import com.viethoa.lazadaretailer.di.homemodule.HomeModule;
import com.viethoa.lazadaretailer.models.Store;
import com.viethoa.lazadaretailer.screens.baseviews.BaseSnackBarActivity;
import com.viethoa.lazadaretailer.screens.home.storefragment.StoreFragment;

import java.util.List;

import javax.inject.Inject;

public class HomeActivity extends BaseSnackBarActivity implements HomeViewModel.Listener {

    private HomeComponent homeComponent;
    private StoreFragment storeFragment;

    @Inject
    HomeViewModel homeViewModel;

    public static Intent newInstance(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Init
        homeViewModel.initialize(this);

        // Store fragment always
        storeFragment = StoreFragment.newInstance();
        replaceFragment(storeFragment, R.id.fragment_content, false, false);

        showLoadingDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        homeViewModel.destroy();
    }

    //----------------------------------------------------------------------------------------------
    // Dependence
    //----------------------------------------------------------------------------------------------

    @Override
    protected void injectModule(ApplicationComponent appComponent) {
        homeComponent = DaggerHomeComponent.builder()
                .applicationComponent(appComponent)
                .homeModule(new HomeModule())
                .build();
        homeComponent.inject(this);
    }

    public BaseComponent getComponent() {
        return homeComponent;
    }

    //----------------------------------------------------------------------------------------------
    // View events
    //----------------------------------------------------------------------------------------------

    @Override
    public void onError(Throwable e) {
        if (e != null) {
            showTopErrorMessage(e.getMessage());
        }
    }

    @Override
    public void getAllStoresSuccess(List<Store> stores) {
        storeFragment.initializeView(stores);
    }
}
