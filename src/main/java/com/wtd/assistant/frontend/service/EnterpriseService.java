package com.wtd.assistant.frontend.service;

import com.wtd.assistant.frontend.dao.EnterpriseDao;
import com.wtd.assistant.frontend.domain.Enterprise;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class EnterpriseService {

    private Set enterprises;
    private static EnterpriseService enterpriseService;
    private EnterpriseDao enterpriseDao;

    public EnterpriseService(EnterpriseDao enterpriseDao) {
        this.enterpriseDao = enterpriseDao;
    }

    public List<Enterprise> findAll() {
        return (List<Enterprise>) enterpriseDao.findAll();
    }

    //Overloading
    public List<Enterprise> findAll(String filter) {
        if (filter == null || filter.isEmpty()) {
            return (List<Enterprise>) enterpriseDao.findAll();
        } else {
            return enterpriseDao.findByName(filter);
        }
    }

    public List<Enterprise> findByNameAndPeriod(String filter, LocalDate expiryDateStart, LocalDate expiryDateEnd) {
            return enterpriseDao.findByNameAndPeriod(filter, expiryDateStart, expiryDateEnd);
    }

    public String enterprisesByAuditTripIdToString(int tripId) {
        List<Enterprise> enterpriseList = enterpriseDao.findByAudits_TripId_TripId(tripId);
        StringBuilder stringBuilder = new StringBuilder(100);

        for(Enterprise e : enterpriseList) {
            stringBuilder.append(e.getName() + " " + e.getIppcCode() + " ");
        }
        return stringBuilder.toString();
    }

    public String enterprisesByAuditTripIdToStringNextLine(int tripId) {
        List<Enterprise> enterpriseList = enterpriseDao.findByAudits_TripId_TripId(tripId);
        StringBuilder stringBuilder = new StringBuilder(100);

        for(Enterprise e : enterpriseList) {
            stringBuilder.append("\n" + e.getName() + " " + e.getIppcCode());
        }
        return stringBuilder.toString();
    }

    public void updateExpiryDate(int enterpriseId, LocalDate newExpiryDate) {
        //Date expiryDate = Date.valueOf(newExpiryDate);
        //enterpriseDao.updateExpiryDate(enterpriseId, expiryDate);
        enterpriseDao.updateExpiryDate(enterpriseId, newExpiryDate);
    }

    public void updateName(int enterpriseId, String name) {
        enterpriseDao.updateName(enterpriseId, name);
    }

    public void updateCode(int enterpriseId, String code) {
        enterpriseDao.updateName(enterpriseId, code);
    }

}
