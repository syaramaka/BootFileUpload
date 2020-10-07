package com.qualapps.pdfmerger.service;

import com.qualapps.pdfmerger.model.FileData;
import com.qualapps.pdfmerger.repository.FileDataRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

@Service
public class FileDataService {

    @Autowired
    FileDataRepository repository;

    public void save(final FileData fileData) {
        repository.save(fileData);
    }

    // Get all students from the h2 database.
    public List<FileData> getAll() {
        final List<FileData> files = new ArrayList<>();
        repository.findAll().forEach(fileData -> files.add(fileData));
        for (FileData file : files)
            System.out.println(file);
        return files;
    }

    public FileData findOne(UUID Id) {
        File file = new File("C:\\QualApps\\".concat(String.valueOf(Id)).concat(".pdf"));
        Optional<FileData> fileData = Optional.ofNullable(repository.findOne(Id));
        System.out.println(" file data for given id :" + fileData);
        byte[] file1 = fileData.get().getContent();
        try {
            OutputStream os = new FileOutputStream(file);
            os.write(file1);
            System.out.println("Successfully byte inserted");
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileData.get();
    }

    public FileData update(UUID Id, String[] pages) {
        Optional<FileData> fileData = Optional.ofNullable(repository.findOne(Id));
        byte[] file1 = fileData.get().getContent();
        List<FileData> filesData = null;
        FileData updatedFile = new FileData();
        try {
            PDDocument document = PDDocument.load(file1);
            PDDocument document1 = new PDDocument();
            for (int i = 0; i < pages.length; i++) {
                System.out.println("page:" + i);
                int pageIndex = Integer.parseInt(pages[i]);
                int updatedPageIndex = pageIndex - 1;
                System.out.println("original page:" + updatedPageIndex);
                document1.addPage(document.getPage(updatedPageIndex));
            }
            final File file = File.createTempFile(String.valueOf(Id), ".pdf");
            document1.save(file);
            System.out.println("name:" + file.getName());
            FileInputStream fin = new FileInputStream(file);
            byte fileContent[] = new byte[(int) file.length()];
            fin.read(fileContent);
            updatedFile.setFileId(Id);
            updatedFile.setFileName(fileData.get().getFileName());
            updatedFile.setFileSize(fileData.get().getFileSize());
            updatedFile.setFileType(fileData.get().getFileType());
            updatedFile.setUploadedDate(new Timestamp(System.currentTimeMillis()));
            updatedFile.setContent(fileContent);
            System.out.println("updated content:" + new String(fileContent));
            System.out.println("updated file data :" + updatedFile);
            repository.save(updatedFile);
            System.out.println(" updated file has been updated :" + updatedFile.getFileId());
            //filesData = getAll();
            System.out.println("filesData :" + filesData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return repository.save(updatedFile);
    }

}
