package com.drblury.shopkeeper.Navigation.Controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drblury.shopkeeper.entities.Product;

import com.drblury.shopkeeper.ExcelConverter.ProductTableReader;

@RestController
public class maincontroller {

    @RequestMapping(name = "/home")
    public List<Product> home() throws IOException {
        final String path = "C:\\Users\\Julian\\Desktop\\gitrepos\\Shopkeeper\\shopkeeper\\shopkeeper\\backend\\src\\main\\resources\\ExcelFiles\\pricing.xlsx";
        return ProductTableReader.read(path);
    }
}
