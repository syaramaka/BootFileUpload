package com.mkyong.repository;

import com.mkyong.model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDataRepository extends JpaRepository<FileData, String> {

   // FileData findByFileId(String fileId);

    //Product findByName(String name);
}
