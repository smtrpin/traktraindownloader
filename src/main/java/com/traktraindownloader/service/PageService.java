package com.traktraindownloader.service;

import com.traktraindownloader.core.exception.ErrorException;
import com.traktraindownloader.entity.Instrumental;

import java.io.IOException;
import java.util.List;

public interface PageService {
    List<Instrumental> getInstrumentalByUrl(String url) throws ErrorException;
}
