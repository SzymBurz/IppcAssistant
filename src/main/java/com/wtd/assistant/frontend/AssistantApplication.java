package com.wtd.assistant.frontend;

import com.wtd.assistant.frontend.data.DataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Objects;


@SpringBootApplication
public class AssistantApplication {

    @Autowired
    DataProvider dataProvider;
    @Value("${assistant.app.mode}")
    String mode;

    public static void main(String[] args) {
        SpringApplication.run(AssistantApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData() {

        return(args) -> {

            System.out.println("App running mode: " + mode);
            if(Objects.equals(mode, "dataupdate")) {
                System.out.println("updating data ");
                dataProvider.updateEnterprises();
            }
        };
    }
}


