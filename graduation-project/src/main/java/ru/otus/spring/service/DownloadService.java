package ru.otus.spring.service;

import org.springframework.core.io.Resource;

import java.util.zip.ZipOutputStream;

public interface DownloadService {

    Resource downloadFile(String fileName);

    void createZipStream(ZipOutputStream zipOut);

    String outputFileName(String realFileName);
}
