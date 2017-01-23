package com.viethoa.lazadaretailer.network.services.userservice;

import com.viethoa.lazadaretailer.models.User;
import com.viethoa.lazadaretailer.network.responses.NetworkResponse;

import rx.Observable;

/**
 * Created by VietHoa on 22/01/2017.
 */

public interface UserService {

    Observable<NetworkResponse<User>> login(String email, String password);
}
