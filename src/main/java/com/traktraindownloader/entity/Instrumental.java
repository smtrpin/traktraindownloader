package com.traktraindownloader.entity;

import javafx.scene.image.ImageView;

public class Instrumental {

    private ImageView img;

    private String caption;

    private String dataId;

    public Instrumental(ImageView img, String caption, String dataId) {
        this.img = img;
        this.caption = caption;
        this.dataId = dataId;
    }

    public ImageView getImg() {
        return img;
    }

    public String getCaption() {
        return caption;
    }

    public String getDataId() {
        return dataId;
    }
}
