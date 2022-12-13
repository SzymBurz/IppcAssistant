package com.wtd.assistant.frontend;

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
    private TextArea output;
    private EnterpriseSummaryGenerator generator;

    //dodać filtrowanie wyników
    //dodać sortowanie: po województwie/misiącu
    //dodać eksport do csv
    //add new prowadzi do widoku wprowadzania -> pierwszy dokment
    //dodanie statusów do przedsiębiorstw: ważny certyfikat/nieważny certyfikat
    //klasa wysyłająca powiadomienia 

    public EnterprisesView(EnterpriseDao enterpriseDao, EnterpriseService enterpriseService, EnterpriseSummaryGenerator generator) {
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
        this.output = new TextArea();
        this.generator = generator;

        configureFilter();
        configureAddNewButton();
        configureExportButton();
        configureGrid();
        configureDatePicker();
        configureEndDatePicker();
        configureGridContextMenu();
        configureExportGrid();
        configureExportGridMenu();
        configureOutputArea();


        HorizontalLayout layout = new HorizontalLayout(filter, datePicker, endDatePicker, addNewBtn);
        VerticalLayout layout2 = new VerticalLayout(addNewBtn);
        VerticalLayout layout3 = new VerticalLayout(exportButton);
        layout2.setAlignItems(FlexComponent.Alignment.END);
        layout3.setAlignItems(FlexComponent.Alignment.END);

        /*
        VerticalLayout generalLayout = new VerticalLayout();
        generalLayout.add(gridHeader, layout, grid, layout2, exportGridHeader, exportGrid, layout3, csvOutput);
        setContent(generalLayout);
         */
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
            menu.getUI().ifPresent(ui -> ui.navigate(AddNewAudit.class));
        });
    }

    private void configureGrid() {
        grid.setHeight("200px");
        grid.setColumns("name", "ippcCode", "expiryDate");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setItems((List<Enterprise>) enterpriseDao.findAll());
    }

    private void configureAddNewButton() {
        addNewBtn.addClickListener(e -> UI.getCurrent().navigate(NewEnterpriseView.class));
    }

    private void configureExportButton() {
        exportButton.addClickListener(e -> generator.generateSummary(exportSelection, output));
    }

    private void configureFilter() {
        filter.setPlaceholder("Filter by name...");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> updateList());
    }

    private void updateList() {

        if (filter.isEmpty() && datePicker.isEmpty() && endDatePicker.isEmpty()) {
            grid.setItems((List<Enterprise>) enterpriseDao.findAll());
        } else if (!filter.isEmpty() && datePicker.isEmpty() && endDatePicker.isEmpty()) {
            grid.setItems(enterpriseDao.findByNameOrIppcCode(filter.getValue()));
        } else if (!filter.isEmpty() && !datePicker.isEmpty() && !endDatePicker.isEmpty()) {
            grid.setItems(enterpriseDao.findByExpiryDateBetweenAndNameLikeIgnoreCaseOrIppcCodeLikeIgnoreCase(datePicker.getValue(), endDatePicker.getValue(), filter.getValue(), filter.getValue()));
        } else if (filter.isEmpty() && !datePicker.isEmpty() && !endDatePicker.isEmpty()) {
            grid.setItems(enterpriseDao.findByExpiryDate(datePicker.getValue(), endDatePicker.getValue()));
        }
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


