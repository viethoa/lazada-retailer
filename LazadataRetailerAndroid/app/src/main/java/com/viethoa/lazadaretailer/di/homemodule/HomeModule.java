package com.viethoa.lazadaretailer.di.homemodule;

import com.viethoa.lazadaretailer.di.ActivityScope;
import com.viethoa.lazadaretailer.screens.home.HomeViewModel;
import com.viethoa.lazadaretailer.screens.home.HomeViewModelImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by VietHoa on 23/01/2017.
 */
@Module
public class HomeModule {

    @Provides
    @ActivityScope
    public HomeViewModel provideHomeViewModel(HomeViewModelImpl viewModel) {
        return viewModel;
    }
}
