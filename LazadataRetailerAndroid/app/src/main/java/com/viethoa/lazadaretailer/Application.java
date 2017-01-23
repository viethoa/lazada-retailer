package com.viethoa.lazadaretailer;

import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.NoEncryption;
import com.viethoa.lazadaretailer.di.ApplicationComponent;
import com.viethoa.lazadaretailer.di.ApplicationModule;
import com.viethoa.lazadaretailer.di.DaggerApplicationComponent;

/**
 * Created by VietHoa on 22/01/2017.
 */

public class Application extends android.app.Application {

    private static Application application;
    private ApplicationComponent appComponent;

    public static Application getInstance() {
        return application;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        //Hawk
        setupHawk();

        // Dagger
        initializeDagger();
    }

    //----------------------------------------------------------------------------------------------
    // Setup dagger
    //----------------------------------------------------------------------------------------------

    private void initializeDagger() {
        appComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return appComponent;
    }

    //----------------------------------------------------------------------------------------------
    // Setup Hawk
    //----------------------------------------------------------------------------------------------

    private void setupHawk() {
        Hawk.init(this)
                .setEncryption(new NoEncryption())
                //.setLogInterceptor(new HawkLogger())
                .build();
    }
}
