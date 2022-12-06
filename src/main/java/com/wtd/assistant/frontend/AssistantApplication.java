package com.wtd.assistant.frontend;

import com.wtd.assistant.frontend.dao.*;
import com.wtd.assistant.frontend.domain.Audit;
import com.wtd.assistant.frontend.domain.Enterprise;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
public class AssistantApplication {

    public static void main(String[] args) {
        SpringApplication.run(com.wtd.assistant.frontend.AssistantApplication.class, args);
    }

    /*




    @Bean
    public CommandLineRunner loadData (EnterpriseDao enterpriseDao, AuditDao auditDao, TripDao tripDao, UserDao userDao, CarDao carDao) {
        return (args) -> {
            List<Enterprise> enterpriseList = new ArrayList<>();
            enterpriseDao.findAll().forEach(enterpriseList::add);

            Random random = new Random();
            Set<Integer> generated = new LinkedHashSet<Integer>();
            while (generated.size() < 15) {
                Integer next = random.nextInt(20) + 1;
                generated.add(next);
            }
            auditDao.deleteAll();
            for (Integer i : generated) {

                auditDao.save(new Audit(enterpriseList.get(i), userDao.findByUserId(random.nextInt(5) + 1).get(), enterpriseList.get(i).getExpiryDate().plusYears(1l)));
            }
            List<Audit> auditList = new ArrayList<>();
            auditList.addAll(auditDao.findAll());

            for(int i = 0; i == auditList.size()-1; i++) {

            }


            /*
            for (Enterprise e : enterpriseList) {
                System.out.println("enterpriseDao.save(new Enterprise(" + e.getIppcCode() +", "+ e.getName() +", LocalDate.parse("+ e.getExpiryDate()+")));");
             */

                /*
                enterpriseDao.deleteAll();
                auditDao.deleteAll();

                enterpriseDao.save(new Enterprise("PL-22 008", "TOR-PAL", LocalDate.parse("2022-07-16")));
                enterpriseDao.save(new Enterprise("PL-12 006", "C.M.C.", LocalDate.parse("2023-10-26")));
                enterpriseDao.save(new Enterprise("PL-26 005", "PALKO", LocalDate.parse("2024-03-13")));
                enterpriseDao.save(new Enterprise("PL-12 004", "PALLETENWERK KOZIK", LocalDate.parse("2024-01-27")));
                enterpriseDao.save(new Enterprise("PL-08 001", "RAVEN", LocalDate.parse("2022-12-12")));
                enterpriseDao.save(new Enterprise("PL-28 011", "JAR", LocalDate.parse("2022-07-16")));
                enterpriseDao.save(new Enterprise("PL-18 145", "TRAK", LocalDate.parse("2024-02-10")));
                enterpriseDao.save(new Enterprise("PL-02 012", "KADOR", LocalDate.parse("2022-10-25")));
                enterpriseDao.save(new Enterprise("PL-30-013", "PALIMEX", LocalDate.parse("2022-09-29")));
                enterpriseDao.save(new Enterprise("PL-30 014", "ADAMUS", LocalDate.parse("2023-11-03")));
                enterpriseDao.save(new Enterprise("PL-08 015", "PRZEMYSŁ DRZEWNY ŚWIEBODZIN", LocalDate.parse("2022-10-11")));
                enterpriseDao.save(new Enterprise("PL-30 017", "MEBLECH", LocalDate.parse("2023-11-03")));
                enterpriseDao.save(new Enterprise("PL-30 018", "SCANDPOL", LocalDate.parse("2022-10-22")));
                enterpriseDao.save(new Enterprise("PL-02 019", "JAMAX", LocalDate.parse("2023-10-25")));
                enterpriseDao.save(new Enterprise("PL-02 019", "EUREX", LocalDate.parse("2023-03-21")));
                enterpriseDao.save(new Enterprise("PL-32 022", "ZAKŁAD DRZEWNY IRCHA", LocalDate.parse("2022-11-05")));
                enterpriseDao.save(new Enterprise("PL-22 024", "TRANSDREWNEX", LocalDate.parse("2023-03-21")));
                enterpriseDao.save(new Enterprise("PL-08 025", "ERMA", LocalDate.parse("2023-04-08")));
                enterpriseDao.save(new Enterprise("PL-28 026", "DUET", LocalDate.parse("2023-01-28")));
                enterpriseDao.save(new Enterprise("PL-32 027", "INTER-PACK", LocalDate.parse("2024-02-03")));
                enterpriseDao.save(new Enterprise("PL-22 028", "COMPANY LTD", LocalDate.parse("2023-04-11")));




            };
        };

        */
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

