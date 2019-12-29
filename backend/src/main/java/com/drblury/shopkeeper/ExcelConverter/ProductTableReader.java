package com.drblury.shopkeeper.ExcelConverter;

import com.drblury.shopkeeper.entities.Product;

import org.apache.poi.ss.usermodel.Cell;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductTableReader {

    public static List<Product> read(String path) {

        // Read from Excel sheet
        Map<Integer, List<Cell>> table = Optional.ofNullable(path).map(pathToExcelsheet -> {
            try {
                return ReadFromExcel.readFile(pathToExcelsheet); // Map<Integer, List<Cell>>
            } catch (final IOException e) {
                e.printStackTrace();
            }
            return null;
        }).orElse(null);

        // Convert the Excel sheet to a List of Products
        return createNewProducts(table);
    }

    private static List<Product> createNewProducts(final Map<Integer, List<Cell>> table) {
        List<Product> productList = new ArrayList<>();

        AtomicInteger counter = new AtomicInteger(0);
        for (final Map.Entry<Integer, List<Cell>> entry : table.entrySet()) {
            if (entry.getKey() > 0) {
                productList.add (Product.builder()
                        .id(counter.incrementAndGet())
                        .producer(entry.getValue().get(0).toString())
                        .productName(entry.getValue().get(1).toString())
                        .buyPriceNetto(entry.getValue().get(4).getNumericCellValue())
                        .multiplyFactor(entry.getValue().get(5).getNumericCellValue())
                        .build());
            }
        }
        return productList;
    }
}
