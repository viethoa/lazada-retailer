package com.viethoa.lazadaretailer.network.services.orderservice;

import com.viethoa.lazadaretailer.configs.ErrorConstants;
import com.viethoa.lazadaretailer.models.Order;
import com.viethoa.lazadaretailer.network.responses.NetworkResponse;
import com.viethoa.lazadaretailer.network.services.BaseServices;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by VietHoa on 24/01/2017.
 */

public class OrderServiceImpl extends BaseServices implements OrderService {

    @Inject
    OrderAPIs orderAPIs;

    @Inject
    public OrderServiceImpl() {
        // Requirement
    }

    @Override
    public Observable<NetworkResponse<Order>> pushOrder(Order order) {
        if (order == null) {
            return Observable.just(new NetworkResponse<>(
                    new Exception(String.format(ErrorConstants.SYSTEM_ERROR, "(pushOrder: order is null)"))
            ));
        }

        return Observable.create(subscriber -> {
            handleResponse(orderAPIs.pushOrder(order.getStoreID(), order.getOrderNo()), null, null, subscriber);
        });
    }
}
