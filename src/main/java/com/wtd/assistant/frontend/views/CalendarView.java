package com.wtd.assistant.frontend.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.wtd.assistant.frontend.dao.AuditDao;
import com.wtd.assistant.frontend.dao.EnterpriseDao;
import com.wtd.assistant.frontend.dao.UserDao;
import com.wtd.assistant.frontend.domain.Audit;

import javax.annotation.security.PermitAll;
import java.time.LocalDate;
import java.time.ZoneId;

@PermitAll
@Route(value = "Calendar", layout = AssistantAppLayout.class)
public class CalendarView extends VerticalLayout {
    final private AuditDao auditDao;
    final private UserDao userDao;
    final private EnterpriseDao enterpriseDao;

    private H2 headerAudits;
    private H2 headerSecTerm;
    private Grid<Audit> grid;
    private Grid<Audit> gridSecTerm;



    public CalendarView(AuditDao auditDao, UserDao userDao, EnterpriseDao enterpriseDao) {
        this.auditDao = auditDao;
        this.userDao = userDao;
        this.enterpriseDao = enterpriseDao;
        this.headerAudits = new H2("Upcoming Audits");
        this.headerSecTerm = new H2("Second Terms");
        this.grid = new Grid<>(Audit.class, false);
        this.gridSecTerm = new Grid<>(Audit.class, false);


        grid.setItems(auditDao.findByDateGreaterThanEqualAndCompletedFalse(LocalDate.now(ZoneId.of("Europe/Paris"))));
        grid.setHeight("200px");
        grid.setColumns("date");
        grid.addColumn(Audit -> enterpriseDao.findByAudits_AuditId(Audit.getAuditId()).get()).setHeader("Enterprise");
        grid.addColumn(Audit -> userDao.findByAudits_AuditId(Audit.getAuditId())).setHeader("User");

        gridSecTerm.setItems(auditDao.findBySecondTermNotNullAndSecondTermGreaterThanEqual(LocalDate.now(ZoneId.of("Europe/Paris"))));
        gridSecTerm.setHeight("200px");
        gridSecTerm.addColumn("secondTerm").setHeader("Second Term");
        gridSecTerm.addColumn(Audit -> enterpriseDao.findByAudits_AuditId(Audit.getAuditId()).get()).setHeader("Enterprise");
        gridSecTerm.addColumn(Audit -> userDao.findByAudits_AuditId(Audit.getAuditId())).setHeader("User");
        gridSecTerm.addColumn("remarks");

        add(headerAudits, grid, headerSecTerm, gridSecTerm);

    }
}
