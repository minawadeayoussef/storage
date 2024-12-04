package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadRequest
{
    private MultipartFile file;
    private long retentionPeriod;
}