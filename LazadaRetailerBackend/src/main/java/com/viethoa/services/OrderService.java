package com.viethoa.services;

import com.viethoa.database.OrderDao;
import com.viethoa.database.StoreDao;
import com.viethoa.models.Order;
import org.springframework.util.StringUtils;

/**
 * Created by VietHoa on 20/01/2017.
 */
public class OrderService {

    public synchronized Order add(long storeID, String orderNo) throws Exception {
        if (StringUtils.isEmpty(orderNo)) {
            throw new Exception("Missing order no");
        }

        StoreDao storeDao = new StoreDao();
        if (!storeDao.isExist(storeID)) {
            throw new Exception("Store does not exist");
        }

        OrderDao orderDao = new OrderDao();
        if (orderDao.isExist(storeID, orderNo)) {
            throw new Exception("This order has created before");
        }

        Order order = new Order(orderNo, storeID);
        Number orderID = orderDao.insert(order);
        if (orderID != null) {
            order.setId(orderID.longValue());
        }

        return order;
    }
}
