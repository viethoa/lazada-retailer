package com.viethoa.lazadaretailer.network.responses;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public abstract class BaseResponse implements Serializable {

    @Expose
    private Error error;

    public boolean isError() {
        return error != null && !TextUtils.isEmpty(error.getMessage());
    }

    public Exception getException() {
        return new Exception(error.getMessage());
    }

    private class Error {
        private int code;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}