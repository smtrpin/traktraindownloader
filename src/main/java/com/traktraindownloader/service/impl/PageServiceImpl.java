package com.traktraindownloader.service.impl;

import com.traktraindownloader.core.ExceptionUtil;
import com.traktraindownloader.core.exception.ErrorDataException;
import com.traktraindownloader.core.exception.ErrorException;
import com.traktraindownloader.entity.Instrumental;
import com.traktraindownloader.service.PageService;
import javafx.scene.image.ImageView;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PageServiceImpl implements PageService {

    @Value("${parser.header.referer}")
    private String referer;

    @Value("${parser.beat.list.selector}")
    private String beatListSelector;

    @Value("${parser.beat.list.image.selector}")
    private String imageSelector;

    @Value("${parser.beat.list.caption}")
    private String captionSelector;

    @Value("${parser.beat.data.id}")
    private String dataIdSelector;

    @Value("${parser.beat.userProfile.image.selector}")
    private String userProfileImageSelector;

    @Value("${list.image.height}")
    private Integer imageHeight;

    @Value("${list.image.width}")
    private Integer imageWidth;

    @Autowired
    private ExceptionUtil exceptionUtil;

    @Override
    public List<Instrumental> getInstrumentalByUrl(String url) throws ErrorException {
        Connection.Response response = getHTML(url);
        if (response.statusCode() != 200) {
            throw new ErrorDataException(url + " return " + response.statusCode() + " code");
        }
        try {
            return getInstrumentalByDocument(response.parse());
        } catch (IOException e) {
            throw new ErrorDataException("Unable to parse HTML");
        }
    }

    private Connection.Response getHTML(String url) throws ErrorException {
        try {
            return Jsoup.connect(url)
                    .header("Referer", referer)
                    .execute();
        } catch (IOException e) {
            throw new ErrorDataException(url + " is invalid");
        }
    }

    private List<Instrumental> getInstrumentalByDocument(Document document) throws ErrorException {
        List<Instrumental> instrumentals = new ArrayList<>();
        Elements elements = document.select(beatListSelector);
        if (elements.size() == 0) {
            throw new ErrorDataException("Find 0 elements for " + beatListSelector + " selector");
        }
        document.select(beatListSelector).forEach(beat -> {
            try {
                String imgUrl = getImg(document, beat);
                ImageView imageView = new ImageView(imgUrl);
                imageView.setFitHeight(imageHeight);
                imageView.setFitWidth(imageWidth);
                String caption = getCaption(beat);
                String dataId = getDataId(beat);
                instrumentals.add(new Instrumental(imageView, caption, dataId));
            } catch(ErrorException e) {
                exceptionUtil.printException(e);
            }

        });
        return instrumentals;
    }

    private String getImg(Document document, Element beat) throws ErrorException {
        String imgUrl = beat.select(imageSelector).attr("src");
        if (imgUrl.isEmpty()) {
            imgUrl = document.select(userProfileImageSelector).attr("src");
        }
        if (imgUrl.isEmpty()) {
            throw new ErrorDataException("Img not found");
        }
        return imgUrl;
    }

    private String getCaption(Element beat) throws ErrorException {
        String caption = beat.select(captionSelector).text();
        if (caption.isEmpty()) {
            throw new ErrorDataException("Caption not found");
        }
        return caption;
    }

    private String getDataId(Element beat) throws ErrorException {
        String dataId =  beat.select(dataIdSelector).attr("data-id");
        if (dataId.isEmpty()) {
            throw new ErrorDataException("dataId not found");
        }
        return dataId;
    }
}
