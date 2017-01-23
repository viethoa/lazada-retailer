package com.viethoa.lazadaretailer.di;

import android.content.Context;

import com.viethoa.lazadaretailer.di.cachemodule.CacheModule;
import com.viethoa.lazadaretailer.di.network.NetworkModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by VietHoa on 06/08/2016.
 */

@Module(
        includes = {
                CacheModule.class,
                NetworkModule.class,
        }
)
public final class ApplicationModule {
    private Context applicationContext;

    public ApplicationModule(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return applicationContext;
    }
}
