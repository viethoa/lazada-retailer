package com.viethoa.lazadaretailer.di.network;
import com.viethoa.lazadaretailer.caches.UserMemoryCache;
import com.viethoa.lazadaretailer.network.CustomInterceptor;
import com.viethoa.lazadaretailer.network.RetrofitAPIService;
import com.viethoa.lazadaretailer.network.services.UserService.UserAPIs;
import com.viethoa.lazadaretailer.network.services.UserService.UserService;
import com.viethoa.lazadaretailer.network.services.UserService.UserServiceImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by VietHoa on 17/01/16.
 */
@Module
public class NetworkModule {

    @Provides
    @Singleton
    CustomInterceptor provideCustomInterceptor(UserMemoryCache userMemoryCache) {
        return new CustomInterceptor(userMemoryCache);
    }

    @Provides
    UserAPIs provideUserAPIs(RetrofitAPIService retrofitAPIService) {
        return retrofitAPIService.getUserAPIs();
    }

    @Provides
    @Singleton
    UserService provideUserService(UserServiceImpl userService) {
        return userService;
    }
}
