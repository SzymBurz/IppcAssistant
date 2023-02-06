package com.wtd.assistant.frontend.service;

import com.wtd.assistant.frontend.dao.UserDao;
import com.wtd.assistant.frontend.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public String userByTripToString(int tripId) {
        Optional<User> optUser = userDao.findByAudits_TripId_TripId(tripId);

        return optUser.isPresent() ? optUser.get().toString() : " ";
    }
}
