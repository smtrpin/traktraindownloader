package com.traktraindownloader.view.pages.main;

import com.traktraindownloader.entity.Instrumental;
import com.traktraindownloader.view.pages.Pages;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.List;


public interface MainPage extends Pages {
    void visibleResult(Stage stage, AnchorPane anchorPane);
    void setResultForListView(List<Instrumental> instrumentals, ListView<Instrumental> listView);
}
