package com.wtd.assistant.frontend;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Space for me to learn

public class JavaRegexTest {

    @Test
    void findTest() {

        String input = "22 222, 44 444";
        Pattern pattern = Pattern.compile("\\d{2} \\d{3}");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find())
        {
            System.out.println(matcher.group(0));
        }
    }

    @Test
    void findLast() {
        String input = "22 222, 44 444, 23 456 77 888";
        Pattern pattern = Pattern.compile("\\d{2} \\d{3}");
        Matcher matcher = pattern.matcher(input);
        String output = null;
        while (matcher.find())
        {
            output = matcher.group(0);
        }

        if(output != null) {
            System.out.println(output);
        }

    }

    @Test
    void returnStringTest() {
        String input = "22 222, 44 444";
        Pattern pattern = Pattern.compile("\\d{2} \\d{3}");
        Matcher matcher = pattern.matcher(input);
        matcher.find();
        System.out.println(matcher.group(0));
    }

    @Test
    void lastPatternToLocalDateTest() {
        String input = "27.10.2021\n" +
                "26.10.2023\n" +
                "232-ZFT-647/2021";
        Pattern pattern = Pattern.compile("\\d{2}.\\d{2}.\\d{4}");
        Matcher matcher = pattern.matcher(input);

        String output = null;
        while (matcher.find())
        {
            output = matcher.group(0);
        }

        if(output != null) {
            System.out.println(output.substring(0,2));
            System.out.println(output.substring(3,5));
            System.out.println(output.substring(6,10));

            LocalDate outputDate = LocalDate.of(
                    Integer.parseInt(output.substring(6,10)),
                    Integer.parseInt(output.substring(3,5)),
                    Integer.parseInt(output.substring(0,2)));
            System.out.println(outputDate);
        }

    }

}
