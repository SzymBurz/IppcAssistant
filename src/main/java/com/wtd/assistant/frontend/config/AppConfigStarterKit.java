package com.wtd.assistant.frontend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("starterkit")
public class AppConfigStarterKit {

    @Value("${dataupdate.property1}")
    private String property1;
}
