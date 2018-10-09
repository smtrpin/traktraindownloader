package com.traktraindownloader.service;

import com.traktraindownloader.core.exception.ErrorException;

import java.io.File;
import java.io.IOException;

public interface SaveFileService {
    void saveFileByPath(File file, byte[] audioBytes) throws ErrorException;
}
