package com.traktraindownloader.view.impl;

import com.traktraindownloader.core.exception.ErrorException;
import com.traktraindownloader.core.exception.ErrorViewException;
import com.traktraindownloader.view.SceneBuilder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class SceneBuilderImpl implements SceneBuilder {

    @Autowired
    ApplicationContext applicationContext;

    private Stage stage;

    private String title;

    private Integer width;

    private Integer height;

    private Boolean resizable;

    private String pathToView;

    public SceneBuilder setStage(Stage stage) {
        this.stage = stage;
        return this;
    }

    public SceneBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public SceneBuilder setWidth(Integer width) {
        this.width = width;
        return this;
    }

    public SceneBuilder setHeight(Integer height) {
        this.height = height;
        return this;
    }

    public SceneBuilder setResizable(Boolean resizable) {
        this.resizable = resizable;
        return this;
    }

    public SceneBuilder setPathToView(String pathToView) {
        this.pathToView = pathToView;
        return this;
    }

    @Override
    public void build() throws ErrorException {
        if (stage == null) {
            throw new ErrorViewException("Stage can not be null");
        }
        if (pathToView == null) {
            throw new ErrorViewException("PathToView can not be null");
        }
        URL viewUrl = getClass().getResource(pathToView);
        if (viewUrl == null) {
            throw new ErrorViewException("View " + pathToView + " not found");
        }
        FXMLLoader fxmlLoader = new FXMLLoader(viewUrl);
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            throw new ErrorViewException("Unable to create scene");
        }
        if (title != null) {
            stage.setTitle(title);
        }
        if (width != null) {
            stage.setWidth(width);
        }
        if (height != null) {
            stage.setHeight(height);
        }
        if (resizable != null) {
            stage.setResizable(resizable);
        }
    }

}
