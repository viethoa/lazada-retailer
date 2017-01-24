package com.viethoa.lazadaretailer.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.viethoa.lazadaretailer.configs.NetworkConfig;
import com.viethoa.lazadaretailer.network.services.orderservice.OrderAPIs;
import com.viethoa.lazadaretailer.network.services.storeservice.StoreAPIs;
import com.viethoa.lazadaretailer.network.services.userservice.UserAPIs;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAPIService {

    private UserAPIs userAPIs;
    private StoreAPIs storeAPIs;
    private OrderAPIs orderAPIs;

    @Inject
    public RetrofitAPIService(CustomInterceptor customInterceptor) {
        //logger
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);

        Gson GSON = new GsonBuilder()
                //.excludeFieldsWithoutExposeAnnotation()
                //.registerTypeAdapter(Date.class, new DateConverter())
                .disableHtmlEscaping()
                .create();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(customInterceptor)
                .addInterceptor(logger)
                .build();

        // Driver gateways
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkConfig.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(GSON))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        userAPIs = retrofit.create(UserAPIs.class);
        storeAPIs = retrofit.create(StoreAPIs.class);
        orderAPIs = retrofit.create(OrderAPIs.class);
    }

    public UserAPIs getUserAPIs() {
        return userAPIs;
    }

    public StoreAPIs getStoreAPIs() {
        return storeAPIs;
    }

    public OrderAPIs getOrderAPIs() {
        return orderAPIs;
    }
}
