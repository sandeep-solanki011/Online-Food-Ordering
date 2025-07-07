package com.mypck.request;

import com.mypck.model.Address;
import lombok.Data;

@Data
public class OrderRequest {

private Long restaurantId;

private Address deliveryAddress;

}
