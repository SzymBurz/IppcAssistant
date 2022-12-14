package com.wtd.assistant.frontend;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.wtd.assistant.frontend.dao.AuditDao;
import com.wtd.assistant.frontend.dao.EnterpriseDao;
import com.wtd.assistant.frontend.dao.UserDao;
import com.wtd.assistant.frontend.domain.Audit;
import com.wtd.assistant.frontend.domain.User;
import com.wtd.assistant.frontend.service.AuditService;
import com.vaadin.flow.component.checkbox.Checkbox;
import java.util.Collection;

@Route(value = "Audits", layout = AssistantAppLayout.class)
public class AuditView extends VerticalLayout {

    private AuditService auditService;
    private AuditDao auditDao;
    private UserDao userDao;
    private EnterpriseDao enterpriseDao;
    private Grid<Audit> grid;
    private TextField filter;
    private DatePicker datePicker;
    private Button addNewBtn;
    private ComboBox<User> userBox;
    private Checkbox notCompleted;
    private Checkbox completed;

    TextField enterpriseTextField;
    ComboBox<User> userBox2;
    DatePicker auditDatePicker;
    DatePicker secondTermDatePicker;
    Checkbox completed2;
    TextArea remarks;
    Button saveChange;

    Audit selectedAudit;

    Binder auditBinder;

    public AuditView(AuditService auditService, AuditDao auditDao, UserDao userDao, EnterpriseDao enterpriseDao) {
        this.auditService = auditService;
        this.auditDao = auditDao;
        this.userDao = userDao;
        this.enterpriseDao = enterpriseDao;
        this.grid = new Grid<>(Audit.class, false);
        this.filter = new TextField();
        this.datePicker = new DatePicker("Filter by date");
        this.secondTermDatePicker = new DatePicker("Second Term");
        this.addNewBtn = new Button("New Audit", VaadinIcon.PLUS.create());
        this.userBox = new ComboBox("Auditor");
        this.notCompleted = new Checkbox("Not completed");
        this.completed = new Checkbox("Completed");
        this.enterpriseTextField = new TextField("Enterprise");
        this.userBox2 = new ComboBox("Auditor");
        this.auditDatePicker = new DatePicker("Audit date");
        this.completed2 = new Checkbox("Completed");
        this.remarks = new TextArea("Remarks");
        this.saveChange = new Button("save");
        this.selectedAudit = null;
        this.auditBinder = new Binder<>(Audit.class);

        configureFilter();
        configureCheckBoxes();
        configureComboBox();
        configureGrid();
        configureGridMenu();
        configureAuditFields();
        configureSaveButton();
        configureAddNewBtn();
        configureDatePicker();

        HorizontalLayout layout = new HorizontalLayout(filter, datePicker, userBox, notCompleted, completed);
        layout.setAlignItems(FlexComponent.Alignment.BASELINE);
        HorizontalLayout layout2 = new HorizontalLayout(completed2, saveChange);
        VerticalLayout layout3 = new VerticalLayout(addNewBtn);
        layout3.setAlignItems(FlexComponent.Alignment.END);
        add(layout, grid, layout3, enterpriseTextField, userBox2, auditDatePicker, remarks,secondTermDatePicker, layout2);

    }

    private void configureDatePicker() {
        datePicker.addValueChangeListener(e -> updateList());
    }

    private void configureAddNewBtn() {
        addNewBtn.addClickListener(e -> {
            getUI().ifPresent(ui -> ui.navigate(AddNewAudit.class));
        });
    }

    private void configureSaveButton() {
        saveChange.addClickListener(e -> {
            auditService.updateAudit(selectedAudit.getAuditId(), userBox2.getValue(), auditDatePicker.getValue(), remarks.getValue(), secondTermDatePicker.getValue());
        });
    }

    private void configureAuditFields() {
        auditBinder.forField(auditDatePicker).bind("date");
        auditBinder.forField(secondTermDatePicker).bind("secondTerm");
        auditBinder.forField(remarks).bind("remarks");
        auditBinder.forField(completed2).bind("completed");
        auditBinder.forField(userBox2).bind("user");
        auditBinder.forField(completed2).bind("completed");
    }

    private void configureGridMenu() {
        GridContextMenu<Audit> menu = grid.addContextMenu();
        menu.addItem("Select", event -> {
            selectedAudit = grid.asSingleSelect().getValue();
            auditBinder.setBean(selectedAudit);
            completed2.setReadOnly(true);
            enterpriseTextField.setValue(
                    enterpriseDao.findByAudits_AuditId(selectedAudit.getAuditId()).get().getName());
        });
    }

    private void configureGrid() {
        grid.setItems(auditDao.findAll());
        grid.setHeight("200px");
        grid.setColumns("auditId", "enterprise", "user", "date");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
    }

    private void configureComboBox() {
        userBox.setItems((Collection<User>) userDao.findAll());
        userBox.setItemLabelGenerator(User::getName);
        userBox2.setItems((Collection<User>) userDao.findAll());
        userBox2.setItemLabelGenerator(User::getName);
        userBox.addValueChangeListener(e -> updateList());
    }

    private void configureFilter() {
        filter.setLabel("Filter by Name/IPPC code");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> updateList());
    }

    private void configureCheckBoxes() {
        notCompleted.setLabel("not completed");
        completed.setLabel("completed");
        notCompleted.addValueChangeListener(e -> updateList());
        completed.addValueChangeListener(e -> updateList());
    }

    private void updateList() {

        grid.setItems(auditService.findAuditsByCriteria(filter.getValue(), datePicker.getValue(), userBox.getValue(), notCompleted.getValue(), completed.getValue()));

    }


}
