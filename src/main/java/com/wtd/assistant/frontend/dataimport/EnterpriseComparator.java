package com.wtd.assistant.frontend.dataimport;

import com.wtd.assistant.frontend.domain.Enterprise;
import org.springframework.stereotype.Component;

@Component
public class EnterpriseComparator {

    public boolean compareEnterprises(Enterprise e, Enterprise o) {

        String eCode = e.getIppcCode().replaceAll("\\D", "");
        String oCode = o.getIppcCode().replaceAll("\\D", "");
        return eCode.equals(oCode);

    }
}
