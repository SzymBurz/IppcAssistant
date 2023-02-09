package com.wtd.assistant.frontend.data;

import com.wtd.assistant.frontend.dao.EnterpriseDao;
import com.wtd.assistant.frontend.domain.Enterprise;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DataProvider {

    @Autowired
    EnterpriseMapper enterpriseMapper;
    @Autowired
    EnterpriseDao enterpriseDao;
    @Autowired
    XlsxReader xr;
    @Autowired
    EnterpriseComparator enterpriseComparator;

    public void updateEnterprises() throws IOException {

        List<Enterprise> inputList = enterpriseMapper.dataToEnterpriseObject(xr.readXlsxToMap());

        System.out.println("Updating Enterprises");
        System.out.println("Enterprises inputList notEmpty: " + !inputList.isEmpty());

        int saved = 0;
        int updated = 0;

        List<String> updatedEnterprises = new ArrayList<>();
        List<String> savedEnterprises = new ArrayList<>();



        for(Enterprise e: inputList) {


            Pattern pattern = Pattern.compile("\\d{2} \\d{3,4}");
            Matcher matcher = pattern.matcher(e.getIppcCode());
            if (matcher.find()) {
                Optional<Enterprise> o = enterpriseDao.findByIppcCodeDigitsOnly(matcher.group(0));
                if (o.isPresent() && enterpriseComparator.compareEnterprises(e, o.get()) && !e.getExpiryDate().isEqual(o.get().getExpiryDate())) {
                    updatedEnterprises.add(o.get().getIppcCode() + " old: " + e.getExpiryDate() + " new: " + o.get().getExpiryDate());
                    enterpriseDao.updateExpiryDate(o.get().getEnterpriseId(), e.getExpiryDate());
                    updated++;
                } else if(o.isEmpty()){
                    enterpriseDao.save(e);
                    savedEnterprises.add(e.getIppcCode());
                    saved++;
                }

            }

        }
        System.out.println("updated enterprises: " + updated);
        System.out.println("saved enterprises: " + saved);

        System.out.println("Updating enterprise database completed");
        System.out.println("updated enterprises: " + updated);
        updatedEnterprises.forEach(System.out::println);
        System.out.println("saved enterprises: " + saved);
        savedEnterprises.forEach(System.out::println);
    }

}
