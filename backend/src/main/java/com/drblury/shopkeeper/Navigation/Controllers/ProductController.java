package com.drblury.shopkeeper.Navigation.Controllers;

import com.drblury.shopkeeper.ExcelConverter.ProductTableReader;
import com.drblury.shopkeeper.dto.ProductDTO;
import com.drblury.shopkeeper.model.Product;
import com.drblury.shopkeeper.repos.ProductDTORepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/excelreader")
public class ProductController {

    @Autowired
    ProductDTORepository productDTORepository;

    Logger log = LoggerFactory.getLogger(ProductController.class);

    @RequestMapping("/getProducts")
    public List<ProductDTO> getProducts() {
        List<ProductDTO> productList = new ArrayList<>();
        for (ProductDTO product : productDTORepository.findAll()) {
            log.info("Found a product: " + product.toString());
            productList.add(product);
        }
        return  productList;
    }

    @RequestMapping("/getFromExcel")
    public List<Product> getFromExcel() {
        List<Product> productList = ProductTableReader.read("C:\\Users\\Julian\\Documents\\GitHub\\Shopkeeper\\backend\\src\\main\\resources\\ExcelFiles\\pricing.xlsx");
        return productList;
    }
}
