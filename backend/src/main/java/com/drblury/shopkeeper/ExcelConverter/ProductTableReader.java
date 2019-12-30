package com.drblury.shopkeeper.ExcelConverter;

import com.drblury.shopkeeper.model.Product;

import org.apache.poi.ss.usermodel.Cell;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
                if (entry.getValue().get(1).getStringCellValue().equals("")){
                    return productList;
                }
                productList.add (Product.builder()
                        .id(counter.incrementAndGet())
                        .producer(entry.getValue().get(0).toString())
                        .productName(entry.getValue().get(1).toString())
                        .buyPriceNetto(round(entry.getValue().get(4).getNumericCellValue(), 2))
                        .multiplyFactor(round(entry.getValue().get(5).getNumericCellValue(), 2))
                        .build());
            }

        }
        System.out.println(productList);
        return productList;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
