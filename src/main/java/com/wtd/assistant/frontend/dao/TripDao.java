package com.wtd.assistant.frontend.dao;

import com.wtd.assistant.frontend.domain.Trip;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface TripDao extends CrudRepository<Trip, Integer> {

}
