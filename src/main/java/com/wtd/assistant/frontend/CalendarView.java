package com.wtd.assistant.frontend;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.wtd.assistant.frontend.dao.AuditDao;
import com.wtd.assistant.frontend.dao.EnterpriseDao;
import com.wtd.assistant.frontend.dao.UserDao;
import com.wtd.assistant.frontend.domain.Audit;

import java.time.LocalDate;
import java.time.ZoneId;


@Route("Calendar")
public class CalendarView extends AssistantAppLayout {
    AuditDao auditDao;
    UserDao userDao;
    EnterpriseDao enterpriseDao;

    H2 headerAudits;
    H2 headerSecTerm;
    Grid<Audit> grid;
    Grid<Audit> gridSecTerm;



    public CalendarView(AuditDao auditDao, UserDao userDao, EnterpriseDao enterpriseDao) {
        this.auditDao = auditDao;
        this.userDao = userDao;
        this.enterpriseDao = enterpriseDao;
        this.headerAudits = new H2("Upcoming Audits");
        this.headerSecTerm = new H2("Second Terms");
        this.grid = new Grid<>(Audit.class, false);
        this.gridSecTerm = new Grid<>(Audit.class, false);


        grid.setItems(auditDao.findByDateGreaterThanEqual(LocalDate.now(ZoneId.of("Europe/Paris"))));
        grid.setHeight("200px");
        grid.setColumns("date");
        grid.addColumn(Audit -> enterpriseDao.findByAudits_AuditId(Audit.getAuditId()).get()).setHeader("Enterprise");
        grid.addColumn(Audit -> userDao.findByAudits_AuditId(Audit.getAuditId())).setHeader("User");

        gridSecTerm.setItems(auditDao.findBySecondTermNotNullAndSecondTermGreaterThanEqual(LocalDate.now(ZoneId.of("Europe/Paris"))));
        gridSecTerm.setHeight("200px");
        gridSecTerm.addColumn("secondTerm").setHeader("date");
        gridSecTerm.addColumn(Audit -> enterpriseDao.findByAudits_AuditId(Audit.getAuditId()).get()).setHeader("Enterprise");
        gridSecTerm.addColumn(Audit -> userDao.findByAudits_AuditId(Audit.getAuditId())).setHeader("User");
        gridSecTerm.addColumn("remarks");

        VerticalLayout generalLayout = new VerticalLayout();
        generalLayout.add(headerAudits, grid, headerSecTerm, gridSecTerm);

        setContent(generalLayout);
    }

    @Override
    public void setContent(Component content) {
        super.setContent(content);
    }

}
