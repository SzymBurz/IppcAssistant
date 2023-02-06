package com.wtd.assistant.frontend.service;

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
    @Autowired
    EntityManager em;

    public List<Trip> findTripsByCriteria(String filter, LocalDate date, LocalDate endDate, User userBox) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Trip> cq = cb.createQuery(Trip.class);

        Root<Trip> trip = cq.from(Trip.class);
        Join<Trip, Audit> audits = trip.join("audits"); //!!
        List<Predicate> predicateList = new ArrayList<>();
        if(filter != null) {
            Predicate namePredicate = cb.like(cb.upper(audits.get("enterprise").get("name")), "%"+filter.toUpperCase()+"%");
            Predicate ippcPredicate = cb.like(cb.upper(audits.get("enterprise").get("ippcCode")), "%"+filter.toUpperCase()+"%");
            Predicate ippcNamePredicate = cb.or(namePredicate, ippcPredicate);
            predicateList.add(ippcNamePredicate);
        }
        if(date != null) {
            Predicate datePeriodPredicate = cb.greaterThanOrEqualTo(trip.get("firstDay"), date);
            predicateList.add(datePeriodPredicate);
        }
        if(endDate != null) {
            Predicate datePeriodPredicate = cb.lessThanOrEqualTo(trip.get("firstDay"), endDate);
            predicateList.add(datePeriodPredicate);
        }
        if(userBox != null) {
            Predicate userPredicate = cb.equal(audits.get("user"), userBox);
            predicateList.add(userPredicate);
        }

        Predicate finalQuery = cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
        cq.where(finalQuery);
        TypedQuery<Trip> query = em.createQuery(cq);

        List<Trip> resList = query.getResultList();
        System.out.println("ResultList: " + resList);

        return resList;

    }

}
