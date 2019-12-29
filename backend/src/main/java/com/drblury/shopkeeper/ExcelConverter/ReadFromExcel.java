package com.drblury.shopkeeper.ExcelConverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadFromExcel {

    public static Map<Integer, List<Cell>> readFile(String path) throws IOException {
        FileInputStream file = new FileInputStream(new File(path));

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
        return data;
    }
}