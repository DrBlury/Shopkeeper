package com.drblury.shopkeeper.Navigation.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import com.drblury.shopkeeper.ExcelConverter.ProductTableReader;
import com.drblury.shopkeeper.dto.ProductDTO;
import com.drblury.shopkeeper.dto.ShoppingCart;
import com.drblury.shopkeeper.mapper.ProductToProductDTOMapper;
import com.drblury.shopkeeper.model.Product;
import com.drblury.shopkeeper.repos.ProductDTORepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sun.xml.fastinfoset.util.StringArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.drblury.shopkeeper.DemoApplication;
import com.drblury.shopkeeper.entities.Book;
import com.drblury.shopkeeper.repos.BookRepository;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ApiController {

    Logger log = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    ProductTableReader productTableReader;

    @Autowired
    ProductDTORepository productDTORepository;

    private List<ShoppingCart> shoppingCartList = new ArrayList<>();

    @RequestMapping("/getCart")
    public ShoppingCart getCart(@RequestParam(value= "username") String username) {
        return shoppingCartList.stream()
                .filter(c -> c.getUsername().equals(username))
                .findAny()
                .orElse(null);
    }

    @RequestMapping("/getCartList")
    public List<String> getCartList() {
        List<String> openCarts = new ArrayList<>();
        for (ShoppingCart openCart : shoppingCartList) {
            openCarts.add(openCart.getUsername());
        }
        return openCarts;
    }

    //TODO make this endpoint decide on new sum of items. Add or remove an item? Maybe via parameter
    @RequestMapping("/addToCart")
    public ShoppingCart addToCart( @RequestParam(value= "id") String itemId,
    @RequestParam(value= "username") String username) {
        int id = Integer.parseInt(itemId);
        ShoppingCart cart = findCartByUsername(username);
        Map<Integer, Integer> productsInCart = cart.getProductList();

        //If there is nothing in the cart, simply return the empty cart
        if (productsInCart == null)
            return cart;

        //Check if product is already in the cart
        if (productsInCart.containsKey(id)) {
            //If so, get the amount and add one
            int amount = productsInCart.get(id);
            amount++;
            productsInCart.put(id, amount);
        } else {
            //Else add the product to the cart with amount of 1
            productsInCart.put(id, 1);
        }

        return cart;
    }

    private ProductDTO findProductById(String id) throws IOException {
        for (ProductDTO productDTO : getProducts()) {
            if (productDTO.getId().toString().equals(id) ) {
                return productDTO;
            }
        }
        return null;
    }

    private ShoppingCart findCartByUsername(String username) {
        for (ShoppingCart cart : shoppingCartList) {
            if (cart.getUsername().equals(username) ) {
                return cart;
            }
        }
        //If the cart is not present, create one
        ShoppingCart newCart = ShoppingCart.builder()
                .productList(new HashMap<>())
                .username(username)
                .build();

        //add it to the List
        shoppingCartList.add(newCart);
        System.out.println("Creating a new cart for customer: " + username);
        return newCart;
    }

    @RequestMapping("/getProducts")
    public List<ProductDTO> getProducts() throws IOException {
        if (productDTORepository.count() <= 0) {
            List<Product> productList = getFromExcel();
            for (Product product : productList) {
                productDTORepository.save(ProductToProductDTOMapper.map(product));
            }
        }

        List<ProductDTO> productDTOList = new ArrayList<>();
        for (ProductDTO product : productDTORepository.findAll()) {
            log.info("Found a product: " + product.toString());
            productDTOList.add(product);
        }
        return productDTOList;
    }

    private List<Product> getFromExcel() throws IOException {
        List<Product> productList = productTableReader.read();
        return productList;
    }

    @RequestMapping("/generateInvoice")
    public String generateInvoice()  {
        return "done.";
    }

    // Forwards all routes to FrontEnd except: '/', '/index.html', '/api', '/api/**'
    // Required because of 'mode: history' usage in frontend routing, see README for further details
    @RequestMapping(value = "{_:^(?!index\\.html|api).$}")
    public String redirectApi() {
        return "forward:/";
    }
}
