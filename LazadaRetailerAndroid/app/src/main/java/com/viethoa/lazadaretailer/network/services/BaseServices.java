package com.viethoa.lazadaretailer.network.services;

import android.content.Context;

import com.viethoa.lazadaretailer.R;
import com.viethoa.lazadaretailer.network.responses.BaseResponse;

import java.io.IOException;
import java.net.SocketTimeoutException;

import javax.inject.Inject;

import retrofit2.Call;
import rx.Subscriber;

public abstract class BaseServices {
    private static final String UNKNOWN_ERROR = "Fail to connect server!";

    @Inject
    public Context mAppContext;

    public interface InternalProcess<T extends BaseResponse> {
        void processInternally(T response);
    }

    protected void handleResponse(Call<? extends BaseResponse> call, InternalProcess successCase,
                                  InternalProcess errorCase, Subscriber subscriber) {
        try {
            // UnSubscribed
            if (subscriber == null || subscriber.isUnsubscribed()) {
                return;
            }

            // Unknown error
            BaseResponse response = call.execute().body();
            if (response == null) {
                errorProcessHandler(errorCase);
                subscriber.onError(new Exception(UNKNOWN_ERROR));
                subscriber.onCompleted();
                return;
            }

            // Server response error
            if (response.isError()) {
                errorProcessHandler(errorCase);
                subscriber.onError(response.getException());
                subscriber.onCompleted();
                return;
            }

            successProcessHandler(successCase, response);
            subscriber.onNext(response);
            subscriber.onCompleted();
        } catch (Exception e) {
            if (subscriber == null || subscriber.isUnsubscribed()) {
                return;
            }

            errorProcessHandler(errorCase);
            if (e.getCause() instanceof SocketTimeoutException) {
                subscriber.onError(new Exception(mAppContext.getString(R.string.no_internet_connection)));
                subscriber.onCompleted();
            } else if (e instanceof IOException) {
                subscriber.onError(new Exception(mAppContext.getString(R.string.request_time_out)));
                subscriber.onCompleted();
            } else {
                subscriber.onError(e);
                subscriber.onCompleted();
            }
        }
    }

    private void errorProcessHandler(InternalProcess errorCase) {
        if (errorCase != null) {
            errorCase.processInternally(null);
        }
    }

    private void successProcessHandler(InternalProcess successCase, BaseResponse response) {
        if (successCase != null) {
            successCase.processInternally(response);
        }
    }
}
