package com.traktraindownloader.service.impl;

import com.traktraindownloader.core.ExceptionUtil;
import com.traktraindownloader.core.exception.ErrorDataException;
import com.traktraindownloader.core.exception.ErrorException;
import com.traktraindownloader.entity.Instrumental;
import com.traktraindownloader.service.DownloadButtonService;
import com.traktraindownloader.service.PageService;
import com.traktraindownloader.service.SaveFileService;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class DownloadButtonServiceImpl implements DownloadButtonService {

    @Value("${targetUrl}")
    String targetUrl;

    @Value("${downloadUrl}")
    String downloadUrl;

    @Autowired
    PageService pageService;

    @Autowired
    private SaveFileService saveFileService;

    @Autowired
    private ExceptionUtil exceptionUtil;

    @Override
    public List<Instrumental> getLinksByUrl(String url) throws ErrorException {
        validateUrl(url);
        return pageService.getInstrumentalByUrl(url);
    }

    @Override
    public byte[] downloadByUrl(String url) throws ErrorException {
        if (!url.isEmpty() && url.contains(downloadUrl)) {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
            request.addHeader("Accept", "audio/webm,audio/ogg,audio/wav,audio/*;q=0.9,application/ogg;q=0.7,video/*;q=0.6,*/*;q=0.5");
            request.addHeader("Referer", "https://traktrain.com/");
            HttpResponse httpResponse;
            try {
                httpResponse = httpClient.execute(request);
            } catch (IOException e) {
                throw new ErrorDataException("Unable to execute request");
            }
            if (httpResponse.getStatusLine().getStatusCode() == 200 || httpResponse.getStatusLine().getStatusCode() == 206) {
                try {
                    return EntityUtils.toByteArray(httpResponse.getEntity());
                } catch (IOException e) {
                    throw new ErrorDataException("Unable to get byte code");
                }
            }
            throw new ErrorDataException("Unable to download from " + url + " address");
        }
        throw new ErrorDataException("Incorrect url. Url must have " + downloadUrl);
    }

    @Override
    public void downloadAndSaveByUrl(String caption, String url) throws ErrorException {
        if (!url.isEmpty()) {
            byte[] audioBytes = downloadByUrl(url);
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save " + caption + " file");
            fileChooser.setInitialFileName(caption + ".mp3");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("audio", "*.mp3"));
            Platform.runLater(() -> {
                File file = fileChooser.showSaveDialog(new Stage());
                if (file != null) {
                    try {
                        saveFileService.saveFileByPath(file, audioBytes);
                    } catch (ErrorException e) {
                        exceptionUtil.printException(e);
                        e.printStackTrace();
                    }
                }
            });
        } else {
            throw new ErrorDataException("Url can not be empty");
        }
    }

    private void validateUrl(String url) throws ErrorException {
        if (!url.contains(targetUrl)) {
            throw new ErrorDataException("Url must begin with " + targetUrl);
        }
    }
}
