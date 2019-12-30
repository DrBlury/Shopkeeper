package com.drblury.shopkeeper.model;

import com.drblury.shopkeeper.ExcelConverter.ProductTableReader;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

    private double getBrutto(double nettoPrice) {
        return nettoPrice * 1.19;
    }

    public Double getBusinessCustomerNetto() {
        return ProductTableReader.round(buyPriceNetto * multiplyFactor, 2);
    }

    public Double getPrivateCustomerNetto() {
        return ProductTableReader.round(getBusinessCustomerNetto() * 1.1, 2);
    }

    public Double getPrivateCustomerBrutto() {
        return ProductTableReader.round(getBrutto(getPrivateCustomerNetto()), 2);
    }

    public Double getBusinessCustomerBrutto() {
        return ProductTableReader.round(getBrutto(getBusinessCustomerNetto()), 2);
    }
}
