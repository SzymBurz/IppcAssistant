package com.wtd.assistant.frontend;

import com.wtd.assistant.frontend.service.AuditService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class AuditServiceTestSuite {

    @Autowired
    AuditService auditService;

    @Test
    void findByUser() {

        auditService.findAuditsByCriteria(null, null, null, false, false);

    }
}
