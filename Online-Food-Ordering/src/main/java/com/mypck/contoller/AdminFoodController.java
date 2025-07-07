package com.mypck.contoller;

import com.mypck.model.Food;
import com.mypck.model.Restaurant;
import com.mypck.model.User;
import com.mypck.request.CreateFoodRequest;
import com.mypck.response.MessageResponse;
import com.mypck.service.FoodService;
import com.mypck.service.RestaurantService;
import com.mypck.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest request,
                                           @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant = restaurantService.findRestaurantById(request.getRestaurantId());
        Food food = foodService.createFood(request,request.getCategory(),restaurant);
        return  new ResponseEntity<>(food, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        foodService.deleteFood(id);
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("food deleted successfully..");
        return  new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvaibilityStatus(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        Food food = foodService.updateAvailibilityStatus(id);

        return  new ResponseEntity<>(food,HttpStatus.CREATED);
    }
}
