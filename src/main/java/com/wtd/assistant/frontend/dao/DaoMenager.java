package com.wtd.assistant.frontend.dao;

import org.springframework.data.repository.CrudRepository;

public class DaoMenager {
    AuditDao auditDao;
    CarDao carDao;
    EnterpriseDao enterpriseDao;
    TripDao tripDao;
    UserDao userDao;

    private static DaoMenager daoMenager;

    public DaoMenager() {
    }

    public static DaoMenager getInstance() {
        if (daoMenager == null) {
            daoMenager = new DaoMenager();
        }
        return daoMenager;
    }
}
