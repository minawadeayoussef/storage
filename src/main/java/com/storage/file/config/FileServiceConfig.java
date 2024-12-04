package com.storage.file.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
public class FileServiceConfig
{

    @Value("${file.save.path}")
    private String fileSavePath;

    @Value("${file.delete.cycle-time}")
    private long deleteCycleTime;
}
