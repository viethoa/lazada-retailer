package com.viethoa.lazadaretailer.network.responses;

import com.google.gson.annotations.Expose;

/**
 * Created by VietHoa on 22/01/2017.
 */

public class NetworkResponse<T> extends BaseResponse implements IDataResponse<T> {

    @Expose
    private T data;

    public NetworkResponse(Exception e) {
        super(e);
    }

    @Override
    public T getData() {
        return data;
    }
}
