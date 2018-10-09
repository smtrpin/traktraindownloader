package com.traktraindownloader.core;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public abstract class AbstractApplication extends Application {

    private static String[] savedArgs;

    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        context = SpringApplication.run(getClass(), savedArgs);
        context.getAutowireCapableBeanFactory().autowireBean(this);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        context.close();
    }

    protected static void launchApp(Class<? extends AbstractApplication> appClass, String[] args) {
        AbstractApplication.savedArgs = args;
        Application.launch(appClass, args);
    }
}
