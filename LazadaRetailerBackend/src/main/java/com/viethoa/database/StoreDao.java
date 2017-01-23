package com.viethoa.database;

import com.viethoa.models.Store;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by VietHoa on 17/01/2017.
 */
@Repository
public class StoreDao {

    static final String TABLE_NAME = "t_store";
    static final String ID = "id";
    static final String NAME = "name";
    static final String USER_ID = "user_id";
    static final String CREATED_AT = "created_at";

    private SimpleJdbcInsert insertStore;

    public StoreDao() {
        insertStore = new SimpleJdbcInsert(new JdbcTemplate(DatabaseManager.getDataSource()));
        insertStore.withTableName(TABLE_NAME);
        insertStore.setGeneratedKeyName(ID);
        insertStore.usingGeneratedKeyColumns(ID);
    }

    public Number insert(Store store) {
        if (store == null) {
            return null;
        }

        Map<String, Object> params = new HashMap<>();
        params.put(NAME, store.getName());
        params.put(USER_ID, store.getUserID());
        params.put(CREATED_AT, store.getCreatedAt());
        return insertStore.executeAndReturnKey(params);
    }

    public List<Store> getAll(long userID) {
        List<Store> stores = new ArrayList<>();
        try {
            Statement stmt = DatabaseManager.getDataSource().getConnection().createStatement();
            String sql = String.format("SELECT * FROM %s WHERE %s = %s",
                    TABLE_NAME, USER_ID, userID);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Store store = new Store(
                        rs.getLong(ID),
                        rs.getString(NAME),
                        rs.getLong(USER_ID),
                        rs.getLong(CREATED_AT)
                );
                stores.add(store);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stores;
    }

    public boolean isExist(long userId, String storeName) {
        if (StringUtils.isEmpty(storeName)) {
            return false;
        }

        boolean isExist = false;
        try {
            Statement stmt = DatabaseManager.getDataSource().getConnection().createStatement();
            String sql = String.format("SELECT * FROM %s WHERE %s = '%s' AND %s = %s",
                    TABLE_NAME, NAME, storeName, USER_ID, userId);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                isExist = true;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isExist;
    }

    public boolean isExist(long storeID) {
        boolean isExist = false;
        try {
            Statement stmt = DatabaseManager.getDataSource().getConnection().createStatement();
            String sql = String.format("SELECT * FROM %s WHERE %s = %s", TABLE_NAME, ID, storeID);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                isExist = true;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isExist;
    }
}
