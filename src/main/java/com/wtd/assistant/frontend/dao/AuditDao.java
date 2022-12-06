package com.wtd.assistant.frontend.dao;


import com.wtd.assistant.frontend.domain.Audit;
import com.wtd.assistant.frontend.domain.Enterprise;
import com.wtd.assistant.frontend.domain.Trip;
import com.wtd.assistant.frontend.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface AuditDao extends CrudRepository<Audit, Integer> {

    List<Audit> findByEnterprise(Optional<Enterprise> enterprise);
    List<Audit> findAll();

    @Query("SELECT a FROM Audit a JOIN a.enterprise e WHERE e.name LIKE CONCAT('%', :searchTerm, '%')")
    List<Audit> findByEnterpriseNameLike(@Param("searchTerm") String searchTerm);

    List<Audit> findByTripId_TripId(int tripId);

    List<Audit> findByCompleted(Boolean completed);

    List<Audit> findByDateGreaterThanEqual(LocalDate date);

    List<Audit> findBySecondTermNotNullAndSecondTermGreaterThanEqual(LocalDate secondTerm);

    @Query("select a from Audit a " +
            "where (a.completed = true and a.user.userId = ?2) and upper(a.enterprise.name) like upper(concat('%', ?1, '%')) or upper(a.enterprise.ippcCode) like upper(concat('%', ?1, '%'))")
    List<Audit> findByFilteringConditionsCompletedTrue(String name, int userId);




    @Modifying
    @Query("update Audit a set a.user = ?1 where a.auditId = ?2")
    void updateUserByAuditId(User user, int auditId);

    @Modifying
    @Query("update Audit a set a.date = ?1 where a.auditId = ?2")
    void updateDateByAuditId(LocalDate date, int auditId);



    @Modifying
    @Query("update Audit a set a.completed = ?1 where a.auditId = ?2")
    void updateCompletedByAuditId(Boolean completed, int auditId);

    @Modifying
    @Query("update Audit a set a.remarks = ?1 where a.auditId = ?2")
    void updateRemarksBy(String remarks, int auditId);

    @Modifying
    @Query("update Audit a set a.secondTerm = ?1 where a.auditId = ?2")
    void updateSecondTermByAuditId(LocalDate secondTerm, int auditId);



    //Było zmieniane
    @Modifying
    @Query("update Audit a set a.tripId = ?2 where a.auditId = ?1")
    void updateTrip(int auditId, Trip tripId);

    @Modifying
    @Query("update Audit a set a.completed = 1 where a.auditId = ?1")
    void setCompleted(int auditId);



    //@Modifying
    ///@Query("update Audit a set a.user = :user where a.auditId = :auditId")
    //void updateUserByAuditId(@Param(value = "auditId")int auditId, @Param(value = "user")User user);





    //select a from Audit a join a.enterprise e where e.name LIKE CONCAT('%', :searchTerm, '%')


    //UPDATE

    /*
    @Transactional
    @Modifying
    @Query("update Audit a set a.name = :name where a.auditId = :auditId")
    void updateName(@Param(value = "enterpriseId") int enterpriseId, @Param(value = "name") String name);

     */




}
