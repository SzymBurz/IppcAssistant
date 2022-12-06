package com.wtd.assistant.frontend;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.wtd.assistant.frontend.dao.*;
import com.wtd.assistant.frontend.domain.*;
import com.wtd.assistant.frontend.service.TripService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Route(value = "Trip", layout = AssistantAppLayout.class)
public class TripView extends VerticalLayout {
    final private TripService tripService;
    final private TripDao tripDao;
    final private CarDao carDao;
    final private UserDao userDao;
    final private AuditDao auditDao;
    final private ExpenseDao expenseDao;
    private Grid<Audit> grid;
    private Grid<Audit> selectedAuditsGrid;
    private TextField filter;
    private DatePicker datePicker;
    private DatePicker endDatePicker;
    private List<Enterprise> enterprises;
    private H2 header;
    private H2 gridHeader;
    private H2 auditsGridHeader;
    private List<Audit> selectedAudits;
    private List<Expense> expenses;
    private Grid<Expense> expenseGrid;
    private NumberField amount;
    private TextField description;
    private Button addExpenseButton;
    private H2 expensesHeader;
    private ComboBox<Car> carComboBox;
    private NumberField carCounterBefore;
    private NumberField carCounterAfter;
    private NumberField workTime;
    private DatePicker firstDay;
    private DatePicker secondDay;
    private Button addNew;
    private Button cancelBtn;
    private Dialog dialog1;
    private Dialog dialog2;

    public TripView(TripService tripService, TripDao tripDao, CarDao carDao, UserDao userDao, AuditDao auditDao, ExpenseDao expenseDao) {
        this.tripService = tripService;
        this.tripDao = tripDao;
        this.carDao = carDao;
        this.userDao = userDao;
        this.auditDao = auditDao;
        this.expenseDao = expenseDao;
        this.grid = new Grid<>(Audit.class, false);
        this.selectedAuditsGrid = new Grid<>(Audit.class, false);
        this.filter = new TextField();
        this.datePicker = new DatePicker();
        this.endDatePicker = new DatePicker();
        this.enterprises = new ArrayList<>();
        this.header = new H2("Settle new Trip");
        this.gridHeader = new H2("Select, and add audits to Trip");
        this.auditsGridHeader = new H2("Added");
        this.selectedAudits = new ArrayList<Audit>();
        this.expenses = new ArrayList<Expense>();
        this.expenseGrid = new Grid<>(Expense.class, false);
        this.amount = new NumberField("amount");
        this.description = new TextField("description");
        this.addExpenseButton = new Button("add expense");
        this.expensesHeader = new H2("Add Expenses");
        this.carComboBox = new ComboBox<>("Car");
        this.carCounterBefore = new NumberField("Car counter Before");
        this.carCounterAfter = new NumberField("Car Counter After");
        this.workTime = new NumberField("Work Time");
        this.firstDay = new DatePicker("First Day");
        this.secondDay = new DatePicker("Second Day (Optional)");
        this.addNew = new Button("Settle trip");
        this.cancelBtn = new Button("Cancel");
        this.dialog1 = new Dialog();
        this.dialog2 = new Dialog();

        HorizontalLayout searchbarLayout = new HorizontalLayout(filter, datePicker, endDatePicker);
        HorizontalLayout expenseLayout = new HorizontalLayout(amount, description, addExpenseButton);
        expenseLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        HorizontalLayout btnLayout = new HorizontalLayout(addNew, cancelBtn);
        HorizontalLayout carLayout = new HorizontalLayout(carComboBox, carCounterBefore, carCounterAfter);
        VerticalLayout tripInformationLayout = new VerticalLayout(firstDay, secondDay, workTime);

        add(header, gridHeader, searchbarLayout, grid, auditsGridHeader, selectedAuditsGrid, expensesHeader, expenseGrid, expenseLayout, carLayout, tripInformationLayout, btnLayout);

        configureGrid();
        configureSelectedAuditsGrid();
        configureExpenseGrid();
        configureButtons();

        dialog1.add("Please fill all fields before saving");
        dialog2.add("Trip was settled successfully");
        filter.setPlaceholder("Filter by Name");
        datePicker.setPlaceholder("filter by time period");
        Binder<Audit> auditBinder = new Binder<>(Audit.class);
        carComboBox.setItems((Collection<Car>) carDao.findAll());
        carComboBox.setItemLabelGenerator(Car::getName);

    }

    private void configureButtons() {
        addExpenseButton.addClickListener(e -> {
            if(amount != null && description != null) {
                expenses.add(new Expense(amount.getValue(), description.getValue()));
                expenseGrid.setItems(expenses);
            }
        });

        addNew.addClickListener(e -> saveNewTrip());
    }

    private void saveNewTrip() {
        if (
                carComboBox.getValue() == null ||
                        carCounterBefore.getValue() == null ||
                        carCounterAfter.getValue() == null ||
                        selectedAudits.isEmpty() ||
                        workTime.getValue()== null ||
                        firstDay.getValue()== null
        ) {
            dialog1.open();

        } else {

            Trip newTrip = new Trip(
                    carComboBox.getValue(),
                    carCounterBefore.getValue(),
                    carCounterAfter.getValue(),
                    selectedAudits,
                    expenses,
                    workTime.getValue(),
                    firstDay.getValue(),
                    secondDay.getValue()
            );

            tripDao.save(newTrip);
            Optional<Trip> savedNewTrip = tripDao.findById(newTrip.getTripId());

            if (savedNewTrip.isPresent()) {
                for (Expense exp : expenses) {
                    expenseDao.updateTrip(exp.getExpense_id(), savedNewTrip.get());
                }
                for (Audit aud : selectedAudits) {
                    auditDao.updateTrip(aud.getAuditId(), savedNewTrip.get());
                    auditDao.setCompleted(aud.getAuditId());
                }
            }
            dialog2.open();
            carComboBox.setReadOnly(true);
            carCounterBefore.setReadOnly(true);
            carCounterAfter.setReadOnly(true);
            workTime.setReadOnly(true);
            firstDay.setReadOnly(true);
            secondDay.setReadOnly(true);
        }
    }

    private void configureExpenseGrid() {
        expenseGrid.setHeight("200px");
        expenseGrid.setColumns("amount", "description");
        expenseGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        expenseGrid.setItems(expenses);

        GridContextMenu<Expense> expenseMenu = expenseGrid.addContextMenu();
        expenseMenu.addItem("Delete", event -> {
            expenses.remove(expenseGrid.asSingleSelect().getValue());
            expenseGrid.getDataProvider().refreshAll();
        });
    }

    private void configureSelectedAuditsGrid() {
        selectedAuditsGrid.setHeight("200px");
        selectedAuditsGrid.setColumns("auditId", "enterprise", "user", "date");
        selectedAuditsGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        selectedAuditsGrid.setItems(selectedAudits);
    }

    private void configureGrid() {
        grid.setHeight("200px");
        grid.setColumns("auditId", "enterprise", "user", "date");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setItems(auditDao.findByCompleted(false));

        GridContextMenu<Audit> menu = grid.addContextMenu();
        menu.addItem("Add to Trip", event -> {
            selectedAudits.add(grid.asSingleSelect().getValue());
            selectedAuditsGrid.getDataProvider().refreshAll();
        });
    }

}
