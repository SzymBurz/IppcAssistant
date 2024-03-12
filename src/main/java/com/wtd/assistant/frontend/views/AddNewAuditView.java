package com.wtd.assistant.frontend.views;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import com.wtd.assistant.frontend.dao.AuditDao;
import com.wtd.assistant.frontend.dao.EnterpriseDao;
import com.wtd.assistant.frontend.dao.UserDao;
import com.wtd.assistant.frontend.domain.Audit;
import com.wtd.assistant.frontend.domain.Enterprise;
import com.wtd.assistant.frontend.domain.User;
import com.vaadin.flow.component.button.Button;
import com.wtd.assistant.frontend.service.AuditService;
import com.vaadin.flow.component.textfield.TextField;
import com.wtd.assistant.frontend.service.EnterpriseService;
import javax.annotation.security.PermitAll;
import java.util.Collection;
@PermitAll
@Route(value = "NewAudit", layout = AssistantAppLayout.class)
public class AddNewAuditView extends VerticalLayout {

    //Repositories, Services
    private AuditDao auditDao;
    private AuditService auditService;
    private UserDao userDao;
    private EnterpriseDao enterpriseDao;
    private EnterpriseService enterpriseService;

    private H2 header1;
    private H2 header2;

    //Enterprises part
    private Grid<Enterprise> grid;

    private TextField filter;
    private DatePicker datePicker;
    private DatePicker endDatePicker;

    //Audit part
    private String enterpriseId;
    private TextField enterpriseTextField;
    private ComboBox<User> userComboBox;
    private DatePicker auditDatePicker;
    private Button addNewBtn;
    private Button getBackBtn;
    private Binder<Enterprise> enterpriseBinder;
    private Enterprise  enterprise;
    private Dialog dialog;

    public AddNewAuditView(UserDao userDao, AuditDao auditDao, AuditService auditService, EnterpriseDao enterpriseDao, EnterpriseService enterpriseService) {

        this.userDao = userDao;
        this.auditDao = auditDao;
        this.auditService = auditService;
        this.enterpriseDao = enterpriseDao;
        this.enterpriseService = enterpriseService;

        this.header1 = new H2("Select enterprise. LMB then RMB -> Select");
        this.header2 = new H2("Set details");

        this.grid = new Grid<>(Enterprise.class);
        this.filter = new TextField();
        this.datePicker = new DatePicker();
        this.endDatePicker = new DatePicker();

        this.enterpriseTextField =  new TextField("Enterprise");
        this.userComboBox = new ComboBox<>("Auditor");
        this.auditDatePicker = new DatePicker("Planed Date");
        this.addNewBtn = new Button("Add");
        this.getBackBtn = new Button("Cancel");
        this.enterprise = enterprise;
        this.enterpriseBinder = new Binder<>(Enterprise.class);
        this.dialog = new Dialog();

        //Enterprise part

        configureGrid();
        configureGridMenu();
        configureFilter();
        configureDatePicker();
        configureEndDatePicker();
        configureUserComboBox();
        configureAddNewBtn();
        configureEnterpriseBinder();
        configureDialog();

        HorizontalLayout layout1 = new HorizontalLayout(filter, datePicker, endDatePicker);
        HorizontalLayout layout2 = new HorizontalLayout(addNewBtn, getBackBtn);
        add(header1, layout1, grid, header2, enterpriseTextField, userComboBox, auditDatePicker, layout2, dialog);
    }

    private void configureDialog() {
        dialog.setHeaderTitle(String.format("Audit created"));
        Button closeButton = new Button("Close", (e) -> {
            dialog.close();
            userComboBox.setReadOnly(true);
            auditDatePicker.setReadOnly(true);
        });
        closeButton.getStyle().set("margin-right", "auto");
        dialog.getFooter().add(closeButton);
    }

    private void configureFilter() {
        filter.setPlaceholder("Filter by name...");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> updateList());
    }

    private void configureDatePicker() {
        datePicker.setPlaceholder("Filter by expiry date");
        datePicker.addValueChangeListener(e -> updateList());
    }

    private void configureEndDatePicker() {
        endDatePicker.setPlaceholder("period");
        endDatePicker.addValueChangeListener(e -> updateList());
    }

    private void updateList(){
        grid.setItems(enterpriseService.findEnterprisesByCriteria(filter.getValue(), datePicker.getValue(), endDatePicker.getValue()));
    }
    private void configureGrid() {
        grid.setHeight("200px");
        grid.setColumns("name", "ippcCode", "expiryDate");
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.setItems(enterpriseService.findEnterprisesByCriteria(null, null, null));
    }

    private void configureUserComboBox() {
        userComboBox.setItems((Collection<User>) userDao.findAll());
        userComboBox.setItemLabelGenerator(User::getName);
    }

    private void configureAddNewBtn() {
        addNewBtn.setDisableOnClick(true);
        addNewBtn.addClickListener(e -> {
            if (enterprise == null) {
                System.out.println("enterprise must be selected");
            } else {
                Audit newAudit = new Audit(enterprise,
                        userComboBox.getValue(), auditDatePicker.getValue());
                newAudit.setCompleted(false);
                auditDao.save(newAudit);
                dialog.open();
            }
        });
    }

    private void configureEnterpriseBinder() {

        enterpriseBinder.forField(enterpriseTextField).bind("name");
        enterpriseTextField.setReadOnly(true);
    }

    private void configureGridMenu() {
        GridContextMenu<Enterprise> menu = grid.addContextMenu();
        menu.addItem("Select", event -> {
            enterprise = grid.asSingleSelect().getValue();
            enterpriseBinder.setBean(enterprise);
            userComboBox.setReadOnly(false);
            userComboBox.clear();
            auditDatePicker.setReadOnly(false);
            auditDatePicker.clear();
            addNewBtn.setEnabled(true);
        });
    }
}

