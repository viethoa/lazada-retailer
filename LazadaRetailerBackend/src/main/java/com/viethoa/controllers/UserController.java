package com.viethoa.controllers;

import com.viethoa.models.ErrorModel;
import com.viethoa.services.ErrorService;
import com.viethoa.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by VietHoa on 16/01/2017.
 */
@RestController
public class UserController {

    private final UserService userService = new UserService();
    private final ErrorService errorService = new ErrorService();

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestParam(value = "email", defaultValue = "") String email,
                                   @RequestParam(value = "password", defaultValue = "") String password,
                                   @RequestParam(value = "name", defaultValue = "") String name,
                                   @RequestParam(value = "phone", defaultValue = "") String phone,
                                   @RequestParam(value = "address", defaultValue = "") String address) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(userService.register(email, password, name, phone, address));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorService.badRequest(ex));
        }
    }

    @RequestMapping(value = "/user/sign_in", method = RequestMethod.POST)
    public ResponseEntity signIn(@RequestParam(value = "email", defaultValue = "") String email,
                                   @RequestParam(value = "password", defaultValue = "") String password) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(userService.signIn(email, password));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorService.badRequest(ex));
        }
    }
}
