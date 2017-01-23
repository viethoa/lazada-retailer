package com.viethoa.controllers;

import com.viethoa.models.Authentication;
import com.viethoa.models.ErrorModel;
import com.viethoa.services.AuthenticationService;
import com.viethoa.services.ErrorService;
import com.viethoa.services.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by VietHoa on 19/01/2017.
 */
@RestController
public class StoreController {

    private final StoreService storeService = new StoreService();
    private final ErrorService errorService = new ErrorService();
    private final AuthenticationService authenticationService = new AuthenticationService();

    @RequestMapping(value = "/store/create", method = RequestMethod.POST)
    public ResponseEntity register(@RequestHeader(value = "token", defaultValue = "") String token,
                                   @RequestParam(value = "store_name", defaultValue = "") String storeName) {
        try {
            // Token checking
            Authentication authentication = authenticationService.parseJWT(token);
            if (authentication == null || authentication.getUser() == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(errorService.tokenExpired());
            }
            if (authenticationService.isExpired(authentication)) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(errorService.tokenExpired());
            }

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(storeService.create(authentication.getUser().getId(), storeName));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorService.badRequest(ex));
        }
    }

    @RequestMapping(value = "/store/get_all_stores", method = RequestMethod.GET)
    public ResponseEntity getAll(@RequestHeader(value = "token", defaultValue = "") String token,
                                   @RequestParam(value = "user_id", defaultValue = "") long userID) {
        try {
            // Token checking
            Authentication authentication = authenticationService.parseJWT(token);
            if (authentication == null || authentication.getUser() == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(errorService.tokenExpired());
            }
            if (authenticationService.isExpired(authentication)) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(errorService.tokenExpired());
            }

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(storeService.getAll(userID));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorService.badRequest(ex));
        }
    }
}
