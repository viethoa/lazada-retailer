package com.viethoa.lazadaretailer.network.services.orderservice;

import com.viethoa.lazadaretailer.models.Order;
import com.viethoa.lazadaretailer.network.responses.NetworkResponse;

import rx.Observable;

/**
 * Created by VietHoa on 24/01/2017.
 */

public interface OrderService {

    Observable<NetworkResponse<Order>> pushOrder(Order order);
}
