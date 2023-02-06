package com.wtd.assistant.frontend;

import com.wtd.assistant.frontend.dao.AuditDao;
import com.wtd.assistant.frontend.dao.EnterpriseDao;
import com.wtd.assistant.frontend.dao.TripDao;
import com.wtd.assistant.frontend.dao.UserDao;
import com.wtd.assistant.frontend.domain.Audit;
import com.wtd.assistant.frontend.domain.Enterprise;
import com.wtd.assistant.frontend.domain.Trip;
import com.wtd.assistant.frontend.domain.User;
import com.wtd.assistant.frontend.service.TripService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class TripServiceTestSuite {

    @Autowired
    TripService tripService;
    static Enterprise enterpriseToClean;
    static User userToClean;
    static Audit auditToClean;
    static Trip tripToClean;
    static Integer tripId;

    @BeforeAll
    static void testDataSet(@Autowired EnterpriseDao enterpriseDao, @Autowired UserDao userDao, @Autowired AuditDao auditDao, @Autowired TripDao tripDao){
        Enterprise enterprise = new Enterprise("PL - 66 666", "PATIMEX", LocalDate.of(1998, 4, 21));
        enterpriseDao.save(enterprise);
        //enterpriseToClean = enterprise;

        User user = new User("Stachu");
        userDao.save(user);
        //userToClean = user;

        Audit audit = new Audit();
        audit.setEnterprise(enterprise);
        audit.setUser(user);
        auditDao.save(audit);

        Trip trip = new Trip();
        trip.setFirstDay(LocalDate.of(1998, 4, 21));
        tripDao.save(trip);
        auditDao.updateTrip(audit.getAuditId(), trip);
        enterpriseToClean = enterprise;
        userToClean = user;
        auditToClean = audit;
        tripToClean = trip;
        tripId = trip.getTripId();
        System.out.println("enterpriseToClean.getName(): " + enterpriseToClean.getName());
        System.out.println("auditToClean.getEnterprise(): " + auditToClean.getEnterprise());
        System.out.println("auditToClean.getUser(): " + auditToClean.getUser());
        System.out.println("tripToClean.getAudits().get(0).getEnterprise(): " + tripToClean.getAudits());
        System.out.println("tripId: " + tripId);
    }

    @AfterAll
    static void cleanDataSet(@Autowired EnterpriseDao enterpriseDao, @Autowired UserDao userDao, @Autowired AuditDao auditDao, @Autowired TripDao tripDao){
        System.out.println("starts cleaning");
        auditDao.delete(auditToClean);
        System.out.println("audit deleted");
        userDao.delete(userToClean);
        System.out.println("user deleted");
        enterpriseDao.delete(enterpriseToClean);
        System.out.println("enterprise deleted");
        tripDao.delete(tripToClean);
        System.out.println("trip deleted");
    }

    @Test
    void testTest() {

    }

    @Test
    void getByNameTest() {
        System.out.println("getByNameCodeTest");
        List<Trip> tripList = tripService.findTripsByCriteria("Patimex", null, null, null );
        System.out.println(tripList);
        Assertions.assertEquals(1, tripList.size());
    }

    @Test
    void getByCodeTest() {
        System.out.println("getByCodeTest");
        List<Trip> tripList = tripService.findTripsByCriteria("666", null, null, null );
        System.out.println(tripList);
        Assertions.assertEquals(1, tripList.size());
    }

    @Test
    void getByDateStartTest() {
        System.out.println("getByDateStartTest");
        List<Trip> tripList = tripService.findTripsByCriteria(null, LocalDate.of(1998, 4, 19), null, null );
        System.out.println(tripList);
    }

    @Test
    void getByDateEndTest() {
        System.out.println("getByDateEndTest");
        List<Trip> tripList = tripService.findTripsByCriteria(null, null, LocalDate.of(1998, 4, 24), null );
        System.out.println(tripList);
    }

    @Test
    void getByDateStartEndTest() {
        System.out.println("getByDateStartEndTest");
        List<Trip> tripList = tripService.findTripsByCriteria(null, LocalDate.of(1998, 4, 19), LocalDate.of(1998, 4, 24), null );
        System.out.println(tripList);
        Assertions.assertEquals(1, tripList.size());
    }

    @Test
    void getByUserTest() {
        System.out.println("getByUserTest");
        List<Trip> tripList = tripService.findTripsByCriteria(null, null, null, userToClean);
        System.out.println(tripList);
    }

    @Test
    void getByAllCriteriaTest() {
        System.out.println("getByAllCriteriaTest");
        List<Trip> tripList = tripService.findTripsByCriteria("Patimex", LocalDate.of(1998, 4, 19), LocalDate.of(1998, 4, 24), userToClean );
        System.out.println(tripList);
    }

}
