package com.traktraindownloader.service.impl;

import com.traktraindownloader.core.exception.ErrorDataException;
import com.traktraindownloader.core.exception.ErrorException;
import com.traktraindownloader.service.SaveFileService;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;

@Component
public class SaveFileImpl implements SaveFileService {

    @Override
    public void saveFileByPath(File file, byte[] audioBytes) throws ErrorException {
        try(
            FileOutputStream outputStream = new FileOutputStream(file)
        ) {
            outputStream.write(audioBytes);
        } catch (Exception e) {
            throw new ErrorDataException("Unable to save file " + file.getName());
        }

    }
}
