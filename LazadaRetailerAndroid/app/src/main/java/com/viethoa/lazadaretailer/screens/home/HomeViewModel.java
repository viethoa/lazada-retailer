package com.viethoa.lazadaretailer.screens.home;

import com.viethoa.lazadaretailer.models.Store;
import com.viethoa.lazadaretailer.screens.baseviews.BaseViewModel;

import java.util.List;

/**
 * Created by VietHoa on 23/01/2017.
 */

public interface HomeViewModel {

    interface Listener extends BaseViewModel.Listener {

        void getAllStoresSuccess(List<Store> stores);
    }

    void initialize(HomeViewModel.Listener listener);

    void destroy();

    void getAllStores();

    void forceLogout();
}
