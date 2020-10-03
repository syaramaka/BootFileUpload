package com.mkyong.service;

import com.mkyong.model.FileData;
import com.mkyong.repository.FileDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FileDataService {

    // @Autowired annotation provides the automatic dependency injection.
    @Autowired
    FileDataRepository repository;

    // Save student entity in the h2 database.
    public void save(final FileData fileData) {
        repository.save(fileData);
    }

    // Get all students from the h2 database.
    public List<FileData> getAll() {
        final List<FileData> files = new ArrayList<>();
        repository.findAll().forEach(student -> files.add(student));
        return files;
    }

    public FileData findOne(String Id)
    {
        Optional<FileData> fileData = Optional.ofNullable(repository.findOne(Id));
        return fileData.get();
    }

}
