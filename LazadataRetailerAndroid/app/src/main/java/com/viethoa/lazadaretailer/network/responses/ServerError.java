package com.viethoa.lazadaretailer.network.responses;

/**
 * Created by VietHoa on 22/11/2016.
 */

public class ServerError extends Exception {

    private String error;

    public ServerError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    @Override
    public String getMessage() {
        return error;
    }

    @Override
    public synchronized Throwable getCause() {
        return new Throwable(error);
    }
}
