package com.wtd.assistant.frontend.dao;


import com.wtd.assistant.frontend.domain.Audit;
import com.wtd.assistant.frontend.domain.Enterprise;
import com.wtd.assistant.frontend.domain.Trip;
import com.wtd.assistant.frontend.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
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

    List<Audit> findByCompleted(Boolean completed);

    List<Audit> findByDateGreaterThanEqualAndCompletedFalse(LocalDate date);


    List<Audit> findBySecondTermNotNullAndSecondTermGreaterThanEqual(LocalDate secondTerm);

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

    @Modifying
    @Query("update Audit a set a.tripId = ?2 where a.auditId = ?1")
    void updateTrip(int auditId, Trip tripId);

    @Modifying
    @Query("update Audit a set a.completed = 1 where a.auditId = ?1")
    void setCompleted(int auditId);


}
