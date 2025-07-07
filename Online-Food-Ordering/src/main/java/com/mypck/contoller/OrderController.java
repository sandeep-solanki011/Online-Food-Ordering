package com.mypck.contoller;


import com.mypck.model.CartItem;
import com.mypck.model.Order;
import com.mypck.model.User;
import com.mypck.repository.OrderRepository;
import com.mypck.request.OrderRequest;
import com.mypck.service.OrderService;
import com.mypck.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;


    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request,
                                                @RequestHeader("Authorization")String jwt)throws Exception{
        User user = userService.findUserByJwtToken(jwt);

        Order order = orderService.createOrder(request,user);
        return  new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(@RequestHeader("Authorization")String jwt)throws Exception{
        User user = userService.findUserByJwtToken(jwt);

        List<Order> orders = orderService.getUserOrder(user.getId());
        return  new ResponseEntity<>(orders, HttpStatus.OK);
    }

}
