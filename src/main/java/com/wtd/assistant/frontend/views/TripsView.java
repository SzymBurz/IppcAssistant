package com.wtd.assistant.frontend.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.wtd.assistant.frontend.dao.TripDao;
import com.wtd.assistant.frontend.dao.UserDao;
import com.wtd.assistant.frontend.domain.Trip;
import com.wtd.assistant.frontend.domain.User;
import com.wtd.assistant.frontend.generator.TripSummaryGenerator;
import com.wtd.assistant.frontend.service.AuditService;
import com.wtd.assistant.frontend.service.EnterpriseService;
import com.wtd.assistant.frontend.service.TripService;
import com.wtd.assistant.frontend.service.UserService;

import java.util.*;

@Route(value = "Trips", layout = AssistantAppLayout.class)
public class TripsView extends VerticalLayout {

    final private TripDao tripDao;
    final private TripService tripService;
    final private AuditService auditService;
    final private UserDao userDao;
    final private EnterpriseService enterpriseService;
    final private UserService userService;
    private H2 header;
    private TextField filter;
    private DatePicker datePicker;
    private DatePicker endDatePicker;
    private ComboBox<User> userBox;
    private Grid<Trip> grid;
    private H2 exportHeader;
    private Grid<Trip> exportGrid;
    private TextArea output;
    private List<Trip> tripsToExport;
    private Button settleNewButton;
    private Set<Trip> tripsToGrid;
    private TextArea textArea;

    Button exportButton;
    TripSummaryGenerator generator;

    public TripsView(TripDao tripDao, TripService tripService, EnterpriseService enterpriseService, AuditService auditService, UserService userService, UserDao userDao, TripSummaryGenerator generator) {
        this.tripDao = tripDao;
        this.tripService = tripService;
        this.enterpriseService = enterpriseService;
        this.auditService = auditService;
        this.userService = userService;
        this.userDao = userDao;
        this.generator = generator;
        this.header = new H2("Trips");
        this.filter = new TextField("Filter by Enterprise/IPPC code");
        this.datePicker = new DatePicker("Filter by period: start");
        this.endDatePicker = new DatePicker("end");
        this.userBox = new ComboBox<>("Filter by auditor");
        this.grid = new Grid<>(Trip.class);
        this.exportHeader = new H2("Export");
        this.exportGrid = new Grid<>(Trip.class);
        this.output = new TextArea("export");
        this.tripsToExport = new ArrayList<>();
        this.settleNewButton = new Button("Settle new Trip");
        this.tripsToGrid= new HashSet<>();
        this.textArea = new TextArea("Output");
        this.exportButton = new Button("Generate summary");


        configureGrid();
        configureExportGrid();
        configureBtn();
        configureFilter();
        configureDatePickers();
        configureUserBox();
        configureExportBtn();
        configureTextArea();

        HorizontalLayout filterLayout = new HorizontalLayout(filter, datePicker, endDatePicker, userBox);
        VerticalLayout btnLayout = new VerticalLayout(settleNewButton);
        btnLayout.setAlignItems(Alignment.END);
        VerticalLayout exportBtnLayout = new VerticalLayout(exportButton);
        exportBtnLayout.setAlignItems(Alignment.END);
        add(filterLayout, grid, btnLayout, exportGrid, exportBtnLayout, textArea);

    }

    private void configureTextArea() {
        textArea.setWidthFull();
    }

    private void configureExportBtn() {
        exportButton.addClickListener(e -> {
            generator.generateSummary(tripsToExport, textArea);
        });
    }

    private void configureUserBox() {
        userBox.setItems((Collection<User>) userDao.findAll());
        userBox.addValueChangeListener(e -> refreshGrid());
    }

    private void configureDatePickers() {
        datePicker.addValueChangeListener(e -> refreshGrid());
        endDatePicker.addValueChangeListener(e -> refreshGrid());
    }

    private void configureFilter() {
        filter.addValueChangeListener(e -> refreshGrid());
        filter.setValueChangeMode(ValueChangeMode.EAGER);
    }


    private void configureBtn() {
        settleNewButton.addClickListener(e -> {
           getUI().ifPresent(ui -> ui.navigate(TripView.class));
        });
    }

    private void configureExportGrid() {
        exportGrid.setHeight("200px");
        exportGrid.setColumns("tripId");
        exportGrid.addColumn(Trip::daysToString).setHeader("days");
        exportGrid.setItems(tripsToExport);
    }

    private void configureGrid() {
        grid.setHeight("200px");
        grid.setColumns("tripId");
        grid.addColumn(Trip::daysToString).setHeader("days");
        grid.addColumn(Trip -> enterpriseService.enterprisesByAuditTripIdToString(Trip.getTripId())).setHeader("Audits");
        grid.addColumn(Trip -> userService.userByTripToString(Trip.getTripId())).setHeader("User");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setItems((List<Trip>) tripDao.findAll());

        ContextMenu menu = new ContextMenu(grid);
        menu.addItem("add To Export", e ->
        {
            tripsToExport.add(grid.asSingleSelect().getValue());
            exportGrid.getDataProvider().refreshAll();
        });
    }

    private void refreshGrid() {
        tripsToGrid.clear();
        tripsToGrid.addAll(tripService.findTripsByCriteria(filter.getValue(), datePicker.getValue(), endDatePicker.getValue(), userBox.getValue()));
        grid.setItems(tripsToGrid);
    }
}
