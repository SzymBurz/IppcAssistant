package com.wtd.assistant.frontend.dao;

import com.wtd.assistant.frontend.domain.Enterprise;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EnterpriseDao extends CrudRepository<Enterprise, Integer> {

    //W HQL zawsze nazwa klasy nie tabeli: Enterprise a nie ENTERPRISES

    @Query("SELECT e FROM Enterprise e WHERE e.ippcCode LIKE CONCAT('%', :searchTerm)")
    Optional<Enterprise> findByIppcCodeDigitsOnly(@Param("searchTerm") String searchTerm);

    List<Enterprise> findByAudits_TripId_TripId(int tripId);

    //update
    @Transactional
    @Modifying
    @Query("update Enterprise e set e.expiryDate = :expiryDate where e.enterpriseId = :enterpriseId")
    void updateExpiryDate(@Param(value = "enterpriseId") int enterpriseId, @Param(value = "expiryDate") LocalDate expiryDate);


    @Query("select e from Enterprise e where e.ippcCode = ?1")
    Optional<Enterprise> findByIppcCode(String ippcCode);

    Optional<Enterprise> findByAudits_AuditId(int auditId);




}

//@Modifying
//@Query("update Enterprise u set u.expiryDate = :expiryDate where u.enterpriseId = :enterpriseId")
//void updateExpiryDate(@Param(value = "enterpriseId") int enterpriseId, @Param(value = "expiryDate") Date expiryDate);