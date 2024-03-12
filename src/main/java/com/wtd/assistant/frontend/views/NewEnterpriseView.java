package com.wtd.assistant.frontend.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import com.wtd.assistant.frontend.dao.EnterpriseDao;
import com.wtd.assistant.frontend.domain.Enterprise;
import javax.annotation.security.PermitAll;

@PermitAll
@Route(value = "NewEnterprise", layout = AssistantAppLayout.class)
public class NewEnterpriseView extends VerticalLayout{

    public NewEnterpriseView(EnterpriseDao enterpriseDao) {

        Binder<Enterprise> enterpriseBinder = new Binder<>(Enterprise.class);
        enterpriseBinder.setBean(new Enterprise());

        TextField name = new TextField("Name");
        enterpriseBinder.forField(name).bind("name");

        TextField ippcCode = new TextField("IPPC Code");
        enterpriseBinder.forField(ippcCode).bind("ippcCode");

        DatePicker expiryDate = new DatePicker("Expiry Date");
        enterpriseBinder.forField(expiryDate).bind("expiryDate");

        VerticalLayout enterpriseInformationLayout = new VerticalLayout();
        enterpriseInformationLayout.add(name, ippcCode, expiryDate);

        enterpriseInformationLayout.setSpacing(false);
        enterpriseInformationLayout.setPadding(false);

        name.setReadOnly(false);
        ippcCode.setReadOnly(false);
        expiryDate.setReadOnly(false);

        Button saveButton = new Button("Save",
                (e) -> {

                    Enterprise newEnterprise = new Enterprise(ippcCode.getValue(), name.getValue(), expiryDate.getValue());

                    if (name.getValue().isEmpty() || ippcCode.getValue().isEmpty()) {
                        System.out.println("Enter missing value");
                    } else if (enterpriseDao.findByIppcCode(ippcCode.getValue()).isPresent()) {
                        System.out.println("Object with given code already exists");
                    } else {
                        enterpriseDao.save(newEnterprise);
                    }

                    boolean isMapped = enterpriseDao.findById(newEnterprise.getEnterpriseId()).isPresent();

                    if (isMapped) {
                        name.setReadOnly(true);
                        ippcCode.setReadOnly(true);
                        expiryDate.setReadOnly(true);
                    }else {
                        System.out.println("Object not created");
                    }

                });

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        enterpriseInformationLayout.add(saveButton);
        add(enterpriseInformationLayout);

    }
}
