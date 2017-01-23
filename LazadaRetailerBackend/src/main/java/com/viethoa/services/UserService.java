package com.viethoa.services;

import com.viethoa.database.UserDao;
import com.viethoa.models.User;
import org.springframework.util.StringUtils;

/**
 * Created by VietHoa on 16/01/2017.
 */
public class UserService {

    private static final long EXPIRE_TIME_MILLISECOND = 24 * 60 * 60 * 1000;

    public synchronized User register(String email, String password, String name, String phone, String address) throws Exception {
        if (StringUtils.isEmpty(email)) {
            throw new Exception("Missing email");
        }
        if (StringUtils.isEmpty(password)) {
            throw new Exception("Missing password");
        }
        if (StringUtils.isEmpty(name)) {
            throw new Exception("Missing name");
        }
        if (StringUtils.isEmpty(phone)) {
            throw new Exception("Missing phone");
        }
        UserDao userDao = new UserDao();
        if (userDao.isExist(email)) {
            throw new Exception("You already have an account with this email");
        }

        User user = new User(email, password, name, phone, address);
        Number userID = userDao.insert(user);
        if (userID != null) {
            user.setId(userID.longValue());
        }

        AuthenticationService authenticationService = new AuthenticationService();
        user.setToken(authenticationService.createJsonWebToken(
                String.valueOf(user.getId()),
                user.getEmail(),
                password,
                EXPIRE_TIME_MILLISECOND
        ));

        return user;
    }

    public synchronized User signIn(String email, String password) throws Exception {
        if (StringUtils.isEmpty(email)) {
            throw new Exception("Missing email");
        }
        if (StringUtils.isEmpty(password)) {
            throw new Exception("Missing password");
        }
        UserDao userDao = new UserDao();
        if (!userDao.isExist(email)) {
            throw new Exception("You don't have an account with this email");
        }

        User user = userDao.signIn(email, password);
        if (user == null) {
            throw new Exception("Email or password incorrect");
        }

        AuthenticationService authenticationService = new AuthenticationService();
        user.setToken(authenticationService.createJsonWebToken(
                String.valueOf(user.getId()),
                user.getEmail(),
                password,
                EXPIRE_TIME_MILLISECOND
        ));

        return user;
    }
}
