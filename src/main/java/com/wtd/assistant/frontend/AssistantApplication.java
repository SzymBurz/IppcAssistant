package com.wtd.assistant.frontend;

import com.wtd.assistant.frontend.dataimport.DataProvider;
import com.wtd.assistant.frontend.generator.SampleDataSuiteGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import java.util.Objects;


@SpringBootApplication
public class AssistantApplication {

    @Autowired
    DataProvider dataProvider;

    @Autowired
    SampleDataSuiteGenerator sampleGenerator;

    @Autowired
    private Environment environment;

    static final Logger log = LoggerFactory.getLogger(AssistantApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AssistantApplication.class, args);
    }

    @Bean
    @Profile("dataupdate")
    public CommandLineRunner loadDataForDataUpdate() {
        return (args) -> {
            for (String activeProfile : environment.getActiveProfiles()) {
                log.info("Spring running in profile: " + activeProfile);
            }
            dataProvider.updateEnterprises();
        };
    }

    @Bean
    @Profile("starterkit")
    public CommandLineRunner loadDataForStarterKit() {
        return (args) -> {
            for (String activeProfile : environment.getActiveProfiles()) {
                log.info("Spring running in profile: " + activeProfile);
            }
            sampleGenerator.starterKit();
        };
    }
}


