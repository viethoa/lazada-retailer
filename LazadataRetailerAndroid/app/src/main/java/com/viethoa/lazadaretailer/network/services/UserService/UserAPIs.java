package com.viethoa.lazadaretailer.network.services.userservice;

import com.viethoa.lazadaretailer.models.User;
import com.viethoa.lazadaretailer.network.responses.NetworkResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by VietHoa on 22/01/2017.
 */

public interface UserAPIs {

    @FormUrlEncoded
    @POST("/user/login")
    Call<NetworkResponse<User>> login(
            @Field("email") String email,
            @Field("password") String password
    );
}
