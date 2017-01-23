package com.viethoa.lazadaretailer.di.cachemodule;

import com.viethoa.lazadaretailer.caches.UserMemoryCache;
import com.viethoa.lazadaretailer.network.CustomInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by VietHoa on 21/05/16.
 */
@Module
public class CacheModule {

    @Provides
    @Singleton
    UserMemoryCache provideUserMemoryCache() {
        return UserMemoryCache.getInstance();
    }

}
