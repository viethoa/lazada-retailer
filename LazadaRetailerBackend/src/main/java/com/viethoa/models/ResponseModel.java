package com.viethoa.models;

/**
 * Created by VietHoa on 24/01/2017.
 */
public class ResponseModel<T> {

    private T data;
    private ErrorModel error;

    public ResponseModel(T data) {
        this.data = data;
    }

    public ResponseModel(ErrorModel error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
    }
}
