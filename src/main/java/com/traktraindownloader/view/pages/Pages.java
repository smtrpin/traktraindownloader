package com.traktraindownloader.view.pages;

import com.traktraindownloader.core.exception.ErrorException;
import javafx.stage.Stage;

import java.io.IOException;

public interface Pages {
    void createStage(Stage stage) throws ErrorException;
}
