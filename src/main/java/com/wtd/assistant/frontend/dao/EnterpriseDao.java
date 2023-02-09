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


    //findBy
    @Query("SELECT e FROM Enterprise e WHERE e.name LIKE CONCAT('%', :searchTerm, '%')")
    List<Enterprise> findByName(@Param("searchTerm") String searchTerm);

    @Query("select e from Enterprise e where e.expiryDate between :expiryDateStart and :expiryDateEnd order by e.expiryDate DESC")
    List<Enterprise> findByExpiryDate(@Param("expiryDateStart") LocalDate expiryDateStart,@Param("expiryDateEnd") LocalDate expiryDateEnd);

    @Transactional
    @Query("select e from Enterprise e where e.expiryDate between :expiryDateStart and :expiryDateEnd and e.name LIKE CONCAT('%', :searchTerm, '%')")
    List<Enterprise> findByNameAndPeriod(@Param("searchTerm") String searchTerm,@Param("expiryDateStart") LocalDate expiryDateStart,@Param("expiryDateEnd") LocalDate expiryDateEnd);

    @Query("SELECT e FROM Enterprise e WHERE e.name LIKE CONCAT('%', :searchTerm, '%') OR e.ippcCode LIKE CONCAT('%', :searchTerm, '%')")
    List<Enterprise> findByNameOrIppcCode(@Param("searchTerm") String searchTerm);

    @Query("SELECT e FROM Enterprise e WHERE e.ippcCode LIKE CONCAT('%', :searchTerm)")
    Optional<Enterprise> findByIppcCodeDigitsOnly(@Param("searchTerm") String searchTerm);


    @Query("select e from Enterprise e " +
            "where (e.expiryDate between ?1 and ?2) and (e.name like concat('%', ?3, '%') or upper(e.ippcCode) like concat('%', ?4, '%'))")
    List<Enterprise> findByExpiryDateBetweenAndNameLikeIgnoreCaseOrIppcCodeLikeIgnoreCase(LocalDate expiryDateStart, LocalDate expiryDateEnd, String name, String ippcCode);

    List<Enterprise> findByAudits_TripId_TripId(int tripId);



    //update
    @Transactional
    @Modifying
    @Query("update Enterprise e set e.expiryDate = :expiryDate where e.enterpriseId = :enterpriseId")
    void updateExpiryDate(@Param(value = "enterpriseId") int enterpriseId, @Param(value = "expiryDate") LocalDate expiryDate);

    @Transactional
    @Modifying
    @Query("update Enterprise e set e.ippcCode = :ippcCode where e.enterpriseId = :enterpriseId")
    void updateIppcCode(@Param(value = "enterpriseId") int enterpriseId, @Param(value = "ippcCode") String ippcCode);

    @Transactional
    @Modifying
    @Query("update Enterprise e set e.name = :name where e.enterpriseId = :enterpriseId")
    void updateName(@Param(value = "enterpriseId") int enterpriseId, @Param(value = "name") String name);

    @Query("select e from Enterprise e where e.ippcCode = ?1")
    Optional<Enterprise> findByIppcCode(String ippcCode);

    Optional<Enterprise> findByAudits_AuditId(int auditId);




}

//@Modifying
//@Query("update Enterprise u set u.expiryDate = :expiryDate where u.enterpriseId = :enterpriseId")
//void updateExpiryDate(@Param(value = "enterpriseId") int enterpriseId, @Param(value = "expiryDate") Date expiryDate);