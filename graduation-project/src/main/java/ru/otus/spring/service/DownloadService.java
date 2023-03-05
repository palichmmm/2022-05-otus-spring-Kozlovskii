package ru.otus.spring.service;

import org.springframework.core.io.Resource;

public interface DownloadService {

    Resource downloadFile(String fileName);

    Resource downloadAllFileZip();

    String outputFileName(String realFileName);
}
