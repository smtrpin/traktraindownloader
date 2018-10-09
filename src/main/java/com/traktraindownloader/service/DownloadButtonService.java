package com.traktraindownloader.service;

import com.traktraindownloader.core.exception.ErrorException;
import com.traktraindownloader.entity.Instrumental;

import java.util.List;

public interface DownloadButtonService {
    List<Instrumental> getLinksByUrl(String url) throws ErrorException;
    byte[] downloadByUrl(String url) throws ErrorException;
    void downloadAndSaveByUrl(String caption, String url) throws ErrorException;
}
