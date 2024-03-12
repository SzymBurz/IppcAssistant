package com.wtd.assistant.frontend.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import com.wtd.assistant.frontend.dao.EnterpriseDao;
import com.wtd.assistant.frontend.domain.Enterprise;
import com.wtd.assistant.frontend.generator.EnterpriseSummaryGenerator;
import com.wtd.assistant.frontend.service.EnterpriseService;
import java.util.ArrayList;
import java.util.List;
import com.wtd.assistant.frontend.print.PrintDialog;

@Route(value = "Enterprises", layout = AssistantAppLayout.class)
public class EnterprisesView extends VerticalLayout {

    final private EnterpriseService enterpriseService;
    final private EnterpriseDao enterpriseDao;
    private Grid<Enterprise> grid;
    private Grid<Enterprise> exportGrid;
    private TextField filter;
    private Button addNewBtn;
    private DatePicker datePicker;
    private DatePicker endDatePicker;
    private List<Enterprise> exportSelection;
    private H2 gridHeader;
    private H2 exportGridHeader;
    private Button exportButton;
    private Button printButton;
    private TextArea output;
    private EnterpriseSummaryGenerator generator;
    private PrintDialog printDialog;

    public EnterprisesView(EnterpriseDao enterpriseDao, EnterpriseService enterpriseService, EnterpriseSummaryGenerator generator, PrintDialog printDialog) {
        this.enterpriseService = enterpriseService;
        this.enterpriseDao = enterpriseDao;
        this.grid = new Grid<>(Enterprise.class);
        this.exportGrid = new Grid<>(Enterprise.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("New Enterprise", VaadinIcon.PLUS.create());
        this.datePicker  = new DatePicker();
        this.endDatePicker  = new DatePicker();
        this.exportSelection = new ArrayList<>();
        this.gridHeader = new H2("Enterprises");
        this.exportGridHeader = new H2("Export");
        this.exportButton = new Button("Export Data");
        this.printButton = new Button("Print", VaadinIcon.PRINT.create());
        this.output = new TextArea();
        this.generator = generator;
        this.printDialog = printDialog;

        configureFilter();
        configureAddNewButton();
        configureExportButton();
        configurePrintButton();
        configureGrid();
        configureDatePicker();
        configureEndDatePicker();
        configureGridContextMenu();
        configureExportGrid();
        configureExportGridMenu();
        configureOutputArea();


        HorizontalLayout layout = new HorizontalLayout(filter, datePicker, endDatePicker, addNewBtn);
        VerticalLayout layout2 = new VerticalLayout(addNewBtn);
        HorizontalLayout layout3 = new HorizontalLayout(exportButton, printButton);
        layout2.setAlignItems(FlexComponent.Alignment.END);
        layout3.setAlignItems(FlexComponent.Alignment.END);
        add(gridHeader, layout, grid, layout2, exportGridHeader, exportGrid, layout3, output);

    }

    private void configureOutputArea() {
        output.setWidthFull();
    }

    private void configureExportGridMenu() {
        GridContextMenu<Enterprise> selectionGridMenu = exportGrid.addContextMenu();
        selectionGridMenu.addItem("export to CSV", event -> {
            System.out.println((exportSelection));
        });
        selectionGridMenu.addItem("Remove from table", event -> {
            exportSelection.remove(exportGrid.asSingleSelect().getValue());
            exportGrid.getDataProvider().refreshAll();
        });
    }

    private void configureExportGrid() {
        exportGrid.setHeight("200px");
        exportGrid.setColumns("name", "ippcCode", "expiryDate");
        exportGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        exportGrid.setItems(exportSelection);
    }

    private void configureGridContextMenu() {
        GridContextMenu<Enterprise> menu = grid.addContextMenu();
        menu.addItem("View", event -> {
            menu.getUI().ifPresent(ui -> ui.navigate(EnterpriseView.class, Integer.toString(grid.asSingleSelect().getValue().getEnterpriseId())));
        });
        menu.addItem("Add to export", event -> {
            exportSelection.add(grid.asSingleSelect().getValue());
            exportGrid.getDataProvider().refreshAll();
        });
        menu.addItem("Create Audit", event -> {
            menu.getUI().ifPresent(ui -> ui.navigate(AddNewAuditView.class));
        });
    }

    private void configureGrid() {
        grid.setHeight("200px");
        grid.setColumns("name", "ippcCode", "expiryDate");
        grid.getColumnByKey("expiryDate").setAutoWidth(true);
        grid.getColumnByKey("ippcCode").setAutoWidth(true);
        grid.getColumnByKey("name").setAutoWidth(true);
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setItems(enterpriseService.findEnterprisesByCriteria(null, null, null));
    }

    private void configureAddNewButton() {
        addNewBtn.addClickListener(e -> UI.getCurrent().navigate(NewEnterpriseView.class));
    }

    private void configureExportButton() {
        exportButton.addClickListener(e -> generator.generateSummary(exportSelection, output));
    }

    private void configurePrintButton() {
        printButton.addClickListener(e -> {
            printDialog.setEnterprisesToPrint(exportSelection);
            printDialog.open();
        });
    }

    private void configureFilter() {
        filter.setPlaceholder("Filter by name...");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> updateList());
    }

    private void updateList() {
        grid.setItems(enterpriseService.findEnterprisesByCriteria(filter.getValue(), datePicker.getValue(), endDatePicker.getValue()));

    }

    private void configureDatePicker() {
        datePicker.setPlaceholder("Filter by expiry date");
        datePicker.addValueChangeListener(e -> updateList());
    }

    private void configureEndDatePicker() {
        endDatePicker.setPlaceholder("Filter by expiry date");
        endDatePicker.addValueChangeListener(e -> updateList());
    }
}


