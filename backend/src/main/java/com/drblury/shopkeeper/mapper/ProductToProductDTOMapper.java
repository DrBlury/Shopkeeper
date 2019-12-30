package com.drblury.shopkeeper.mapper;

import com.drblury.shopkeeper.dto.ProductDTO;
import com.drblury.shopkeeper.model.Product;

public class ProductToProductDTOMapper {

    public static ProductDTO map(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProducer(product.getProducer());
        productDTO.setProductName(product.getProductName());
        productDTO.setTags(product.getTags());
        productDTO.setDescription(product.getDescription());
        productDTO.setComment(product.getComment());
        productDTO.setStock(product.getStock());
        productDTO.setMultiplyFactor(product.getMultiplyFactor());
        productDTO.setEanCode(product.getEanCode());
        productDTO.setBuyPriceNetto(product.getBuyPriceNetto());
        productDTO.setBusinessCustomerBrutto(product.getBusinessCustomerBrutto());
        productDTO.setBusinessCustomerNetto(product.getBusinessCustomerNetto());
        productDTO.setPrivateCustomerBrutto(product.getPrivateCustomerBrutto());
        productDTO.setPrivateCustomerNetto(product.getPrivateCustomerNetto());

        return productDTO;
    }
}
