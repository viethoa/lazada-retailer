package com.viethoa.lazadaretailer.network.services.storeservice;

import com.viethoa.lazadaretailer.models.Store;
import com.viethoa.lazadaretailer.network.responses.NetworkResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;

/**
 * Created by VietHoa on 23/01/2017.
 */

public interface StoreAPIs {

    @GET("/store/get_all_stores")
    Call<NetworkResponse<List<Store>>> getAllStores(
            @Field("user_id") long userID
    );
}
