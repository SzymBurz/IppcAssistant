package com.wtd.assistant.frontend.generator;

import com.wtd.assistant.frontend.dao.*;
import com.wtd.assistant.frontend.domain.*;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class SampleDataSuiteGenerator {
    @Autowired
    EnterpriseDao enterpriseDao;
    @Autowired
    AuditDao auditDao;
    @Autowired
    UserDao userDao;
    @Autowired
    CarDao carDao;
    @Autowired
    TripDao tripDao;
    @Autowired
    ExpenseDao expenseDao;

    public void generateDataSuite() {
        enterpriseDao.deleteAll();
        auditDao.deleteAll();
        userDao.deleteAll();
        carDao.deleteAll();
        tripDao.deleteAll();
        expenseDao.deleteAll();

        Enterprise e1 = new Enterprise("PL-22 008", "TOR-PAL", LocalDate.parse("2022-07-16"));
        Enterprise e2 = new Enterprise("PL-12 006", "C.M.C.", LocalDate.parse("2023-10-26"));
        Enterprise e3 = new Enterprise("PL-26 005", "PALKO", LocalDate.parse("2024-03-13"));
        Enterprise e4 = new Enterprise("PL-12 004", "PALLETENWERK KOZIK", LocalDate.parse("2024-01-27"));
        Enterprise e5 = new Enterprise("PL-08 001", "RAVEN", LocalDate.parse("2022-12-12"));
        Enterprise e6 = new Enterprise("PL-28 011", "JAR", LocalDate.parse("2022-07-16"));
        Enterprise e7 = new Enterprise("PL-18 145", "TRAK", LocalDate.parse("2024-02-10"));
        Enterprise e8 = new Enterprise("PL-02 012", "KADOR", LocalDate.parse("2022-10-25"));
        Enterprise e9 = new Enterprise("PL-30-013", "PALIMEX", LocalDate.parse("2022-09-29"));
        Enterprise e10 = new Enterprise("PL-30 014", "ADAMUS", LocalDate.parse("2023-11-03"));
        Enterprise e11 = new Enterprise("PL-08 015", "PRZEMYSŁ DRZEWNY ŚWIEBODZIN", LocalDate.parse("2022-10-11"));
        Enterprise e12 = new Enterprise("PL-30 017", "MEBLECH", LocalDate.parse("2023-11-03"));
        Enterprise e13 = new Enterprise("PL-30 018", "SCANDPOL", LocalDate.parse("2022-10-22"));
        Enterprise e14 = new Enterprise("PL-02 019", "JAMAX", LocalDate.parse("2023-10-25"));
        Enterprise e15 = new Enterprise("PL-02 019", "EUREX", LocalDate.parse("2023-03-21"));
        Enterprise e16 = new Enterprise("PL-32 022", "ZAKŁAD DRZEWNY IRCHA", LocalDate.parse("2022-11-05"));
        Enterprise e17 = new Enterprise("PL-22 024", "TRANSDREWNEX", LocalDate.parse("2023-03-21"));
        Enterprise e18 = new Enterprise("PL-08 025", "ERMA", LocalDate.parse("2023-04-08"));
        Enterprise e19 = new Enterprise("PL-28 026", "DUET", LocalDate.parse("2023-01-28"));
        Enterprise e20 = new Enterprise("PL-32 027", "INTER-PACK", LocalDate.parse("2024-02-03"));
        Enterprise e21 = new Enterprise("PL-22 028", "COMPANY LTD", LocalDate.parse("2023-04-11"));

        enterpriseDao.save(e1);
        enterpriseDao.save(e2);
        enterpriseDao.save(e3);
        enterpriseDao.save(e4);
        enterpriseDao.save(e5);
        enterpriseDao.save(e6);
        enterpriseDao.save(e7);
        enterpriseDao.save(e8);
        enterpriseDao.save(e9);
        enterpriseDao.save(e10);
        enterpriseDao.save(e11);
        enterpriseDao.save(e12);
        enterpriseDao.save(e13);
        enterpriseDao.save(e14);
        enterpriseDao.save(e15);
        enterpriseDao.save(e16);
        enterpriseDao.save(e17);
        enterpriseDao.save(e18);
        enterpriseDao.save(e19);
        enterpriseDao.save(e20);
        enterpriseDao.save(e21);

        Car c1 = new Car("Dacia Duster", "PO2433", 0);
        Car c2 = new Car("Kia Ceed", "PO2534", 0);
        Car c3 = new Car("Suzuki Vitara", "PO3639", 0);
        Car c4 = new Car("Polonez Caro", "PZ1223", 0);

        carDao.save(c1);
        carDao.save(c2);
        carDao.save(c3);
        carDao.save(c4);

        User u1 = new User("Dominik Młodzionek");
        User u2 = new User("Łukasz Zły");
        User u3 = new User("Wojciech Damianowski");
        User u4 = new User("Anna Fett");
        User u5 = new User("Szymon Murzyński");

        userDao.save(u1);
        userDao.save(u2);
        userDao.save(u3);
        userDao.save(u4);
        userDao.save(u5);

        Audit a1 = new Audit(e1, u1, LocalDate.parse("2022-07-16"));
        Audit a2 = new Audit(e6, u1, LocalDate.parse("2022-07-16"));
        Audit a3 = new Audit(e8, u2, LocalDate.parse("2022-10-01"));
        Audit a4 = new Audit(e9, u2, LocalDate.parse("2022-10-01"));
        Audit a5 = new Audit(e15,u3, LocalDate.parse("2023-04-10"));
        Audit a6 = new Audit(e21,u3, LocalDate.parse("2023-04-10"));
        Audit a7 = new Audit(e19, u4, LocalDate.parse("2023-01-28"));
        Audit a8 = new Audit(e15, u1, LocalDate.parse("2023-03-21"));

        auditDao.save(a1);
        auditDao.save(a2);
        auditDao.save(a3);
        auditDao.save(a4);
        auditDao.save(a5);
        auditDao.save(a6);
        auditDao.save(a7);
        auditDao.save(a8);

        List<Audit> audits1 = new ArrayList<>();
        audits1.add(a1);
        audits1.add(a2);

        List<Audit> audits2 = new ArrayList<>();
        audits2.add(a3);
        audits2.add(a4);

        Expense ex1 = new Expense(170.0,"Hotel");
        Expense ex2 = new Expense(100.0,"Highway fees");
        Expense ex3 = new Expense(230.0, "Fuel");
        Expense ex4 = new Expense(185.0,"Hotel");
        Expense ex5 = new Expense(800.0,"Highway fees");
        Expense ex6 = new Expense(330.0, "Fuel");

        List<Expense> explist1 = new ArrayList<>();
        explist1.add(ex1);
        explist1.add(ex2);
        explist1.add(ex3);

        List<Expense> explist2 = new ArrayList<>();
        explist2.add(ex4);
        explist2.add(ex5);
        explist2.add(ex6);

        Trip t1 = new Trip(c1, 43000.0, 43500.0, audits1, explist1, 15.0, LocalDate.parse("2022-07-16"), null);
        Trip t2 = new Trip(c2, 25000.0, 25700.0, audits2, explist2, 18.0, LocalDate.parse("2022-10-01"), LocalDate.parse("2022-10-02"));

        tripDao.save(t1);
        tripDao.save(t2);

        auditDao.updateTrip(a1.auditId, t1);
        auditDao.updateTrip(a2.auditId, t1);
        auditDao.updateTrip(a3.auditId, t2);
        auditDao.updateTrip(a4.auditId, t2);

        auditDao.updateCompletedByAuditId( true, a1.getAuditId());
        auditDao.updateCompletedByAuditId( true, a2.getAuditId());
        auditDao.updateCompletedByAuditId( true, a3.getAuditId());
        auditDao.updateCompletedByAuditId( true, a4.getAuditId());
        auditDao.updateCompletedByAuditId( false, a5.getAuditId());
        auditDao.updateCompletedByAuditId( false, a6.getAuditId());
        auditDao.updateCompletedByAuditId( false, a7.getAuditId());
        auditDao.updateCompletedByAuditId( false, a8.getAuditId());

        expenseDao.updateTrip(ex1.getExpense_id(), t1);
        expenseDao.updateTrip(ex2.getExpense_id(), t1);
        expenseDao.updateTrip(ex3.getExpense_id(), t1);
        expenseDao.updateTrip(ex4.getExpense_id(), t2);
        expenseDao.updateTrip(ex5.getExpense_id(), t2);
        expenseDao.updateTrip(ex6.getExpense_id(), t2);

        auditDao.updateSecondTermByAuditId(LocalDate.parse("2023-01-01"), a3.getAuditId());
        auditDao.updateRemarksBy("Thermal sensors lacking required calibration certificate", a3.getAuditId());
    }

    public void starterKit() {
        Car c1 = new Car("Dacia Duster", "PO2433", 0);
        Car c2 = new Car("Kia Ceed", "PO2534", 0);
        Car c3 = new Car("Suzuki Vitara", "PO3639", 0);
        Car c4 = new Car("Polonez Caro", "PZ1223", 0);

        carDao.save(c1);
        carDao.save(c2);
        carDao.save(c3);
        carDao.save(c4);

        User u1 = new User("Dominik Młodzionek");
        User u2 = new User("Łukasz Zły");
        User u3 = new User("Wojciech Damianowski");
        User u4 = new User("Anna Fett");
        User u5 = new User("Szymon Murzyński");

        userDao.save(u1);
        userDao.save(u2);
        userDao.save(u3);
        userDao.save(u4);
        userDao.save(u5);
    }

    void cleanEnterprisesTable() {
        enterpriseDao.deleteAll();
    }
}
