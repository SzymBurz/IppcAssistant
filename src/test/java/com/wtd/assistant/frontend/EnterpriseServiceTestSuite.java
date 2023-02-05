package com.wtd.assistant.frontend;


import com.wtd.assistant.frontend.dao.AuditDao;
import com.wtd.assistant.frontend.dao.EnterpriseDao;
import com.wtd.assistant.frontend.dao.TripDao;
import com.wtd.assistant.frontend.domain.Audit;
import com.wtd.assistant.frontend.domain.Enterprise;
import com.wtd.assistant.frontend.domain.Trip;
import com.wtd.assistant.frontend.service.EnterpriseService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class EnterpriseServiceTestSuite {

    @Autowired
    EnterpriseService enterpriseService;
    static Enterprise enterpriseToClean;
    static Audit auditToClean;
    static Trip tripToClean;
    static Integer tripId;

    @BeforeAll
    static void testDataSet(@Autowired EnterpriseDao enterpriseDao, @Autowired AuditDao auditDao, @Autowired TripDao tripDao){
        Enterprise enterprise = new Enterprise("PL - 66 666", "PATIMEX", LocalDate.of(1998, 4, 21));
        enterpriseDao.save(enterprise);
        enterpriseToClean = enterprise;
        Audit audit = new Audit();
        audit.setEnterprise(enterprise);
        auditDao.save(audit);
        Trip trip = new Trip();
        tripDao.save(trip);
        auditDao.updateTrip(audit.getAuditId(), trip);
        enterpriseToClean = enterprise;
        auditToClean = audit;
        tripToClean = trip;
        tripId = trip.getTripId();
        System.out.println("enterpriseToClean.getName(): " + enterpriseToClean.getName());
        System.out.println("auditToClean.getEnterprise(): " + auditToClean.getEnterprise());
        System.out.println("tripToClean.getAudits().get(0).getEnterprise(): " + tripToClean.getAudits());
        System.out.println("tripId: " + tripId);
    }

    @AfterAll
    static void cleanDataSet(@Autowired EnterpriseDao enterpriseDao, @Autowired AuditDao auditDao, @Autowired TripDao tripDao){
        System.out.println("starts cleaning");
        auditDao.delete(auditToClean);
        System.out.println("audit deleted");
        enterpriseDao.delete(enterpriseToClean);
        System.out.println("enterprise deleted");
        tripDao.delete(tripToClean);
        System.out.println("trip deleted");
    }


    @Test
    void getByName() {
        System.out.println("getByName");
        List<Enterprise> enList =  enterpriseService.findEnterprisesByCriteria("PATIMEX", null, null, null);
        System.out.println(enList);
        Assertions.assertEquals(1, enList.size());
    }

    @Test
    void getByExpiryDate() {
        System.out.println("getByExpiryDate");
        List<Enterprise> enList =  enterpriseService.findEnterprisesByCriteria(null, LocalDate.of(1998, 4, 20), LocalDate.of(1998, 4, 22), null);
        System.out.println(enList);
        Assertions.assertEquals(1, enList.size());
    }

    @Test
    void getByTripId() {
        System.out.println("getByTripId");
        List<Enterprise> enList =  enterpriseService.findEnterprisesByCriteria(null, null, null, tripId);
        System.out.println(enList);
        Assertions.assertEquals(1, enList.size());
    }

    @Test
    void getByNameExpiryDateAndTripId() {
        System.out.println("getByNameExpiryDateAndTripId");
        List<Enterprise> enList =  enterpriseService.findEnterprisesByCriteria("PATIMEX", LocalDate.of(1998, 4, 20), LocalDate.of(1998, 4, 22), tripId);
        System.out.println(enList);
        Assertions.assertEquals(1, enList.size());
    }
}
