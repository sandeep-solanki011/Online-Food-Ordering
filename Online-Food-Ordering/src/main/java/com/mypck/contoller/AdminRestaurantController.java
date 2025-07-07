package com.mypck.contoller;


import com.mypck.model.Restaurant;
import com.mypck.model.User;
import com.mypck.request.CreateRestaurantRequest;
import com.mypck.response.MessageResponse;
import com.mypck.service.RestaurantService;
import com.mypck.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurants")
public class AdminRestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Restaurant> createRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt) throws Exception {
            System.out.println("========"+req);
        User user  = userService.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantService.createRestauarant(req,user);
//        System.out.println("Its Done............");
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);

    }
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @RequestBody CreateRestaurantRequest req,
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id) throws Exception {

        User user  = userService.findUserByJwtToken(jwt);

        Restaurant restaurant = restaurantService.updateRestaurant(id,req);
        return new ResponseEntity<>(restaurant, HttpStatus.CREATED);

    }
//
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteRestaurant(
            @RequestBody CreateRestaurantRequest req,
    		@RequestHeader("Authorization") String jwt,
            @PathVariable Long id) throws Exception {

        User user  = userService.findUserByJwtToken(jwt);

        restaurantService.deleteRestaurant(id);

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("restaurant deleted successfully..");

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);

    }
//
    @PutMapping("/{id}/status")
    public ResponseEntity<Restaurant> updateRestaurantStatus(

            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id) throws Exception {

          User user  = userService.findUserByJwtToken(jwt);

          Restaurant restaurant =  restaurantService.updateRestaurantStatus(id);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);

    }
//
    @GetMapping("/user")
    public ResponseEntity<Restaurant> findRestaurantByUserId(
            @RequestHeader("Authorization") String jwt
           ) throws Exception {

        User user  = userService.findUserByJwtToken(jwt);

        Restaurant restaurant =  restaurantService.getRestaurantByUserId(user.getId());

        return new ResponseEntity<>(restaurant, HttpStatus.OK);

    }
}
