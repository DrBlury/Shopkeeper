package com.drblury.shopkeeper.dto;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class ShoppingCart {
    private String username;
    private Map<Integer, Integer> productList = new HashMap<>();
}
