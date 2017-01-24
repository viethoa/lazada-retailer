package com.viethoa.lazadaretailer.network.services.orderservice;

import com.viethoa.lazadaretailer.models.Order;
import com.viethoa.lazadaretailer.network.responses.NetworkResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by VietHoa on 24/01/2017.
 */

public interface OrderAPIs {

    @FormUrlEncoded
    @POST("/order/create")
    Call<NetworkResponse<Order>> pushOrder(
            @Field("store_id") long storeID,
            @Field("order_no") String orderNo
    );
}
