package com.wtd.assistant.frontend.generator;


import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextArea;
import com.wtd.assistant.frontend.domain.Enterprise;

import java.io.StringWriter;
import java.util.List;
import java.util.stream.Stream;

public class CsvGenerator {

    public CsvGenerator() {
    }

    public static void export(List<Enterprise> enterprisesToExport, TextArea result) {

        StringWriter output = new StringWriter();
        Stream<Enterprise> enterprisesToExportStream =enterprisesToExport.stream();
        StatefulBeanToCsv<Enterprise> writer = new StatefulBeanToCsvBuilder<Enterprise>(output).build();
        try {
            writer.write(enterprisesToExportStream);
        } catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            output.write("An error occured during writing: " + e.getMessage());
        }

        result.setValue(output.toString());
    }
}
