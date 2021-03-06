package com.viethoa.lazadaretailer.screens.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.viethoa.lazadaretailer.screens.scan.scanbarcodefragment.ScannerFragment;
import com.viethoa.lazadaretailer.screens.scan.scanbarcodefragment.ScannerFragmentListener;
import com.viethoa.lazadaretailer.screens.home.storefragment.StoreFragment;
import com.viethoa.lazadaretailer.screens.home.storefragment.StoreFragmentListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class HomeActivity extends BaseSnackBarActivity implements
        StoreFragmentListener,
        ScannerFragmentListener,
        HomeViewModel.Listener {

    private HomeRouter homeRouter = new HomeRouter();
    private HomeComponent homeComponent;
    private StoreFragment storeFragment;

    @Inject
    HomeViewModel homeViewModel;

    @Bind(R.id.toolbar_title)
    TextView toolbarTitle;
    @Bind(R.id.toolbar_icon)
    ImageView toolbarIcon;

    public static Intent newInstance(Context context) {
        return new Intent(context, HomeActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Init toolbar
        toolbarIcon.setImageResource(R.mipmap.ic_launcher);
        toolbarTitle.setText(R.string.app_name);

        // Init view model
        homeViewModel.initialize(this);

        // Ask camera permission
        askCameraPermission();

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
    // BackPress events
    //----------------------------------------------------------------------------------------------

    @Override
    public void onBackPressed() {
        // Can not back press if scanner in processing
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_content);
        if (currentFragment instanceof ScannerFragment) {
            if (((ScannerFragment) currentFragment).isProcessing()) {
                leaveScannerWhileProcessing();
                return;
            }
        }

        super.onBackPressed();
    }

    private void leaveScannerWhileProcessing() {
        new DialogMessage(this,
                R.string.scanner_leave_scan_title,
                R.string.scanner_leave_scan_message,
                R.string.scanner_leave_scan_cancel,
                R.string.scanner_leave_scan_ok,
                new DialogMessage.MessageDialogListener() {
                    @Override
                    public void OnNegativeClicked() {
                        // This dialog has dismissed.
                    }

                    @Override
                    public void OnPositiveClicked() {
                        HomeActivity.super.onBackPressed();
                    }
                }
        ).show();
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
        storeFragment.initializeView(stores);
    }

    @Override
    public void onStoreItemClick(Store store) {
        if (store != null) {
            homeRouter.navigateToStoreActivity(this, store);
        }
    }
}
