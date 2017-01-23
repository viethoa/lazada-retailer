package com.viethoa.lazadaretailer.network.services.storeservice;

import com.viethoa.lazadaretailer.caches.StoreMemoryCache;
import com.viethoa.lazadaretailer.models.Store;
import com.viethoa.lazadaretailer.models.User;
import com.viethoa.lazadaretailer.network.responses.NetworkResponse;
import com.viethoa.lazadaretailer.network.services.BaseServices;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by VietHoa on 23/01/2017.
 */

public class StoreServiceImpl extends BaseServices implements StoreService {

    @Inject
    StoreAPIs storeAPIs;
    @Inject
    StoreMemoryCache storeMemoryCache;

    @Inject
    public StoreServiceImpl() {
        // Requirement
    }

    @Override
    public Observable<NetworkResponse<List<Store>>> getAllStores(long userID) {
        return Observable.create(subscriber -> {
            handleResponse(storeAPIs.getAllStores(userID), new InternalProcess<NetworkResponse<List<Store>>>() {
                @Override
                public void processInternally(NetworkResponse<List<Store>> response) {
                    if (response != null && response.getData() != null) {
                        storeMemoryCache.set(response.getData());
                    }
                }
            }, null, subscriber);
        });
    }
}
