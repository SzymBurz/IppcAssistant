package com.wtd.assistant.frontend.service;

import com.wtd.assistant.frontend.dao.*;
import com.wtd.assistant.frontend.domain.Audit;
import com.wtd.assistant.frontend.domain.Trip;
import com.wtd.assistant.frontend.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TripService {
    TripDao tripDao;

    @Autowired
    EntityManager em;

    AuditDao auditDao;
    ExpenseDao expenseDao;
    EnterpriseDao enterpriseDao;

    UserDao userDao;

    public TripService(TripDao tripDao) {
        this.tripDao = tripDao;
    }

    //TODO
    public List<Trip> findAuditsByCriteria(String filter, LocalDate datePicker, LocalDate endDatePicker, User userBox){

        if(filter != null && datePicker !=null && endDatePicker != null && userBox != null ) {
            return tripDao.findByCriteriaFilterDateUser(filter, datePicker, endDatePicker, userBox);
        }
        else if(filter != null && datePicker != null && endDatePicker != null) {
            return tripDao.findByCriteriaFilterDate(filter, datePicker, endDatePicker);
        }
        else if(filter != null && datePicker ==null && endDatePicker == null && userBox != null) {
            return tripDao.findByCriteriaFilterUser(filter, userBox);
        }
        else if(filter == null && datePicker !=null && endDatePicker != null && userBox != null ) {
            return tripDao.findByCriteriaDateUser(datePicker, endDatePicker, userBox);
        }
        else if(filter != null && datePicker == null && endDatePicker == null) {
            return tripDao.findByCriteriaFilter(filter);
        }
        else if(filter == null && datePicker != null && endDatePicker != null) {
            return tripDao.findByCriteriaDate(datePicker, endDatePicker);
        }
        else if(filter == null && datePicker ==null && endDatePicker == null && userBox != null ) {
            return tripDao.findByCriteriaUser(userBox);
        }
        else {
            return (List<Trip>) tripDao.findAll();
        }



    };

    /* TODO
    public List<Trip> findAuditsByCriteria(String filter, LocalDate datePicker, LocalDate endDatePicker, User userBox) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Trip> cq = cb.createQuery(Trip.class);

        Root<Trip> trip = cq.from(Trip.class);
        Join<Trip, Audit> tripAuditsJoined = trip.join("audits"); //!!
        List<Predicate> predicateList = new ArrayList<>();
        if(filter != null) {
            Predicate namePredicate = cb.like(cb.upper(trip.get("audits").get("enterprise").get("name")), "%"+filter.toUpperCase()+"%");
            Predicate ippcPredicate = cb.like(cb.upper(trip.get("audits").get("enterprise").get("ippcCode")), "%"+filter.toUpperCase()+"%");
            Predicate ippcNamePredicate = cb.or(namePredicate, ippcPredicate);
            predicateList.add(ippcNamePredicate);
        }
        if(datePicker != null && endDatePicker != null) {
            Predicate datePredicate = cb.greaterThanOrEqualTo(trip.get("date"), datePicker);
            Predicate endDatePredicate = cb.lessThan(trip.get("date"), endDatePicker);
            Predicate datePeriodPredicate = cb.and(datePredicate, endDatePredicate);
            predicateList.add(datePeriodPredicate);
        }
        if(userBox != null) {
            Predicate userPredicate = cb.equal(trip.get("audit").get("user"), userBox);
            predicateList.add(userPredicate);
        }


        Predicate finalQuery = cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
        cq.where(finalQuery);
        TypedQuery<Trip> query = em.createQuery(cq);

        List<Trip> resList = query.getResultList();
        System.out.println("ResultList: " + resList);

        return resList;

    }

     */


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
