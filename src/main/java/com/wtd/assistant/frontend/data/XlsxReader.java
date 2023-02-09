package com.wtd.assistant.frontend.data;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@Component
public class XlsxReader {

    public void readXlsx(String filename) throws IOException {

        String fileLocation = "src/main/resources/KRAJOWY-WYKAZ-KODOW-IPPC-09.12.2022.xlsx";

        if(Files.exists(Path.of("src/main/resources/"+ filename))) {
            fileLocation = "src/main/resources/"+ filename;
        } else {
            System.out.println("File not exiting at given directory. Executing reading for: " + fileLocation);
        }

        FileInputStream file = new FileInputStream(fileLocation);
        Workbook workbook = new XSSFWorkbook(file);

        Sheet sheet = workbook.getSheetAt(0);

        Map<Integer, List<String>> data = new HashMap<>();

        int i = 0;

        for (Row row : sheet) {

            data.put(i, new ArrayList<String>());

            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case STRING: {
                        data.get(i).add(cell.getRichStringCellValue().getString());
                        break;
                    }
                    case NUMERIC: {
                        if (DateUtil.isCellDateFormatted(cell)) {
                            data.get(i).add(cell.getDateCellValue() + "");
                        } else {
                            data.get(i).add(cell.getNumericCellValue() + "");
                        }
                        break;
                    }
                    case BOOLEAN: {
                        data.get(i).add(cell.getBooleanCellValue() + "");
                        break;
                    }
                    case FORMULA: {
                        data.get(i).add(cell.getCellFormula() + "");
                        break;
                    }
                    default: data.get(i).add(" ");
                }
            }
            i++;
        }

        /*
        data.forEach((key, value) -> {
            System.out.println("row: " + key);
            for(String s: value) {
                System.out.println(s);
            }
        });

         */

    }

    public Map<Integer, List<String>> readXlsxToMap() throws IOException {

        String fileLocation = "src/main/resources/KRAJOWY-WYKAZ-KODOW-IPPC-09.12.2022.xlsx";

        FileInputStream file = new FileInputStream(fileLocation);
        Workbook workbook = new XSSFWorkbook(file);

        Sheet sheet = workbook.getSheetAt(0);

        Map<Integer, List<String>> data = new HashMap<>();

        int i = 0;

        for (Row row : sheet) {

            data.put(i, new ArrayList<String>());

            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case STRING: {
                        data.get(i).add(cell.getRichStringCellValue().getString());
                        break;
                    }
                    case NUMERIC: {
                        if (DateUtil.isCellDateFormatted(cell)) {
                            data.get(i).add(cell.getDateCellValue() + "");
                        } else {
                            data.get(i).add(cell.getNumericCellValue() + "");
                        }
                        break;
                    }
                    case BOOLEAN: {
                        data.get(i).add(cell.getBooleanCellValue() + "");
                        break;
                    }
                    case FORMULA: {
                        data.get(i).add(cell.getCellFormula() + "");
                        break;
                    }
                    default: data.get(i).add(" ");
                }
            }
            i++;
        }

        return data;

    }
}
