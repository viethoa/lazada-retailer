package com.viethoa.models;

/**
 * Created by VietHoa on 17/01/2017.
 */
public class ErrorModel {

    private String message;
    private int code;

    public ErrorModel(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
