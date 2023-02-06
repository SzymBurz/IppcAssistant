package com.wtd.assistant.frontend;

import com.wtd.assistant.frontend.generator.SampleDataSuiteGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AssistantApplication {

    @Value("${assistant.app.sampleDataMode}")
    static private boolean mode;

    @Autowired
    static SampleDataSuiteGenerator generator;
    public static void main(String[] args) {
        SpringApplication.run(com.wtd.assistant.frontend.AssistantApplication.class, args);

        if(mode){generator.generateDataSuite();}

    }

}


