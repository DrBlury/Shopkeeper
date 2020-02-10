package com.drblury.shopkeeper.ExcelConverter;

import com.drblury.shopkeeper.Navigation.controllers.MainController;
import com.drblury.shopkeeper.model.Product;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ProductTableReader {

    @Autowired
    private ResourceLoader resourceLoader;

    public List<Product> read() throws IOException {
        Resource resource = this.resourceLoader.getResource("classpath:ExcelFiles/pricing.xlsx");
        File excelfile = resource.getFile();
        FileInputStream file = new FileInputStream(excelfile);
        Workbook workbook = new XSSFWorkbook(file);

        Sheet sheet = workbook.getSheetAt(0);
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

        // This is all collected data
        Map<Integer, List<Cell>> data = new HashMap<>();

        int rowNumber = 0;
        for (Row row : sheet) {
            data.put(rowNumber, new ArrayList<>());
            for (Cell cell : row) {
                data.get(rowNumber).add(cell);
            }
            rowNumber++;
        }
        workbook.close();
        List<Product> productList = new ArrayList<>();

        AtomicInteger counter = new AtomicInteger(0);
        for (final Map.Entry<Integer, List<Cell>> entry : data.entrySet()) {
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
