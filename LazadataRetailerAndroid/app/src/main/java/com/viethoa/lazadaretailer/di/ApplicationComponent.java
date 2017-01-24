package com.viethoa.lazadaretailer.di;

import android.content.Context;

import com.viethoa.lazadaretailer.caches.StoreMemoryCache;
import com.viethoa.lazadaretailer.caches.UserMemoryCache;
import com.viethoa.lazadaretailer.network.services.orderservice.OrderService;
import com.viethoa.lazadaretailer.network.services.storeservice.StoreService;
import com.viethoa.lazadaretailer.network.services.userservice.UserService;
import com.viethoa.lazadaretailer.service.SyncOrderService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by VietHoa on 19/04/16.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncOrderService syncOrderService);


    UserMemoryCache getUserMemoryCache();

    StoreMemoryCache getStoreMemoryCache();


    UserService getUserService();

    StoreService getStoreService();

    OrderService getOrderService();


    Context getApplicationContext();
}