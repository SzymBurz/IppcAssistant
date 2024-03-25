package com.wtd.assistant.frontend.service;

import com.wtd.assistant.frontend.dao.UserDao;
import com.wtd.assistant.frontend.domain.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String userByTripToString(int tripId) {
        Optional<User> optUser = userDao.findByAudits_TripId_TripId(tripId);

        return optUser.isPresent() ? optUser.get().toString() : " ";
    }

    public User registerUser(String username, String password) {
        if (userDao.findByName(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setName(username);
        user.setPassword(encodedPassword);

        return userDao.save(user);
    }
}
