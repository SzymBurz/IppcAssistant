package com.wtd.assistant.frontend.sample;

import com.wtd.assistant.frontend.dao.EnterpriseDao;
import com.wtd.assistant.frontend.domain.Enterprise;
import org.springframework.context.annotation.Bean;


public class SampleSuiteGenerator {

    EnterpriseDao enterpriseDao;

    public SampleSuiteGenerator() {
    }

    void setEnterprises() {
        Enterprise a = new Enterprise("PL-08 001","RAVEN");
        Enterprise b = new Enterprise("PL-12 004","PALLETNERK KOZIK");
        Enterprise c = new Enterprise("PL-26 005","PALKO");
        Enterprise d = new Enterprise("PL-12 006", "C.M.C.");
        Enterprise e = new Enterprise("PL-22 008", "TOR-PAL");

        enterpriseDao.save(a);
        enterpriseDao.save(b);
        enterpriseDao.save(c);
        enterpriseDao.save(d);
        enterpriseDao.save(e);
    }

    void cleanEnterprisesTable() {
        enterpriseDao.deleteAll();
    }
}
