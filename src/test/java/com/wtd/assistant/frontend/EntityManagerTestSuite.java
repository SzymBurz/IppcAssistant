package com.wtd.assistant.frontend;

import com.wtd.assistant.frontend.dao.EnterpriseDao;
import com.wtd.assistant.frontend.domain.Audit;
import com.wtd.assistant.frontend.domain.Enterprise;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class EntityManagerTestSuite {
    /*

    @Autowired
    EntityManager em;

    @Autowired
    private EnterpriseDao enterpriseDao;

    @Test
    void entityManagerQueryTest(){

        String filter = null;
        LocalDate datePickerStart = null;
        LocalDate datePickerEnd = null;
        Integer tripId = null;

        String filter1 = "RAVEN";
        LocalDate datePickerStart1 = null;
        LocalDate datePickerEnd1 = null;
        Integer tripId1 = null;

        System.out.println(findEnterprisesByCriteria(filter, datePickerStart, datePickerEnd, null).size());
        System.out.println(findEnterprisesByCriteria(filter1, datePickerStart1, datePickerEnd1, null).size());

    }

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

     */
}
