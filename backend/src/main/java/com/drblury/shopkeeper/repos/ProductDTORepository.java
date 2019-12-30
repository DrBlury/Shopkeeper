package com.drblury.shopkeeper.repos;

import com.drblury.shopkeeper.dto.ProductDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDTORepository extends CrudRepository<ProductDTO, Long> {
    ProductDTO findById(long id);
}

