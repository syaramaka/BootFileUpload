package com.qualapps.pdfmerger.model;
import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "FILEDATA")
public class FileData {

    @Id
    @GeneratedValue
    private UUID fileId;
    private String fileType;
    private Timestamp uploadedDate;
    private long fileSize;
    private String fileName;
    @Lob
    private byte[] content;

    @Column(name = "FILE")
    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
    @Column(name = "FILENAME")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    @Column(name = "FILEID")
    public UUID getFileId() {
        return fileId;
    }

    public void setFileId(UUID fileId) {
        this.fileId = fileId;
    }
    @Column(name = "FILETYPE")
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    @Column(name = "UPLOADEDDATE")
    public Timestamp getUploadedDate() {
        return uploadedDate;
    }
    public void setUploadedDate(Timestamp uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    @Column(name = "FILESIZE")
    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "FileData{" +
                "fileId=" + fileId +
                ", fileType='" + fileType + '\'' +
                ", uploadedDate=" + uploadedDate +
                ", fileSize=" + fileSize +
                ", fileName='" + fileName + '\'' +
                ", content=" + content +
                '}';
    }
}
