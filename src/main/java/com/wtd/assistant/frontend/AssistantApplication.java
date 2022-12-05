package com.wtd.assistant.frontend;

import com.wtd.assistant.frontend.dao.AuditDao;
import com.wtd.assistant.frontend.dao.EnterpriseDao;
import com.wtd.assistant.frontend.domain.Audit;
import com.wtd.assistant.frontend.domain.Enterprise;
import com.wtd.assistant.frontend.service.EnterpriseService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class AssistantApplication {

    public static void main(String[] args) {
        SpringApplication.run(com.wtd.assistant.frontend.AssistantApplication.class, args);

    }

    /*

    @Bean
    public CommandLineRunner loadData (EnterpriseDao enDao, AuditDao auDao, EnterpriseService enService) {
        return (args) -> {
            enDao.deleteAll();
            auDao.deleteAll();

            //, LocalDate.of(2022, 12, 12)
            Enterprise raven = new Enterprise("PL-08 001","RAVEN");
            enDao.save(raven);
            enDao.save(new Enterprise("PL-12 004","PALLETENWERK KOZIK"));
            enDao.save(new Enterprise("PL-26 005","PALKO"));
            enDao.save(new Enterprise("PL-12 006", "C.M.C."));
            enDao.save(new Enterprise("PL-22 008", "TOR-PAL"));

            auDao.save(new Audit(raven));
            auDao.save(new Audit(raven));
            auDao.save(new Audit(raven));
            auDao.save(new Audit(raven));
            auDao.save(new Audit(raven));

            auDao.findAll().forEach(audit -> audit.setEnterprise(raven));

            enService.updateExpiryDate(raven.getEnterpriseId(), LocalDate.of(2022, 12, 12));
            //raven.setExpiryDate(new Date(2022, 3, 3));
            //enDao.findById(raven.getEnterpriseId()).get().setExpiryDate(new Date(2022, 3, 3));


        };
    }

     */



}