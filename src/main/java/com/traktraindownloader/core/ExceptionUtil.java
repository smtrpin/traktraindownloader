package com.traktraindownloader.core;

import com.traktraindownloader.core.exception.ErrorException;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ExceptionUtil {

    @Value("${error.title}")
    private String errorTitle;

    public void printException(Exception e) {
        Platform.runLater(() -> {
            Alert.AlertType alertType = Alert.AlertType.NONE;
            if (e instanceof ErrorException) {
                alertType = Alert.AlertType.ERROR;
            }
            Alert alert = new Alert(alertType);
            alert.setTitle(errorTitle);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        });
    }
}
