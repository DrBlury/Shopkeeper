package com.drblury.shopkeeper.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    private int id;
    private String eanCode;
    private Integer imageSet;
    private String productName;
    private String producer;
    private Double buyPriceNetto;
    private Double multiplyFactor;
    private String description;
    private String comment;
    private Integer stock;
    private Integer tags;

    public double getBrutto(double nettoPrice) {
        return nettoPrice * 119;
    }

    public double getBusinessCustomerNetto() {
        return buyPriceNetto * multiplyFactor;
    }

    public double getPrivateCustomerNetto() {
        return getBusinessCustomerNetto() * 1.1;
    }

    public double getPrivateCustomerBrutto() {
        return getBrutto(getPrivateCustomerNetto());
    }

    public double getBusinessCustomerBrutto() {
        return getBrutto(getBusinessCustomerNetto());
    }
}
