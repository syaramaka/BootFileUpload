package com.qualapps.pdfmerger.controller;

import com.qualapps.pdfmerger.model.FileData;
import com.qualapps.pdfmerger.service.FileDataService;
import com.qualapps.pdfmerger.util.UploadModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class RestUploadController {

    private final Logger logger = LoggerFactory.getLogger(RestUploadController.class);
    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "C://Users//sunee//Downloads//test//";
    @Autowired
    FileDataService service;

    //Single file upload
    @PostMapping("/api/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile uploadfile) {
        logger.debug("Single file upload!");
        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        try {
            saveUploadedFiles(Arrays.asList(uploadfile));
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully uploaded - " +
                uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }

    // Multiple file upload
    @PostMapping("/api/upload/multi")
    public ResponseEntity<?> uploadFileMulti(
            @RequestParam("extraField") String extraField,
            @RequestParam("files") MultipartFile[] uploadfiles) {
        logger.debug("Multiple file upload!");
        String uploadedFileName = Arrays.stream(uploadfiles).map(x -> x.getOriginalFilename())
                .filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));
        if (StringUtils.isEmpty(uploadedFileName)) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }
        try {
            saveUploadedFiles(Arrays.asList(uploadfiles));
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Successfully uploaded - "
                + uploadedFileName, HttpStatus.OK);
    }

    //save file
    private void saveUploadedFiles(List<MultipartFile> files) throws IOException {

        FileData fileData = new FileData();
        for (MultipartFile file : files) {
            UUID uuid = UUID.randomUUID();
            System.out.println("uuid-----:" + uuid);
            System.out.println("file---:" + file.getOriginalFilename());
            if (file.isEmpty()) {
                continue; //next pls
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            fileData.setFileId(uuid);
            fileData.setFileName(file.getOriginalFilename());
            fileData.setFileSize(file.getSize());
            fileData.setFileType(file.getContentType());
            fileData.setUploadedDate(new Timestamp(System.currentTimeMillis()));
            fileData.setContent(file.getBytes());
            System.out.println("path --- :" + path);
            System.out.println(fileData);
            service.save(fileData);
            //Files.write(path, bytes); // save the file in locally
        }
    }

    // Get all documents from the h2 database.
    @RequestMapping(value = "/file/getAll", method = RequestMethod.GET)
    public List<FileData> getAll() {
        logger.debug("Getting all file details from the database.");
        return service.getAll();
    }

    //get particular document for given documentId
    @RequestMapping(value = "/file/{id}", method = RequestMethod.GET)
    public FileData getFileData(@PathVariable("id") UUID id) {
        System.out.println("get the file for id :" + id);
        logger.debug("getting particular file details from database");
        return service.findOne(id);
    }

    //update the updated document with given pages order
    @RequestMapping(value = "/file/{id}/pages/{pageId}", method = RequestMethod.PUT)
    public FileData updateFileData(@PathVariable("id") UUID id, @PathVariable("pageId") String pages[]) {
        System.out.println(pages);
        return service.update(id, pages);
    }
}
