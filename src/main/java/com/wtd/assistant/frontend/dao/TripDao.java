package com.wtd.assistant.frontend.dao;


import com.wtd.assistant.frontend.domain.Trip;
import com.wtd.assistant.frontend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Repository
public interface TripDao extends JpaRepository<Trip, Integer> {
    @Query("select t from Trip t left join t.audits audits " +
            "where (upper(audits.enterprise.name) like upper(concat('%', ?1, '%')) or upper(audits.enterprise.ippcCode) like upper(concat('%', ?1, '%'))) and t.firstDay between ?2 and ?3 and audits.user = ?4")
    List<Trip> findByCriteriaFilterDateUser(String filter, LocalDate firstDayStart, LocalDate firstDayEnd, User user);

    @Query("select t from Trip t left join t.audits audits " +
            "where (upper(audits.enterprise.name) like upper(concat('%', ?1, '%')) or upper(audits.enterprise.ippcCode) like upper(concat('%', ?1, '%'))) and t.firstDay between ?2 and ?3")
    List<Trip> findByCriteriaFilterDate(String filter, LocalDate firstDayStart, LocalDate firstDayEnd);

    @Query("select t from Trip t left join t.audits audits " +
            "where (upper(audits.enterprise.name) like upper(concat('%', ?1, '%')) or upper(audits.enterprise.ippcCode) like upper(concat('%', ?1, '%'))) and audits.user = ?2")
    List<Trip> findByCriteriaFilterUser(String filter, User user);

    @Query("select t from Trip t left join t.audits audits " +
            "where  t.firstDay between ?1 and ?2 and audits.user = ?3")
    List<Trip> findByCriteriaDateUser(LocalDate firstDayStart, LocalDate firstDayEnd, User user);

    @Query("select t from Trip t left join t.audits audits " +
            "where upper(audits.enterprise.name) like upper(concat('%', ?1, '%')) or upper(audits.enterprise.ippcCode) like upper(concat('%', ?1, '%'))")
    List<Trip> findByCriteriaFilter(String filter);

    @Query("select t from Trip t left join t.audits audits " +
            "where t.firstDay between ?1 and ?2")
    List<Trip> findByCriteriaDate(LocalDate firstDayStart, LocalDate firstDayEnd);

    @Query("select t from Trip t left join t.audits audits " +
            "where audits.user = ?1")
    List<Trip> findByCriteriaUser(User user);




}
