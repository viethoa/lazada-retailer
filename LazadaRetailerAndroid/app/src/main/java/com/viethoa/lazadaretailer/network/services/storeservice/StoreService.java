package com.viethoa.lazadaretailer.network.services.storeservice;

import com.viethoa.lazadaretailer.models.Store;
import com.viethoa.lazadaretailer.network.responses.NetworkResponse;

import java.util.List;

import rx.Observable;

/**
 * Created by VietHoa on 23/01/2017.
 */

public interface StoreService {

    Observable<NetworkResponse<List<Store>>> getAllStores(long userID);

}
