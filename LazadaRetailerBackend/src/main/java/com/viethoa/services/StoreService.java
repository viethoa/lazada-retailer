package com.viethoa.services;

import com.viethoa.database.StoreDao;
import com.viethoa.models.Store;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VietHoa on 19/01/2017.
 */
public class StoreService {

    public synchronized Store create(long userID, String storeName) throws Exception {
        if (StringUtils.isEmpty(storeName)) {
            throw new Exception("Hãy nhập tên giang hàng");
        }

        StoreDao storeDao = new StoreDao();
        if (storeDao.isExist(userID, storeName)) {
            throw new Exception("Gian hàng đã tồn tại");
        }

        Store store = new Store(storeName, userID);
        Number storeID = storeDao.insert(store);
        if (storeID != null) {
            store.setId(storeID.longValue());
        }

        return store;
    }

    public synchronized List<Store> getAll(long userID) throws Exception {
        StoreDao storeDao = new StoreDao();
        if (!storeDao.isExist(userID)) {
            throw new Exception("Gian hàng này không tồn tại hoặc đã bị xóa");
        }

        return storeDao.getAll(userID);
    }
}
