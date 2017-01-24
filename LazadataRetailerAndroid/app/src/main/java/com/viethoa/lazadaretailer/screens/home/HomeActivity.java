package com.viethoa.lazadaretailer.screens.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.viethoa.lazadaretailer.R;
import com.viethoa.lazadaretailer.di.ApplicationComponent;
import com.viethoa.lazadaretailer.di.BaseComponent;
import com.viethoa.lazadaretailer.di.homemodule.DaggerHomeComponent;
import com.viethoa.lazadaretailer.di.homemodule.HomeComponent;
import com.viethoa.lazadaretailer.di.homemodule.HomeModule;
import com.viethoa.lazadaretailer.models.Store;
import com.viethoa.lazadaretailer.screens.BriefObserver;
import com.viethoa.lazadaretailer.screens.baseviews.BaseSnackBarActivity;
import com.viethoa.lazadaretailer.screens.home.scanbarcodefragment.ScannerFragment;
import com.viethoa.lazadaretailer.screens.home.scanbarcodefragment.ScannerFragmentListener;
import com.viethoa.lazadaretailer.screens.home.storefragment.StoreFragment;
import com.viethoa.lazadaretailer.screens.home.storefragment.StoreFragmentListener;

import java.util.List;

import javax.inject.Inject;

public class HomeActivity extends BaseSnackBarActivity implements
        StoreFragmentListener,
        ScannerFragmentListener,
        HomeViewModel.Listener {

    private HomeComponent homeComponent;
    private StoreFragment storeFragment;
    private ScannerFragment scanBarcodeFragment;

    @Inject
    HomeViewModel homeViewModel;

    public static Intent newInstance(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Ask camera permission
        askCameraPermission();

        // Init
        homeViewModel.initialize(this);

        // Store fragment always
        storeFragment = StoreFragment.newInstance();
        replaceFragment(storeFragment, R.id.fragment_content, false, false);

        showLoadingDialog();
        homeViewModel.getAllStores();
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
    // Permission
    //----------------------------------------------------------------------------------------------

    private void askCameraPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        manageSubscription(rxPermissions.request(
                Manifest.permission.CAMERA)
                .compose(bindToMainThread())
                .subscribe(new BriefObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean permissionGranted) {
                        askInternetPermissionCallback(permissionGranted);
                    }
                }));
    }

    private void askInternetPermissionCallback(Boolean permissionGranted) {
        if (!permissionGranted) {
            finish();
        }
    }

    //----------------------------------------------------------------------------------------------
    // View events
    //----------------------------------------------------------------------------------------------

    @Override
    public void onError(Throwable e) {
        dismissLoadingDialog();
        if (e != null) {
            showTopErrorMessage(e.getMessage());
        }
    }

    @Override
    public void getAllStoresSuccess(List<Store> stores) {
        dismissLoadingDialog();

        // Auto select if there is just 1 store
        if (stores != null && stores.size() == 1) {
            onStoreItemClick(stores.get(0));
            return;
        }

        // Need user select store
        storeFragment.initializeView(stores);
    }

    @Override
    public void onStoreItemClick(Store store) {
        if (store == null) {
            return;
        }
        if (scanBarcodeFragment == null) {
            scanBarcodeFragment = ScannerFragment.newInstance();
        }

        replaceFragment(scanBarcodeFragment, R.id.fragment_content, true, true);
        scanBarcodeFragment.initializeView(store);
    }
}
