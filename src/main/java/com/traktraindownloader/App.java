package com.traktraindownloader;

import com.traktraindownloader.core.AbstractApplication;
import com.traktraindownloader.view.pages.main.MainPage;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App extends AbstractApplication {

    @Autowired
    private MainPage mainPage;

    public static void main(String[] args) {
        launchApp(App.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainPage.createStage(primaryStage);
        primaryStage.show();
    }
}
