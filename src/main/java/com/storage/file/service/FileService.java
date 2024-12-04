package com.storage.file.service;

import dto.FileUploadRequest;

import java.io.IOException;

public interface FileService
{
    String saveFile(FileUploadRequest request) throws IOException;
    void deleteExpiredFiles();
}
