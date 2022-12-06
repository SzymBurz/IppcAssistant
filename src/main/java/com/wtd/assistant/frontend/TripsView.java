package com.wtd.assistant.frontend;

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
import com.vaadin.flow.router.Route;
import com.wtd.assistant.frontend.dao.TripDao;
import com.wtd.assistant.frontend.dao.UserDao;
import com.wtd.assistant.frontend.domain.Trip;
import com.wtd.assistant.frontend.domain.User;
import com.wtd.assistant.frontend.service.AuditService;
import com.wtd.assistant.frontend.service.EnterpriseService;
import com.wtd.assistant.frontend.service.TripService;
import com.wtd.assistant.frontend.service.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Route(value = "Trips", layout = AssistantAppLayout.class)
public class TripsView extends VerticalLayout {

    TripDao tripDao;
    TripService tripService;
    AuditService auditService;
    UserDao userDao;
    EnterpriseService enterpriseService;
    UserService userService;
    H2 header;
    TextField filter;
    DatePicker datePicker;
    DatePicker endDatePicker;
    ComboBox<User> userBox;
    Grid<Trip> grid;
    H2 exportHeader;
    Grid<Trip> exportGrid;
    TextArea output;
    List<Trip> tripsToExport;
    Button settleNewButton;

    public TripsView(TripDao tripDao, TripService tripService, EnterpriseService enterpriseService, AuditService auditService, UserService userService, UserDao userDao) {
        this.tripDao = tripDao;
        this.tripService = tripService;
        this.enterpriseService = enterpriseService;
        this.auditService = auditService;
        this.userService = userService;
        this.userDao = userDao;
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

        configureGrid();
        configureExportGrid();
        configureBtn();
        configureFilter();
        configureDatePickers();
        configureUserBox();

        HorizontalLayout filterLayout = new HorizontalLayout(filter, datePicker, endDatePicker, userBox);
        VerticalLayout btnLayout = new VerticalLayout(settleNewButton);
        btnLayout.setAlignItems(Alignment.END);
        add(filterLayout, grid, btnLayout, exportGrid);

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
    }


    private void configureBtn() {
        settleNewButton.addClickListener(e -> {
           getUI().ifPresent(ui -> ui.navigate(TripView.class));
        });
    }

    private void configureExportGrid() {
        exportGrid.setHeight("200px");
        exportGrid.setColumns("tripId");
        exportGrid.addColumn(Trip -> Trip.daysToString()).setHeader("days");
        exportGrid.setItems(tripsToExport);
    }

    private void configureGrid() {
        grid.setHeight("200px");
        grid.setColumns("tripId");
        grid.addColumn(Trip -> Trip.daysToString()).setHeader("days");
        grid.addColumn(Trip -> enterpriseService.enterprisesByAuditTripIdToString(Trip.getTripId())).setHeader("Audits");
        grid.addColumn(Trip -> userService.userByTrip(Trip.getTripId())).setHeader("User");
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
        grid.setItems(tripService.findAuditsByCriteria(filter.getValue(), datePicker.getValue(), endDatePicker.getValue(), userBox.getValue()));
        grid.getDataProvider().refreshAll();
    }
}
