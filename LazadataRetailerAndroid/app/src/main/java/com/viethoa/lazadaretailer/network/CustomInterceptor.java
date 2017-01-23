package com.viethoa.lazadaretailer.network;

import android.content.Context;
import android.text.TextUtils;

import com.viethoa.lazadaretailer.Application;
import com.viethoa.lazadaretailer.caches.UserMemoryCache;
import com.viethoa.lazadaretailer.models.User;
import com.viethoa.lazadaretailer.utilities.DeviceUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CustomInterceptor implements Interceptor {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String AUTHORIZATION_KEY = "Auth-Token";
    private static final String APP_PLATFORM = "Platform";
    private static final String APP_VERSION = "App-Version";
    private static final String DEVICE_NAME = "Device-Name";
    private static final String FORBIDDEN_CONTENT = "{\"error\":\"Forbidden\"}";
    private static final String NO_CONTENT = "{}";

    private String responseString;
    private UserMemoryCache userMemoryCache;

    public CustomInterceptor(UserMemoryCache userMemoryCache) {
        this.userMemoryCache = userMemoryCache;
    }

    private Response mockResponse(Chain chain) {
        return new Response.Builder()
                .code(200)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()))
                .addHeader("content-type", "application/json")
                .build();
    }

    // Only for unit test
    public void setResponseString(String reponseString) {
        responseString = reponseString;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!TextUtils.isEmpty(responseString)) { //only for unit test
            return mockResponse(chain);
        }

        //real working flow come from here
        Request request = chain.request();
        User currentUser = userMemoryCache.get();
        Context context = Application.getInstance();
        if (context != null) {
            Request.Builder builder = request.newBuilder()
                    .addHeader(CONTENT_TYPE, "application/json")
                    .addHeader(APP_VERSION, DeviceUtils.getAppVersionName(context))
                    .addHeader(APP_PLATFORM, "android")
                    .addHeader(DEVICE_NAME, DeviceUtils.getDeviceName());
            if (currentUser != null && !TextUtils.isEmpty(currentUser.getToken())) {
                builder.addHeader(AUTHORIZATION_KEY, currentUser.getToken());
            }
            request = builder.build();
        }

        //authentication problem
        Response response = chain.proceed(request);
        if (response.code() == 401 || response.code() == 403) {
            Response.Builder builder = response.newBuilder();
            builder.code(403);
            builder.body(ResponseBody.create(MediaType.parse("application/json"), FORBIDDEN_CONTENT));
            return builder.build();
        }

        // Error from server
        if (response.code() == 400) {
            Response.Builder builder = response.newBuilder();
            builder.code(200);  //handle by BaseResponse
            return builder.build();
        }

        // No content response
        if (response.code() == 204) {
            Response.Builder builder = response.newBuilder();
            builder.code(200);
            builder.body(ResponseBody.create(MediaType.parse("application/json"), NO_CONTENT));
            return builder.build();
        }

        return response;
    }
}