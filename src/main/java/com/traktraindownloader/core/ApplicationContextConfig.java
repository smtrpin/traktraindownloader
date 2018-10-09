package com.traktraindownloader.core;

import com.traktraindownloader.controller.pages.MainPageController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContextConfig {

    @Bean
    MainPageController getMainPageController() {
        return new MainPageController();
    }
}
