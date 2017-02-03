package com.viethoa.lazadaretailer.network.services.userservice;

import com.viethoa.lazadaretailer.caches.UserMemoryCache;
import com.viethoa.lazadaretailer.models.User;
import com.viethoa.lazadaretailer.network.responses.NetworkResponse;
import com.viethoa.lazadaretailer.network.services.BaseServices;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by VietHoa on 22/01/2017.
 */

public class UserServiceImpl extends BaseServices implements UserService {

    @Inject
    UserAPIs userAPIs;
    @Inject
    UserMemoryCache userMemoryCache;

    @Inject
    public UserServiceImpl() {
        // Requirement
    }

    @Override
    public Observable<NetworkResponse<User>> login(String email, String password) {
        return Observable.create(subscriber -> {
            handleResponse(userAPIs.login(email, password), new InternalProcess<NetworkResponse<User>>() {

                @Override
                public void processInternally(NetworkResponse<User> response) {
                    if (response != null && response.getData() != null) {
                        userMemoryCache.set(response.getData());
                    }
                }
            }, null, subscriber);
        });
    }
}
