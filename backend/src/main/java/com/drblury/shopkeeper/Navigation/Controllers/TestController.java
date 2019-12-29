package com.drblury.shopkeeper.Navigation.Controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
