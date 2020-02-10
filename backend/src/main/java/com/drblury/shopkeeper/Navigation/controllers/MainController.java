package com.drblury.shopkeeper.Navigation.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drblury.shopkeeper.model.Product;

import com.drblury.shopkeeper.ExcelConverter.ProductTableReader;

@RestController
public class MainController {

    @Autowired
    ProductTableReader productTableReader;

    @RequestMapping(name = "/home")
    public List<Product> home() throws IOException {
        return productTableReader.read();
    }
}
