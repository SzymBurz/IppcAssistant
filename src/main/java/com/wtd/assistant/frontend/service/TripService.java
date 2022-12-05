package com.wtd.assistant.frontend.service;

import com.wtd.assistant.frontend.dao.*;
import org.springframework.stereotype.Service;

@Service
public class TripService {
    TripDao tripDao;

    AuditDao auditDao;
    ExpenseDao expenseDao;
    EnterpriseDao enterpriseDao;

    UserDao userDao;

    public TripService(TripDao tripDao) {
        this.tripDao = tripDao;
    }


    //TODO TripDay n longer exists
    /*
    public void getTripExpensesSummary(int tripId) {

        List<String> summary = new ArrayList<>();

        summary.add("Trip ID: " + tripId);
        summary.add("Auditor(s): " + userDao.findByAudits_AuditId(tripDayDao.findByTrip_TripId(tripId).get(0).getDayId()));
        summary.add("Enterprise(s): ");

        for (TripDay tripDay : tripDayDao.findByTrip_TripId(tripId)) {
            List<Audit> audits = auditDao.findByTripDay_dayId(tripDay.getDayId());
             for (Audit audit : audits) {
                 summary.add(enterpriseDao.findByAudits_AuditId(audit.getAuditId()).toString());
             }
        }

        summary.add("Day(s): ");

        for (TripDay tripDay : tripDayDao.findByTrip_TripId(tripId)) {
            summary.add(tripDay.toString());
        }

        summary.add("Expenses: ");

        for (TripDay tripDay : tripDayDao.findByTrip_TripId(tripId)) {
            expenseDao.findByTripDay_dayId(tripDay.getDayId());
            summary.add(expenseDao.findByTripDay_dayId(tripDay.getDayId()).toString());
        }

        System.out.println(summary);
    }

     */

}
