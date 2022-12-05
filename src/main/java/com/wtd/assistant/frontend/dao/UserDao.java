package com.wtd.assistant.frontend.dao;

import com.wtd.assistant.frontend.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserDao extends CrudRepository<User, Integer> {

    User findByAudits_AuditId(int auditId);

    @Query("select u from User u inner join u.audits audits where audits.tripId.tripId = ?1")
    Optional<User> findByAudits_TripId_TripId(int tripId);




}
