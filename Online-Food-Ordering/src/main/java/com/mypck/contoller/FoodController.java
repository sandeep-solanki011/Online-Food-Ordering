package com.mypck.contoller;

import com.mypck.model.Food;
import com.mypck.model.Restaurant;
import com.mypck.model.User;
import com.mypck.request.CreateFoodRequest;
import com.mypck.service.FoodService;
import com.mypck.service.RestaurantService;
import com.mypck.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {

    @Autowired
    private UserService userService;

    @Autowired
    private FoodService foodService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String name,
                                           @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.searchFood(name);
        return  new ResponseEntity<>(foods, HttpStatus.CREATED);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getRestaurantFood(
            @RequestParam boolean vagetarian,
            @RequestParam boolean seasonal,
            @RequestParam boolean nonveg,
            @PathVariable Long restaurantId,
            @RequestParam(required = false) String food_category,
            @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        List<Food> foods = foodService.getRestaurantsFood(restaurantId,vagetarian,nonveg,seasonal,food_category);
        return  new ResponseEntity<>(foods, HttpStatus.OK);
    }

}
