package com.viethoa.controllers;

import com.viethoa.models.Authentication;
import com.viethoa.models.Order;
import com.viethoa.models.ResponseModel;
import com.viethoa.services.AuthenticationService;
import com.viethoa.services.ErrorService;
import com.viethoa.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by VietHoa on 20/01/2017.
 */
@RestController
public class OrderController {

    private final OrderService orderService = new OrderService();
    private final ErrorService errorService = new ErrorService();
    private final AuthenticationService authenticationService = new AuthenticationService();

    @RequestMapping(value = "/order/create", method = RequestMethod.POST)
    public ResponseEntity register(@RequestHeader(value = "token", defaultValue = "") String token,
                                   @RequestParam(value = "store_id", defaultValue = "") long storeID,
                                   @RequestParam(value = "order_no", defaultValue = "") String orderNo) {
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

            Order order = orderService.add(storeID, orderNo);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseModel<Order>(order));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorService.badRequest(ex));
        }
    }
}
