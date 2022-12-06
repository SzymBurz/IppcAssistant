package com.wtd.assistant.frontend;

import com.vaadin.flow.component.Component;
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
import com.wtd.assistant.frontend.dao.AuditDao;
import com.wtd.assistant.frontend.dao.TripDao;
import com.wtd.assistant.frontend.domain.Audit;
import com.wtd.assistant.frontend.domain.Trip;
import com.wtd.assistant.frontend.domain.User;
import com.wtd.assistant.frontend.service.AuditService;
import com.wtd.assistant.frontend.service.EnterpriseService;
import com.wtd.assistant.frontend.service.TripService;
import com.wtd.assistant.frontend.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Route(value = "Trips", layout = AssistantAppLayout.class)
public class TripsView extends VerticalLayout {

    TripDao tripDao;
    TripService tripService;
    AuditService auditService;
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

    public TripsView(TripDao tripDao, TripService tripService, EnterpriseService enterpriseService, AuditService auditService, UserService userService) {
        this.tripDao = tripDao;
        this.tripService = tripService;
        this.enterpriseService = enterpriseService;
        this.auditService = auditService;
        this.userService = userService;
        this.header = new H2("Trips");
        this.filter = new TextField();
        this.datePicker = new DatePicker();
        this.endDatePicker = new DatePicker();
        this.userBox = new ComboBox<>();
        this.grid = new Grid<>(Trip.class);
        this.exportHeader = new H2("Export");
        this.exportGrid = new Grid<>(Trip.class);
        this.output = new TextArea("export");
        this.tripsToExport = new ArrayList<>();

        configureGrid();
        configureExportGrid();
        HorizontalLayout filterLayout = new HorizontalLayout(filter, datePicker, endDatePicker, userBox);
        /*
        VerticalLayout generalLayout = new VerticalLayout();
        generalLayout.add(grid, exportGrid);
        setContent(generalLayout);
         */
        add(grid, exportGrid);

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
}
