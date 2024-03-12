package com.wtd.assistant.frontend.service;

import com.vaadin.flow.server.StreamResource;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

@Service
@Setter
@Getter
public class PrintingService {

    public PrintingService() {
    }

    public StreamResource genPDF(InputStream templateInputStream, List data){
        try {

            JasperReport jasperReport = JasperCompileManager.compileReport(templateInputStream);
            JRDataSource dataSource = new JRBeanCollectionDataSource(data);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, bos);
            ByteArrayInputStream bais = new ByteArrayInputStream(bos.toByteArray());
            StreamResource resource = new StreamResource("123.pdf", () -> bais);

            return resource;

        } catch (JRException e) {
            e.printStackTrace();
        }
        return null;
    }

    public StreamResource genPDF(String hardcodedJRXML, List data){
        try {
            JasperDesign design = JRXmlLoader.load(getClass().getResourceAsStream(hardcodedJRXML));
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JRDataSource dataSource = new JRBeanCollectionDataSource(data);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, bos);
            ByteArrayInputStream bais = new ByteArrayInputStream(bos.toByteArray());
            StreamResource resource = new StreamResource("123.pdf", () -> bais);

            return resource;

        } catch (JRException e) {
            e.printStackTrace();
        }
        return null;
    }
}
