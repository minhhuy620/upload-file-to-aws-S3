package be.upload_s3.service;

import be.upload_s3.dtos.ApiResponse;
import be.upload_s3.exception.FileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String uploadFile(MultipartFile multipartFile, int userID) throws IOException , FileException.FileDownloadException;
    Object downloadFile(String fileName) throws IOException, FileException.FileDownloadException;
    String delete(String fileName);
    ApiResponse listFilesFromS3(int userID);
}

