package com.wtd.assistant.frontend.dao;

import com.wtd.assistant.frontend.domain.Expense;
import com.wtd.assistant.frontend.domain.Trip;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ExpenseDao extends CrudRepository<Expense, Integer > {

    @Transactional
    @Modifying
    @Query("update Expense e set e.tripId = ?2 where e.expense_id = ?1")
    void updateTrip(int expense_id, Trip trip);

}
