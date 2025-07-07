package com.mypck.service;

import com.mypck.model.IngredientCategory;
import com.mypck.model.IngredientsItem;

import java.util.List;

public interface IngredientsService {

    public IngredientCategory createIngredientCategory(String name,Long restaurantId)throws Exception;

    public IngredientCategory findIngredientCategoryById(Long id) throws Exception;

    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id)throws  Exception;

    public IngredientsItem createIngredientItem(Long restaurantId,String ingredientName,Long category) throws Exception;

    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId);

    public IngredientsItem updateStock(Long id) throws Exception;



}
