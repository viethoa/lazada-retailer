package com.viethoa.lazadaretailer.di;

import android.content.Context;

import com.viethoa.lazadaretailer.caches.UserMemoryCache;
import com.viethoa.lazadaretailer.network.services.UserService.UserService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by VietHoa on 19/04/16.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    UserMemoryCache getUserMemoryCache();


    UserService getUserService();


    Context getApplicationContext();
}