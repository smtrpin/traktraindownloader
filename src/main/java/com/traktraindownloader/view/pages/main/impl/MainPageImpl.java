package com.traktraindownloader.view.pages.main.impl;

import com.traktraindownloader.core.ExceptionUtil;
import com.traktraindownloader.core.exception.ErrorException;
import com.traktraindownloader.entity.Instrumental;
import com.traktraindownloader.service.DownloadButtonService;
import com.traktraindownloader.view.SceneBuilder;
import com.traktraindownloader.view.pages.main.MainPage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class MainPageImpl implements MainPage {

    @Autowired
    private SceneBuilder view;

    @Autowired
    private DownloadButtonService downloadButtonService;

    @Autowired
    private ExceptionUtil exceptionUtil;

    @Value("${ui.view.path}")
    private String path;

    @Value("${ui.view.main}")
    private String page;

    @Value("${ui.mainPage.title}")
    private String title;

    @Value("${ui.mainPage.width}")
    private Integer width;

    @Value("${ui.mainPage.height}")
    private Integer height;

    @Value("${ui.mainPage.resizable}")
    private Boolean resizable;

    @Value("${ui.mainPage.result.width}")
    private Integer resultWidth;

    @Value("${ui.mainPage.result.height}")
    private Integer resultHeight;

    @Value("${downloadUrl}")
    private String downloadUrl;

    @Override
    public void createStage(Stage stage) throws ErrorException {
        view.setStage(stage)
                .setPathToView(path + page)
                .setTitle(title)
                .setWidth(width)
                .setHeight(height)
                .setResizable(resizable)
                .build();
    }

    @Override
    public void visibleResult(Stage stage, AnchorPane anchorPane) {
        stage.setHeight(height + resultHeight);
        anchorPane.setMinWidth(resultWidth);
        anchorPane.setMinHeight(resultHeight);
        anchorPane.setVisible(true);
    }

    @Override
    public void setResultForListView(List<Instrumental> instrumentals, ListView<Instrumental> listView) {
        listView.setItems(FXCollections.observableArrayList(instrumentals));
        listView.setCellFactory(list -> new ListCell<Instrumental>() {
            @Override
            public void updateItem(final Instrumental instrumental, final boolean empty) {
                super.updateItem(instrumental, empty);
                if (empty) {
                    setText("");
                    setGraphic(null);
                } else {
                    Platform.runLater(() -> {
                        Button downloadButton = new Button();
                        downloadButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/assets/download_icon.png"))));
                        downloadButton.setBorder(Border.EMPTY);
                        downloadButton.setBackground(Background.EMPTY);

                        downloadButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                            try {
                                downloadButtonService.downloadAndSaveByUrl(instrumental.getCaption(), downloadUrl + instrumental.getDataId());
                            } catch (ErrorException exception) {
                                exceptionUtil.printException(exception);
                                exception.printStackTrace();
                            }
                        });

                        Label caption = new Label(instrumental.getCaption());
                        HBox hBox = new HBox(instrumental.getImg(), caption, downloadButton);
                        instrumental.getImg().prefWidth(80);
                        caption.setPrefWidth(590);
                        caption.setPadding(new Insets(10, 0, 0, 10));
                        downloadButton.setPrefWidth(80);
                        setGraphic(hBox);
                        setId(instrumental.getDataId());
                    });
                }
            }
        });
        listView.setVisible(true);
    }
}
