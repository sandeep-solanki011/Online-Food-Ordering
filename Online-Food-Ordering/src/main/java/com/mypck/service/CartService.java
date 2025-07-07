package com.mypck.service;

import com.mypck.model.Cart;
import com.mypck.model.CartItem;
import com.mypck.model.User;
import com.mypck.request.AddCartItemRequest;

public interface CartService {

    public CartItem addItemToCart(AddCartItemRequest request, String jwt) throws Exception;

    public CartItem updateCartItemQuantity(Long cartItemId,int quantity) throws  Exception;

    public Cart removeItemFromCart(Long cartItemId,String jwt)throws Exception;

     public Long calculateCartTotals(Cart cart)throws Exception;

    public Cart findCartById(Long id)throws Exception;

    public Cart findCartByUserId(Long userID)throws Exception;

    public Cart clearCart(Long userId)throws Exception;


}
