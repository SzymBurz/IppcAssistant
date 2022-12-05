package com.wtd.assistant.frontend;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.*;
import com.wtd.assistant.frontend.dao.AuditDao;
import com.wtd.assistant.frontend.dao.EnterpriseDao;
import com.wtd.assistant.frontend.domain.Audit;
import com.wtd.assistant.frontend.domain.Enterprise;
import com.wtd.assistant.frontend.service.EnterpriseService;

import java.util.List;
import java.util.Optional;

@Route("EnterpriseView")
public class EnterpriseView extends AssistantAppLayout implements HasUrlParameter<String>, AfterNavigationObserver {

    final private EnterpriseDao enterpriseDao;

    private final EnterpriseService enterpriseService;
    final private AuditDao auditDao;
    final private Grid<Audit> grid;
    private List<Audit> auditsList;
    private Optional<Enterprise> enterprise;
    private String enterpriseId;
    private Accordion accordion;
    private static final String ENTERPRISE_DETAILS = "Enterprise details";

    public EnterpriseView(EnterpriseDao enterpriseDao, EnterpriseService enterpriseService, AuditDao auditDao) {
        this.enterpriseDao = enterpriseDao;
        this.enterpriseService = enterpriseService;
        this.auditDao = auditDao;
        this.grid = new Grid<>(Audit.class, false);
        this.accordion = new Accordion();


        VerticalLayout generalLayout = new VerticalLayout();
        generalLayout.add(accordion);
        grid.setHeight("200px");
        setContent(generalLayout);

    }

    private FormLayout createFormLayout() {
        FormLayout enterpriseDetailsFormLayout = new FormLayout();
        enterpriseDetailsFormLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("20", 2));
        return enterpriseDetailsFormLayout;
    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        enterpriseId = parameter;
        System.out.println("setParameter(), enterpriseId: " + enterpriseId);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {

        //Grid
        enterprise = Optional.of(enterpriseDao.findById(Integer.valueOf(enterpriseId)).get());
        auditsList = auditDao.findByEnterprise(enterprise);
        System.out.println(enterprise.get().getEnterpriseId());
        System.out.println("EnterpriseId: " + enterprise.get().getEnterpriseId());

        grid.setItems(auditsList);
        grid.addColumn(item -> item.getEnterprise().getName()).setHeader("enterprise");
        grid.addColumn(item -> item.getAuditId()).setHeader("auditId");

        VerticalLayout auditsInformationLayout = new VerticalLayout();
        auditsInformationLayout.add(grid);

        //Accordion
        Binder<Enterprise> enterpriseBinder = new Binder<>(Enterprise.class);
        enterpriseBinder.setBean(enterpriseDao.findById(Integer.valueOf(enterpriseId)).get());

        TextField name = new TextField("Name");
        enterpriseBinder.forField(name).bind("name");

        TextField ippcCode = new TextField("IPPC Code");
        enterpriseBinder.forField(ippcCode).bind("ippcCode");

        DatePicker expiryDate = new DatePicker("Expiry Date");
        enterpriseBinder.forField(expiryDate).bind("expiryDate");
        expiryDate.setInitialPosition(enterprise.get().getExpiryDate());

        VerticalLayout enterpriseInformationLayout = new VerticalLayout();
        enterpriseInformationLayout.add(name, ippcCode, expiryDate);

        enterpriseInformationLayout.setSpacing(false);
        enterpriseInformationLayout.setPadding(false);

        name.setReadOnly(true);
        ippcCode.setReadOnly(true);
        expiryDate.setReadOnly(true);

        Button editButton = new Button("Edit",
                (e) -> {
                    name.setReadOnly(false);
                    ippcCode.setReadOnly(false);
                    expiryDate.setReadOnly(false);
                });

        Button saveButton = new Button("Save",
                (e) -> {
                    name.setReadOnly(true);
                    ippcCode.setReadOnly(true);
                    expiryDate.setReadOnly(true);
                });

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.add(editButton, saveButton);
        enterpriseInformationLayout.add(buttons);

        //Accordion
        accordion.add("Enterprise information", enterpriseInformationLayout);
        accordion.add("Audits information", auditsInformationLayout);
        accordion.setWidthFull();

    }

    @Override
    public void setContent(Component content) {
        super.setContent(content);
    }

}


