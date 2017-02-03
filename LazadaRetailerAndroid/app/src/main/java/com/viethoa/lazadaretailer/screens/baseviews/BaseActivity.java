package com.viethoa.lazadaretailer.screens.baseviews;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.viethoa.lazadaretailer.Application;
import com.viethoa.lazadaretailer.R;
import com.viethoa.lazadaretailer.di.ApplicationComponent;
import com.viethoa.lazadaretailer.di.BaseComponent;
import com.viethoa.lazadaretailer.loggers.Logger;

import javax.annotation.Resource;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by VietHoa on 27/04/16.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();
    private DialogLoading loadingDialog;
    private CompositeSubscription mCompositeSubscription;

    @Nullable
    @Bind(R.id.toolbar)
    protected Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Should start this first to make sure every children can access to activity component.
        initializeDagger();

        super.onCreate(savedInstanceState);
        loadingDialog = DialogLoading.newInstance(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        if (toolBar == null) {
            return;
        }

        setSupportActionBar(toolBar);
    }

    //----------------------------------------------------------------------------------------------
    // Should release all subscriptions
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
            mCompositeSubscription.clear();
            mCompositeSubscription = null;
        }
    }

    public synchronized Subscription manageSubscription(Subscription subscription) {
        if (mCompositeSubscription == null || mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
        return subscription;
    }

    public final <T> Observable.Transformer<T, T> bindToMainThread() {
        return tObservable -> tObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    //----------------------------------------------------------------------------------------------
    // Setup dagger
    //----------------------------------------------------------------------------------------------

    private void initializeDagger() {
        Application application = (Application) getApplication();
        injectModule(application.getComponent());
    }

    protected void injectModule(ApplicationComponent appComponent) {
        // Todo override
    }

    /**
     * For the children of Activity can inject to access dependencies
     */
    public BaseComponent getComponent() {
        return null; // Todo override
    }

    //----------------------------------------------------------------------------------------------
    // Toolbar
    //----------------------------------------------------------------------------------------------

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void showToolbarBackIcon() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            return;
        }

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(null);
    }

    protected void showToolbarTitle(CharSequence title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            return;
        }

        actionBar.setTitle(title);
    }

    protected void showToolbarHomeIcon(@DrawableRes int icon){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            return;
        }

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(icon);
    }

    //----------------------------------------------------------------------------------------------
    // Loading Dialog
    //----------------------------------------------------------------------------------------------

    public void showLoadingDialog() {
        if (isFinishing() || loadingDialog == null || loadingDialog.isShowing()) {
            return;
        }

        loadingDialog.show();
    }

    public void dismissLoadingDialog() {
        if (loadingDialog == null) {
            return;
        }
        if (!loadingDialog.isShowing()) {
            return;
        }

        loadingDialog.dismiss();
    }

    //----------------------------------------------------------------------------------------------
    // Helpers function
    //----------------------------------------------------------------------------------------------

    protected void replaceFragment(final Fragment fg, final int containerResId,
                                   final boolean backStacked, final boolean animated) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        if (animated) {
            tx.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        }
        if (backStacked) {
            tx.addToBackStack(fg.getClass().getSimpleName());
        }
        tx.replace(containerResId, fg, fg.getClass().getSimpleName());
        tx.commit();
        fm.executePendingTransactions();
    }

    protected void openUrlInBrowser(String url) {
        if (url == null || url.length() <= 0) {
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        try {
            intent.setData(Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            Logger.d(TAG, e.getMessage());
        }
    }
}
