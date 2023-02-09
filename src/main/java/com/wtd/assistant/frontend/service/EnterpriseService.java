package com.wtd.assistant.frontend.service;

import com.wtd.assistant.frontend.dao.EnterpriseDao;
import com.wtd.assistant.frontend.domain.Audit;
import com.wtd.assistant.frontend.domain.Enterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class EnterpriseService {


    //private static EnterpriseService enterpriseService;
    @Autowired
    private EnterpriseDao enterpriseDao;

    @Autowired
    EntityManager em;

    public List<Enterprise> findEnterprisesByCriteria(String filter, LocalDate datePickerStart, LocalDate datePickerEnd, Integer tripId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Enterprise> cq = cb.createQuery(Enterprise.class);

        Root<Enterprise> enterprise = cq.from(Enterprise.class);
        Join<Enterprise, Audit> audits = enterprise.join("audits");
        List<Predicate> predicateList = new ArrayList<>();

        if(filter != null) {
            Predicate namePredicate = cb.like(cb.upper(enterprise.get("name")), "%"+filter.toUpperCase()+"%");
            Predicate ippcPredicate = cb.like(cb.upper(enterprise.get("ippcCode")), "%"+filter.toUpperCase()+"%");
            Predicate ippcNamePredicate = cb.or(namePredicate, ippcPredicate);
            predicateList.add(ippcNamePredicate);
        }
        if(datePickerStart != null && datePickerEnd != null) {
            Predicate datePredicate = cb.between(enterprise.get("expiryDate"), datePickerStart, datePickerEnd);
            predicateList.add(datePredicate);
        }
        if(tripId != null) {
            Predicate tripPredicate = cb.equal(audits.get("tripId"), tripId);
            predicateList.add(tripPredicate);
        }

        Predicate finalQuery = cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
        cq.where(finalQuery);
        TypedQuery<Enterprise> query = em.createQuery(cq);

        List<Enterprise> resList = query.getResultList();
        System.out.println("ResultList: " + resList);

        return resList;

    }

    public List<Enterprise> findEnterprisesByCriteria(String filter, LocalDate datePickerStart, LocalDate datePickerEnd) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Enterprise> cq = cb.createQuery(Enterprise.class);

        Root<Enterprise> enterprise = cq.from(Enterprise.class);
        List<Predicate> predicateList = new ArrayList<>();

        if(filter != null) {
            Predicate namePredicate = cb.like(cb.upper(enterprise.get("name")), "%"+filter.toUpperCase()+"%");
            Predicate ippcPredicate = cb.like(cb.upper(enterprise.get("ippcCode")), "%"+filter.toUpperCase()+"%");
            Predicate ippcNamePredicate = cb.or(namePredicate, ippcPredicate);
            predicateList.add(ippcNamePredicate);
        }
        if(datePickerStart != null && datePickerEnd != null) {
            Predicate datePredicate = cb.between(enterprise.get("expiryDate"), datePickerStart, datePickerEnd);
            predicateList.add(datePredicate);
        }

        Predicate finalQuery = cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
        cq.where(finalQuery);
        TypedQuery<Enterprise> query = em.createQuery(cq);

        List<Enterprise> resList = query.getResultList();
        System.out.println("ResultList: " + resList);

        return resList;

    }

    public String enterprisesByAuditTripIdToString(int tripId) {
        List<Enterprise> enterpriseList = enterpriseDao.findByAudits_TripId_TripId(tripId);
        StringBuilder stringBuilder = new StringBuilder(100);

        for(Enterprise e : enterpriseList) {
            stringBuilder.append(e.getName() + " " + e.getIppcCode() + " ");
        }
        return stringBuilder.toString();
    }

    public String enterprisesByAuditTripIdToStringNextLine(int tripId) {
        List<Enterprise> enterpriseList = enterpriseDao.findByAudits_TripId_TripId(tripId);
        StringBuilder stringBuilder = new StringBuilder(100);

        for(Enterprise e : enterpriseList) {
            stringBuilder.append("\n" + e.getName() + " " + e.getIppcCode());
        }
        return stringBuilder.toString();
    }

}
