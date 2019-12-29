package com.drblury.shopkeeper.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="BOOK")
public class Book {

    public Book() {}

    public Book(String name, Float price) {
        this.name = name;
        this.price = price;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Float price;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    // standard constructors

    // standard getters and setters
}