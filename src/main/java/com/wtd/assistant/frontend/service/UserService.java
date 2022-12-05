package com.wtd.assistant.frontend.service;

import com.wtd.assistant.frontend.dao.UserDao;
import com.wtd.assistant.frontend.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static UserService userService;


    @Autowired
    private UserDao userDao;

    public String userByTrip(int tripId) {
        Optional<User> optUsr = userDao.findByAudits_TripId_TripId(tripId);

        if(optUsr.isPresent()) {
            return userDao.findByAudits_TripId_TripId(tripId).get().getName();
        } else {
            return " ";
        }
    }
}
