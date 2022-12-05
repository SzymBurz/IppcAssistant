package com.wtd.assistant.frontend.service;

import com.wtd.assistant.frontend.dao.AuditDao;
import com.wtd.assistant.frontend.domain.Audit;
import com.wtd.assistant.frontend.domain.Enterprise;
import com.wtd.assistant.frontend.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AuditService {

    private static AuditService auditService;


    @Autowired
    private AuditDao auditDao;

    public List<Audit> findAll(String filter) {
        if (filter == null || filter.isEmpty()) {
            return (List<Audit>) auditDao.findAll();
        } else {
            return auditDao.findByEnterpriseNameLike(filter);
        }
    }

    public void updateAudit(int auditId, User user, LocalDate date, String remarks, LocalDate secondTerm){
        if (user != null) {
            auditDao.updateUserByAuditId(user, auditId);
        }
        if (date != null) {
            auditDao.updateDateByAuditId(date, auditId);
        }
        if (remarks != null) {
            auditDao.updateRemarksBy(remarks, auditId);
        }
        if (secondTerm != null) {
            auditDao.updateSecondTermByAuditId(secondTerm, auditId);
        }
    }

}
