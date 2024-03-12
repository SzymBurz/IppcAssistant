package com.wtd.assistant.frontend.print;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.AbstractStreamResource;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.StreamResourceRegistry;
import com.wtd.assistant.frontend.dao.EnterpriseDao;
import com.wtd.assistant.frontend.dao.FileDao;
import com.wtd.assistant.frontend.domain.Enterprise;
import com.wtd.assistant.frontend.service.PrintingService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.security.PermitAll;
import java.io.*;
import java.time.LocalDate;
import java.util.List;

@Service
@PermitAll
public class PrintDialog extends Dialog {

    ComboBox<String> printExtensionCB;
    Button printButton;
    Button downloadButton;
    Anchor downloadAnchor;
    String someJRXMLName;
    LocalDate startDate;
    LocalDate endDate;
    @Setter
    List<Enterprise> enterprisesToPrint;

    @Autowired
    public PrintDialog(PrintingService printingService) {
        this.printExtensionCB = new ComboBox<>("Extension");
        printExtensionCB.setItems("PDF","XLSX");
        this.printButton = new Button("Generuj");
        this.downloadButton = new Button("Pobierz");
        downloadButton.addClickListener((ComponentEventListener<ClickEvent<Button>>) event ->
                UI.getCurrent().getPage().executeJs("$0.click();", downloadAnchor.getElement()));
        downloadButton.setEnabled(false);
        this.someJRXMLName = "EnterpriseList"; //Hardcoded for now
        this.startDate = LocalDate.of(2023,01,01);
        this.endDate = LocalDate.of(2024,01,01);
        this.downloadAnchor = new Anchor();
        downloadAnchor.getElement().setAttribute("download", true);
        printButton.addClickListener(e -> {
            List<Enterprise> data = enterprisesToPrint;
            StreamResource resource = printingService.genPDF("/reports/Enterprises.jrxml", data);
            downloadAnchor.setHref(resource);
            downloadButton.setEnabled(true);
        });

        HorizontalLayout layout = new HorizontalLayout(printExtensionCB,printButton,downloadButton,downloadAnchor);
        layout.setAlignItems(FlexComponent.Alignment.BASELINE);
        add(layout);
    }
}
