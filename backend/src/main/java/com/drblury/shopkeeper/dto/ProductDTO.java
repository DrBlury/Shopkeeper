package com.drblury.shopkeeper.dto;

import com.drblury.shopkeeper.model.Product;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="PRODUCTDTO")
public class ProductDTO {

    public ProductDTO() {}

    @Id
    @GeneratedValue
    private Long id;

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
    private Double businessCustomerNetto;
    private Double privateCustomerNetto;
    private Double privateCustomerBrutto;
    private Double businessCustomerBrutto;


    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", eanCode='" + eanCode + '\'' +
                ", imageSet=" + imageSet +
                ", productName='" + productName + '\'' +
                ", producer='" + producer + '\'' +
                ", buyPriceNetto=" + buyPriceNetto +
                ", multiplyFactor=" + multiplyFactor +
                ", description='" + description + '\'' +
                ", comment='" + comment + '\'' +
                ", stock=" + stock +
                ", tags=" + tags +
                ", businessCustomerNetto=" + businessCustomerNetto +
                ", privateCustomerNetto=" + privateCustomerNetto +
                ", privateCustomerBrutto=" + privateCustomerBrutto +
                ", businessCustomerBrutto=" + businessCustomerBrutto +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEanCode() {
        return eanCode;
    }

    public void setEanCode(String eanCode) {
        this.eanCode = eanCode;
    }

    public Integer getImageSet() {
        return imageSet;
    }

    public void setImageSet(Integer imageSet) {
        this.imageSet = imageSet;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Double getBuyPriceNetto() {
        return buyPriceNetto;
    }

    public void setBuyPriceNetto(Double buyPriceNetto) {
        this.buyPriceNetto = buyPriceNetto;
    }

    public Double getMultiplyFactor() {
        return multiplyFactor;
    }

    public void setMultiplyFactor(Double multiplyFactor) {
        this.multiplyFactor = multiplyFactor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getTags() {
        return tags;
    }

    public void setTags(Integer tags) {
        this.tags = tags;
    }

    public Double getBusinessCustomerNetto() {
        return businessCustomerNetto;
    }

    public void setBusinessCustomerNetto(Double businessCustomerNetto) {
        this.businessCustomerNetto = businessCustomerNetto;
    }

    public Double getPrivateCustomerNetto() {
        return privateCustomerNetto;
    }

    public void setPrivateCustomerNetto(Double privateCustomerNetto) {
        this.privateCustomerNetto = privateCustomerNetto;
    }

    public Double getPrivateCustomerBrutto() {
        return privateCustomerBrutto;
    }

    public void setPrivateCustomerBrutto(Double privateCustomerBrutto) {
        this.privateCustomerBrutto = privateCustomerBrutto;
    }

    public Double getBusinessCustomerBrutto() {
        return businessCustomerBrutto;
    }

    public void setBusinessCustomerBrutto(Double businessCustomerBrutto) {
        this.businessCustomerBrutto = businessCustomerBrutto;
    }
}
