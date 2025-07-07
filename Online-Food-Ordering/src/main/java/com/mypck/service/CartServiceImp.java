package com.mypck.service;

import com.mypck.model.Cart;
import com.mypck.model.CartItem;
import com.mypck.model.Food;
import com.mypck.model.User;
import com.mypck.repository.CartItemRepository;
import com.mypck.repository.CartRepository;
import com.mypck.repository.FoodRepository;
import com.mypck.request.AddCartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CartServiceImp  implements CartService{

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private FoodService foodService;

    @Override
    public CartItem addItemToCart(AddCartItemRequest request, String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Food food = foodService.findFoodById(request.getFoodId());

        Cart cart = cartRepository.findByCustomerId(user.getId());
       for (CartItem cartItem : cart.getItem()){
           if(cartItem.getFood().equals(food)){
               int newQuantity = cartItem.getQuantity()+request.getQuantity();
             return  updateCartItemQuantity(cartItem.getId(), newQuantity);
           }
       }

       CartItem newCartItem = new CartItem();
       newCartItem.setFood(food);
       newCartItem.setCart(cart);
       newCartItem.setQuantity(request.getQuantity());
       newCartItem.setIngredients(request.getIngredients());
       newCartItem.setTotalPrice(request.getQuantity()*food.getPrice());

       CartItem saveCartItem = cartItemRepository.save(newCartItem);

       cart.getItem().add(saveCartItem);
       return saveCartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {

        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);

        if (cartItemOptional.isEmpty()){
            throw new Exception("cart item not found..");
        }
        CartItem item  =cartItemOptional.get();
        item.setQuantity(quantity);

//        5
        item.setTotalPrice(item.getFood().getPrice()*quantity);

        return cartItemRepository.save(item);
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Cart cart = cartRepository.findByCustomerId(user.getId());

        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);

        if (cartItemOptional.isEmpty()){
            throw new Exception("cart item not found..");
        }

        CartItem item = cartItemOptional.get();

        cart.getItem().remove(item);
        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cart) throws Exception {

       Long total = 0L;

       for (CartItem cartItem:cart.getItem()){
           total+=cartItem.getFood().getPrice()*cartItem.getQuantity();
       }
        return total;
    }

    @Override
    public Cart findCartById(Long id) throws Exception {

        Optional<Cart> optionalCart = cartRepository.findById(id);
        if(optionalCart.isEmpty()){
            throw new Exception("cart not found with id"+id);
        }
        return optionalCart.get();
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {

      //  User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartRepository.findByCustomerId(userId);
        cart.setTotal(calculateCartTotals(cart));
         return cart;
    }

    @Override
    public Cart clearCart(Long userId) throws Exception {

//          User user = userService.findUserByJwtToken(jwt);
          Cart cart = findCartByUserId(userId);
          cart.getItem().clear();

        return cartRepository.save(cart);
    }
}
