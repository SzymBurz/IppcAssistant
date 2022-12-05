package com.wtd.assistant.frontend.dao;


import com.wtd.assistant.frontend.domain.Car;
import org.springframework.data.repository.CrudRepository;

public interface CarDao extends CrudRepository<Car, Integer> {
}
