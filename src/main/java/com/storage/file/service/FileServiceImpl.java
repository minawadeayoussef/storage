package com.storage.file.service;

import com.storage.file.config.FileServiceConfig;
import com.storage.file.exception.FileSaveException;
import com.storage.file.exception.InvalidFileException;
import com.storage.file.exception.InvalidRetentionPeriodException;
import com.storage.file.model.StoredFile;
import com.storage.file.repository.StoredFileRepository;
import dto.FileUploadRequest;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class FileServiceImpl implements FileService
{
    private final FileServiceConfig fileServiceConfig;
    private final StoredFileRepository storedFileRepository;
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public FileServiceImpl(FileServiceConfig fileServiceConfig, StoredFileRepository storedFileRepository) {
        this.fileServiceConfig = fileServiceConfig;
        this.storedFileRepository = storedFileRepository;
    }



    @Transactional
    public String saveFile(FileUploadRequest fileUploadRequest) throws IOException {

        String filePath = fileServiceConfig.getFileSavePath() + "/" + fileUploadRequest.getFile().getOriginalFilename();
        Path path = Paths.get(filePath);

        if (fileUploadRequest.getFile().isEmpty()) {
            logger.error("Attempted to save an empty file");
            throw new InvalidFileException("File must not be empty");
        }

        long retentionPeriod = fileUploadRequest.getRetentionPeriod();
        if (retentionPeriod <= 0) {
            logger.error("Invalid retention period: {}", retentionPeriod);
            throw new InvalidRetentionPeriodException("Retention period must be positive");
        }

        Files.createDirectories(path.getParent());
        Files.write(path, fileUploadRequest.getFile().getBytes());
        logger.info("File saved successfully at: {}", path);

        StoredFile storedFile = new StoredFile();
        storedFile.setFileName(fileUploadRequest.getFile().getOriginalFilename());
        storedFile.setFilePath(filePath);
        storedFile.setRetentionPeriod(retentionPeriod);
        storedFile.setCreationTimestamp(System.currentTimeMillis());

        storedFileRepository.save(storedFile);
        logger.info("File metadata saved to the database for file: {}", fileUploadRequest.getFile().getOriginalFilename());

        return filePath;
    }

    @Override
    @Scheduled(fixedRateString = "${file.delete.cycle-time}")
    public void deleteExpiredFiles() {
        long currentTime = System.currentTimeMillis();
        List<StoredFile> filesToDelete = storedFileRepository.findAll();

        for (StoredFile file : filesToDelete) {
            long fileAge = currentTime - file.getCreationTimestamp();
            if (fileAge > file.getRetentionPeriod()) {
                File fileToDelete = new File(file.getFilePath());
                if (fileToDelete.exists() && fileToDelete.delete()) {
                    storedFileRepository.delete(file);
                }
            }
        }

    }
}
