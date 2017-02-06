package com.viethoa.database;

import com.viethoa.models.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by VietHoa on 17/01/2017.
 */
@Repository
public class OrderDao {

    static final String TABLE_NAME = "t_order";
    static final String ID = "id";
    static final String ORDER_NO = "order_no";
    static final String STATE = "state";
    static final String STORE_ID = "store_id";
    static final String INSERT_AT = "insert_at";
    static final String UPDATE_AT = "update_at";

    private SimpleJdbcInsert insertOrder;

    public OrderDao() {
        insertOrder = new SimpleJdbcInsert(new JdbcTemplate(DatabaseManager.getDataSource()));
        insertOrder.withTableName(TABLE_NAME);
        insertOrder.setGeneratedKeyName(ID);
        insertOrder.usingGeneratedKeyColumns(ID);
    }

    public Number insert(Order order) {
        if (order == null) {
            return null;
        }

        Map<String, Object> params = new HashMap<String, Object>(5);
        params.put(ORDER_NO, order.getOrderNo());
        params.put(STATE, order.getState());
        params.put(STORE_ID, order.getStoreID());
        params.put(INSERT_AT, order.getInsertAt());
        params.put(UPDATE_AT, order.getUpdateAt());
        return insertOrder.executeAndReturnKey(params);
    }

    public void delete(Order order) {
        if (order == null) {
            return;
        }

        try {
            String deleteStatement = String.format("DELETE FROM %s WHERE %s = '%s' AND %s = '%s'",
                    TABLE_NAME, ORDER_NO, order.getOrderNo(), STORE_ID, order.getStoreID());
            DatabaseManager.getJdbcTemplate().update(deleteStatement);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isExist(long storeID, String orderNo) {
        if (StringUtils.isEmpty(orderNo)) {
            return false;
        }

        boolean isExist = false;
        try {
            Statement stmt = DatabaseManager.getDataSource().getConnection().createStatement();
            String sql = String.format("SELECT * FROM %s WHERE %s = '%s' AND %s = '%s'",
                    TABLE_NAME, ORDER_NO, orderNo, STORE_ID, storeID);
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
