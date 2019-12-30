package com.drblury.shopkeeper.Navigation.Controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import com.drblury.shopkeeper.ExcelConverter.ProductTableReader;
import com.drblury.shopkeeper.ExcelConverter.ReadFromExcel;
import com.drblury.shopkeeper.dto.ProductDTO;
import com.drblury.shopkeeper.dto.ShoppingCart;
import com.drblury.shopkeeper.mapper.ProductToProductDTOMapper;
import com.drblury.shopkeeper.model.Product;
import com.drblury.shopkeeper.repos.ProductDTORepository;
import com.fasterxml.jackson.databind.ObjectMapper;

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
public class TestController {



    @Value("classpath:Dashboard.vue")
    private Resource dashboardVue;

    Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ProductDTORepository productDTORepository;

    private List<ShoppingCart> shoppingCartList = new ArrayList<>();

    @RequestMapping("/addBook")
    public void addBook( @RequestParam(value= "book") String transferBook)
    throws IOException {
        final Book book = new ObjectMapper().readValue(transferBook, Book.class);
        bookRepository.save(book);
        bookRepository.findAll().forEach(x ->log.info(x.toString()));
    }

    @RequestMapping("/loadDashboardVue")
    public String loadDashboardVue() throws IOException {
        ClassLoader classLoader = DemoApplication.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("Dashboard.vue")).getFile());
        String content = new String(Files.readAllBytes(file.toPath()));
        log.info(content);
        return content;
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

    private ProductDTO findProductById(String id) {
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
    public List<ProductDTO> getProducts() {
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

    private List<Product> getFromExcel() {
        List<Product> productList = ProductTableReader.read("C:\\Users\\Julian\\Documents\\GitHub\\Shopkeeper\\backend\\src\\main\\resources\\ExcelFiles\\pricing.xlsx");
        return productList;
    }

    @RequestMapping("/generateInvoice")
    public String generateInvoice()  {
        return "done.";
    }

    @RequestMapping("/hello")
    public List<Book> helloWorld() {
        List<Book> bookList = new ArrayList<>();
        for(Book book : bookRepository.findAll()) {
            log.info("Found a book: " + book.toString());
            bookList.add(book);
        }
        return bookList;
    }

    // Forwards all routes to FrontEnd except: '/', '/index.html', '/api', '/api/**'
    // Required because of 'mode: history' usage in frontend routing, see README for further details
    @RequestMapping(value = "{_:^(?!index\\.html|api).$}")
    public String redirectApi() {
        return "forward:/";
    }
}
