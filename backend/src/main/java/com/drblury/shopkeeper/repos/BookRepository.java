package com.drblury.shopkeeper.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.drblury.shopkeeper.entities.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    Book findById(long id);
}

