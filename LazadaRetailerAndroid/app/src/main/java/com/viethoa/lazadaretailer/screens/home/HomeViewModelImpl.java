package com.viethoa.lazadaretailer.screens.home;

import com.viethoa.lazadaretailer.caches.StoreMemoryCache;
import com.viethoa.lazadaretailer.caches.UserMemoryCache;
import com.viethoa.lazadaretailer.models.Store;
import com.viethoa.lazadaretailer.models.User;
import com.viethoa.lazadaretailer.network.responses.NetworkResponse;
import com.viethoa.lazadaretailer.network.services.storeservice.StoreService;
import com.viethoa.lazadaretailer.screens.BriefObserver;
import com.viethoa.lazadaretailer.screens.baseviews.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by VietHoa on 23/01/2017.
 */

public class HomeViewModelImpl extends BaseViewModel<HomeViewModel.Listener> implements HomeViewModel {

    @Inject
    StoreService storeService;
    @Inject
    StoreMemoryCache storeMemoryCache;
    @Inject
    UserMemoryCache userMemoryCache;

    @Inject
    public HomeViewModelImpl() {
        // Requirement
    }

    @Override
    public void forceLogout() {

    }

    @Override
    public void getAllStores() {
        if (listener == null) {
            return;
        }
        if (userMemoryCache.get() == null) {
            forceLogout();
            return;
        }

        if (storeMemoryCache.get() != null) {
            listener.getAllStoresSuccess(storeMemoryCache.get());
        }

        manageSubscription(storeService.getAllStores(userMemoryCache.getUserID())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BriefObserver<NetworkResponse<List<Store>>>() {
                    @Override
                    public void onError(Throwable e) {
                        if (listener != null) {
                            listener.onError(e);
                        }
                    }

                    @Override
                    public void onNext(NetworkResponse<List<Store>> response) {
                        if (listener == null) {
                            return;
                        }
                        if (response.getData() == null) {
                            listener.onError(new Exception("data is null"));
                        } else {
                            listener.getAllStoresSuccess(response.getData());
                        }
                    }
                })
        );
    }
}
