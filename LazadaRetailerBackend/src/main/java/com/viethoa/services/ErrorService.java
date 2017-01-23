package com.viethoa.services;

import com.viethoa.models.ErrorModel;

/**
 * Created by VietHoa on 19/01/2017.
 */
public class ErrorService {

    private static final String TOKEN_EXPIRED_MESSAGE = "your token has expired";
    private static final String UNKNOWN_ERROR = "unknown error";
    private static final int CODE_400 = 400;

    public synchronized ErrorModel tokenExpired() {
        return new ErrorModel(TOKEN_EXPIRED_MESSAGE, CODE_400);
    }

    public synchronized ErrorModel badRequest(Exception ex) {
        if (ex == null) {
            return new ErrorModel(UNKNOWN_ERROR, CODE_400);
        } else {
            return new ErrorModel(ex.getMessage(), CODE_400);
        }
    }
}
