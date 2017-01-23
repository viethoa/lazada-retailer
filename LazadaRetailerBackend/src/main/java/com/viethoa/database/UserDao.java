package com.viethoa.database;

import com.viethoa.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.jws.soap.SOAPBinding;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by VietHoa on 16/01/2017.
 */
@Repository
public class UserDao {
    static final String TABLE_NAME = "t_user";
    static final String ID = "id";
    static final String EMAIL = "email";
    static final String PASSWORD = "password";
    static final String NAME = "name";
    static final String PHONE = "phone";
    static final String ADDRESS = "address";
    static final String AUTHORITY = "authority";
    static final String CREATED_AT = "created_at";

    private SimpleJdbcInsert insertUser;

    public UserDao() {
        insertUser = new SimpleJdbcInsert(new JdbcTemplate(DatabaseManager.getDataSource()));
        insertUser.withTableName(TABLE_NAME);
        insertUser.setGeneratedKeyName(ID);
        insertUser.usingGeneratedKeyColumns(ID);
    }

    public Number insert(User user) {
        Map<String, Object> params = new HashMap<>();
        params.put(EMAIL, user.getEmail());
        params.put(PASSWORD, user.getPassword());
        params.put(NAME, user.getName());
        params.put(PHONE, user.getPhone());
        params.put(ADDRESS, user.getAddress());
        params.put(AUTHORITY, user.getAuthority());
        params.put(CREATED_AT, user.getCreatedAt());
        return insertUser.executeAndReturnKey(params);
    }

    public User signIn(String email, String password) {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            return null;
        }

        User user = null;
        try {
            Statement stmt = DatabaseManager.getDataSource().getConnection().createStatement();
            String sql = String.format("SELECT * FROM %s WHERE %s = '%s' AND %s = '%s'",
                    TABLE_NAME, EMAIL, email, PASSWORD, password);
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                user = new User();
                user.setId(rs.getLong(ID));
                user.setEmail(rs.getString(EMAIL));
                user.setName(rs.getString(NAME));
                user.setAddress(rs.getString(ADDRESS));
                user.setAuthority(rs.getString(AUTHORITY));
                user.setPhone(rs.getString(PHONE));
                user.setCreatedAt(rs.getLong(CREATED_AT));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public boolean isExist(String email) {
        if (StringUtils.isEmpty(email)) {
            return false;
        }

        boolean isExist = false;
        try {
            Statement stmt = DatabaseManager.getDataSource().getConnection().createStatement();
            String sql = String.format("SELECT * FROM %s WHERE %s = '%s'", TABLE_NAME, EMAIL, email);
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
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
