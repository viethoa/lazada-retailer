package com.viethoa.lazadaretailer.di.network;
import com.viethoa.lazadaretailer.caches.UserMemoryCache;
import com.viethoa.lazadaretailer.network.CustomInterceptor;
import com.viethoa.lazadaretailer.network.RetrofitAPIService;
import com.viethoa.lazadaretailer.network.services.orderservice.OrderAPIs;
import com.viethoa.lazadaretailer.network.services.orderservice.OrderService;
import com.viethoa.lazadaretailer.network.services.orderservice.OrderServiceImpl;
import com.viethoa.lazadaretailer.network.services.storeservice.StoreAPIs;
import com.viethoa.lazadaretailer.network.services.storeservice.StoreService;
import com.viethoa.lazadaretailer.network.services.storeservice.StoreServiceImpl;
import com.viethoa.lazadaretailer.network.services.userservice.UserAPIs;
import com.viethoa.lazadaretailer.network.services.userservice.UserService;
import com.viethoa.lazadaretailer.network.services.userservice.UserServiceImpl;

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
    UserService provideUserService(UserServiceImpl userService) {
        return userService;
    }

    @Provides
    StoreAPIs provideStoreAPIs(RetrofitAPIService retrofitAPIService) {
        return retrofitAPIService.getStoreAPIs();
    }

    @Provides
    StoreService provideStoreService(StoreServiceImpl storeService) {
        return storeService;
    }

    @Provides
    OrderAPIs provideOrderAPIs(RetrofitAPIService retrofitAPIService) {
        return retrofitAPIService.getOrderAPIs();
    }

    @Provides
    OrderService provideOrderService(OrderServiceImpl orderService) {
        return orderService;
    }
}
