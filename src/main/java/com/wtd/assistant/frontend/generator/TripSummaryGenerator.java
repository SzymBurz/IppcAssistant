package com.wtd.assistant.frontend.generator;

import com.vaadin.flow.component.textfield.TextArea;
import com.wtd.assistant.frontend.dao.*;
import com.wtd.assistant.frontend.domain.Enterprise;
import com.wtd.assistant.frontend.domain.Expense;
import com.wtd.assistant.frontend.domain.Trip;
import com.wtd.assistant.frontend.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripSummaryGenerator {

    @Autowired
    AuditDao auditDao;
    @Autowired
    UserDao userDao;
    @Autowired
    EnterpriseService enterpriseService;
    @Autowired
    ExpenseDao expenseDao;
    @Autowired
    CarDao carDao;

    public void generateSummary(List<Trip> trips, TextArea outputArea) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Summary: ");
        stringBuilder.append("\n");

        for(Trip t : trips) {
            stringBuilder.append("TripId: " + t.getTripId());
            stringBuilder.append("\n");
            stringBuilder.append("Date: " + t.getFirstDay() + " " + t.getSecondDay());
            stringBuilder.append("Auditor: " + userDao.findByAudits_TripId(t).get(0).getName());
            stringBuilder.append("\n");
            stringBuilder.append("Enterprises: " + enterpriseService.enterprisesByAuditTripIdToStringNextLine(t.getTripId()));
            stringBuilder.append("\n");
            if(expenseDao.findByTripId_TripId(t.getTripId()) != null) {
                stringBuilder.append("Expenses: ");
                double sum = 0.0;
                for (Expense exp : expenseDao.findByTripId_TripId(t.getTripId())) {
                    stringBuilder.append("\n" + exp);
                    sum = sum + exp.getAmount();
                }
                stringBuilder.append("\n Sum: " + sum +"zł");
            }
            stringBuilder.append("\n");
            stringBuilder.append("Car: " + carDao.findByTrips_Audits_TripId(t).get().getName());
            stringBuilder.append("\n");
            stringBuilder.append("Distance: " + (t.getCarCounterAfter() - t.getCarCounterBefore()) + "km");
            stringBuilder.append("\n");
            stringBuilder.append("- - - - - -");
            stringBuilder.append("\n");
        }
        outputArea.setValue(stringBuilder.toString());
    }

}
