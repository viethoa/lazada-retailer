package com.viethoa.lazadaretailer.network.responses;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public abstract class BaseResponse implements Serializable {

    @Expose
    private String error;

    public boolean isError() {
        return !TextUtils.isEmpty(error);
    }

    public Exception getException() {
        return new ServerError(error);
    }
}