package com.traktraindownloader.controller.pages;

import com.traktraindownloader.core.ExceptionUtil;
import com.traktraindownloader.core.exception.ErrorException;
import com.traktraindownloader.entity.Instrumental;
import com.traktraindownloader.service.DownloadButtonService;
import com.traktraindownloader.view.pages.main.MainPage;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MainPageController {

    @Autowired
    DownloadButtonService downloadButtonService;

    @Autowired
    ExceptionUtil exceptionUtil;

    @Autowired
    MainPage mainPage;

    @FXML
    private TextField linkTextField;

    @FXML
    private TextField linkTextFieldToDownload;

    @FXML
    private AnchorPane mainStage;

    @FXML
    private AnchorPane resultAnchorPane;

    @FXML
    private ListView<Instrumental> resultList;

    @FXML
    public void downloadClicked(MouseEvent event) {
        Runnable runnable = () -> {
            try {
                List<Instrumental> instrumentals = downloadButtonService.getLinksByUrl(linkTextField.getCharacters().toString());
                if (!instrumentals.isEmpty()) {
                    mainPage.visibleResult((Stage) mainStage.getScene().getWindow(), resultAnchorPane);
                    mainPage.setResultForListView(instrumentals, resultList);
                }
            } catch (ErrorException e) {
                exceptionUtil.printException(e);
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }


    @FXML
    void downloadByLink(MouseEvent event) {
        Runnable runnable = () -> {
            try {
                downloadButtonService.downloadAndSaveByUrl("unnamed", linkTextFieldToDownload.getCharacters().toString());
            } catch (ErrorException e) {
                exceptionUtil.printException(e);
                e.printStackTrace();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    @FXML
    public void initialize() {

    }
}