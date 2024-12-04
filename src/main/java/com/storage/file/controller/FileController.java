package com.storage.file.controller;

import com.storage.file.service.FileService;
import dto.FileUploadRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api/v1/files")
@RestController
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public String saveFile(
            @RequestParam("file") MultipartFile file, @RequestParam("retentionPeriod") long retentionPeriod) throws IOException
    {
        FileUploadRequest request = new FileUploadRequest(file, retentionPeriod);
        return fileService.saveFile(request);
    }
}
