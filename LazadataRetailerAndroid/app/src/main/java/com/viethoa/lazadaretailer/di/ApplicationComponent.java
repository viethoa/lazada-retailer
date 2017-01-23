package com.viethoa.lazadaretailer.di;

import android.content.Context;

import com.viethoa.lazadaretailer.caches.StoreMemoryCache;
import com.viethoa.lazadaretailer.caches.UserMemoryCache;
import com.viethoa.lazadaretailer.network.services.storeservice.StoreService;
import com.viethoa.lazadaretailer.network.services.userservice.UserService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by VietHoa on 19/04/16.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    UserMemoryCache getUserMemoryCache();

    StoreMemoryCache getStoreMemoryCache();


    UserService getUserService();

    StoreService getStoreService();


    Context getApplicationContext();
}