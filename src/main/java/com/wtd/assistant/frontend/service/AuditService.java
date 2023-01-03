package com.wtd.assistant.frontend.service;

import com.wtd.assistant.frontend.dao.AuditDao;
import com.wtd.assistant.frontend.domain.Audit;
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
public class AuditService {

    private static AuditService auditService;

    @Autowired
    EntityManager em;


    @Autowired
    private AuditDao auditDao;

    public List<Audit> findAuditsByCriteria(String filter, LocalDate datePicker, User userBox, boolean notCompleted, boolean completed) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Audit> cq = cb.createQuery(Audit.class);

        Root<Audit> audit = cq.from(Audit.class);
        audit.fetch("enterprise",  JoinType.LEFT);
        List<Predicate> predicateList = new ArrayList<>();
        if(filter != null) {
            Predicate namePredicate = cb.like(cb.upper(audit.get("enterprise").get("name")), "%"+filter.toUpperCase()+"%");
            Predicate ippcPredicate = cb.like(cb.upper(audit.get("enterprise").get("ippcCode")), "%"+filter.toUpperCase()+"%");
            Predicate ippcNamePredicate = cb.or(namePredicate, ippcPredicate);
            predicateList.add(ippcNamePredicate);
        }
        if(datePicker != null) {
            Predicate datePredicate = cb.greaterThanOrEqualTo(audit.get("date"), datePicker);
            predicateList.add(datePredicate);
        }
        if(userBox != null) {
            Predicate userPredicate = cb.equal(audit.get("user"), userBox);
            predicateList.add(userPredicate);
        }
        if (!completed && notCompleted) {
            Predicate completedPredicate = cb.equal(audit.get("completed"), 0);
            predicateList.add(completedPredicate);
        } else if (completed && !notCompleted) {
            Predicate completedPredicate = cb.equal(audit.get("completed"), 1);
            predicateList.add(completedPredicate);
        }

        Predicate finalQuery = cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
        cq.where(finalQuery);
        TypedQuery<Audit> query = em.createQuery(cq);

        List<Audit> resList = query.getResultList();
        System.out.println("ResultList: " + resList);

        return resList;

    }


    public List<Audit> findAll(String filter) {
        if (filter == null || filter.isEmpty()) {
            return (List<Audit>) auditDao.findAll();
        } else {
            return auditDao.findByEnterpriseNameLike(filter);
        }
    }

    public void updateAudit(int auditId, User user, LocalDate date, String remarks, LocalDate secondTerm){
        if (user != null) {
            auditDao.updateUserByAuditId(user, auditId);
        }
        if (date != null) {
            auditDao.updateDateByAuditId(date, auditId);
        }
        if (remarks != null) {
            auditDao.updateRemarksBy(remarks, auditId);
        }
        if (secondTerm != null) {
            auditDao.updateSecondTermByAuditId(secondTerm, auditId);
        }
    }
}
