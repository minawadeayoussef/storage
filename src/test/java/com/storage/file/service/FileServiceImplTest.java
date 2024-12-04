package com.storage.file.service;

import com.storage.file.config.FileServiceConfig;
import com.storage.file.exception.InvalidFileException;
import com.storage.file.exception.InvalidRetentionPeriodException;
import com.storage.file.repository.StoredFileRepository;
import dto.FileUploadRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
 class FileServiceImplTest {

    @InjectMocks
    private FileServiceImpl fileService;


    @Mock
    private MultipartFile mockFile;

    @BeforeEach
    void setUp() {
        // You can mock default values before each test
    }

    @Test
    void testSaveFileEmptyFileThrowsException() {
        // Arrange
        FileUploadRequest request = new FileUploadRequest(mockFile, 100000);
        when(mockFile.getOriginalFilename()).thenReturn("file.txt");
        when(mockFile.isEmpty()).thenReturn(true);

        // Act & Assert
        InvalidFileException thrown = assertThrows(InvalidFileException.class, () -> fileService.saveFile(request));
        assertEquals("File must not be empty", thrown.getMessage());
    }

    @Test
    void testSaveFileInvalidRetentionPeriodThrowsException() {
        // Arrange
        FileUploadRequest request = new FileUploadRequest(mockFile, -1);
        when(mockFile.getOriginalFilename()).thenReturn("file.txt");
        when(mockFile.isEmpty()).thenReturn(false);

        // Act & Assert
        InvalidRetentionPeriodException thrown = assertThrows(InvalidRetentionPeriodException.class, () -> fileService.saveFile(request));
        assertEquals("Retention period must be positive", thrown.getMessage());
    }
}