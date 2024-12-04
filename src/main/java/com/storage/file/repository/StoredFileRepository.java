package com.storage.file.repository;

import com.storage.file.model.StoredFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface StoredFileRepository extends JpaRepository<StoredFile, Long>
{

}
