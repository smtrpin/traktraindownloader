package com.traktraindownloader.view;

import com.traktraindownloader.core.exception.ErrorException;
import javafx.stage.Stage;

import java.io.IOException;

public interface SceneBuilder {
    SceneBuilder setStage(Stage stage);
    SceneBuilder setTitle(String title);
    SceneBuilder setWidth(Integer width);
    SceneBuilder setHeight(Integer height);
    SceneBuilder setResizable(Boolean resizable);
    SceneBuilder setPathToView(String pathToView);
    void build() throws ErrorException;
}
