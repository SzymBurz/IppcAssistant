package com.wtd.assistant.frontend.data;

import com.wtd.assistant.frontend.domain.Enterprise;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EnterpriseMapper {

    public List<Enterprise> dataToEnterpriseObject(Map<Integer, List<String>> data) {

        List<Enterprise> enterprises = new ArrayList<>();

        for (Map.Entry<Integer, List<String>> entry: data.entrySet()) {
            Optional<String> optionalCode= ippcCodeFromXlsx(entry.getValue().get(6));
            String ippcCode = optionalCode.orElse(null);
            String name = entry.getValue().get(1);
            Optional<LocalDate> optionalLocalDate = expiryDateFromXlsx(entry.getValue().get(5));
            LocalDate expiryDate = optionalLocalDate.orElse(null);
            if(name != null && !name.isEmpty() && !name.isBlank() && (ippcCode != null) && !ippcCode.isEmpty() && !ippcCode.isBlank()) {
                enterprises.add(new Enterprise(ippcCode, name, expiryDate));
            } else {
                //TODO handling incomplete records
            }
        }

        return enterprises;

    }

    public Optional<String> ippcCodeFromXlsx(String input) {

        Pattern pattern = Pattern.compile("\\d{2} \\d{3,4}");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return Optional.of("PL - " + matcher.group(0));
        } else {
            return Optional.ofNullable(null);
        }

    }

    public Optional<LocalDate> expiryDateFromXlsx(String input) {
        Pattern pattern = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}");
        Matcher matcher = pattern.matcher(input);
        LocalDate outputDate = null;
        String output = null;
        while (matcher.find())
        {
            output = matcher.group(0);
        }

        if(output != null) {

            try {
                outputDate = LocalDate.of(
                        Integer.parseInt(output.substring(6,10)),
                        Integer.parseInt(output.substring(3,5)),
                        Integer.parseInt(output.substring(0,2)));
                System.out.println(outputDate);
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Invalid input for new LocalDate");
                System.out.println("output: " + output);
            }

        }

        return Optional.ofNullable(outputDate);
    }
}
