package blog.be.service;

import blog.be.dtos.ApiResponse;
import blog.be.exception.FileException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public interface FileService {
    String uploadFile(MultipartFile multipartFile) throws IOException , FileException.FileDownloadException;
    Object downloadFile(String fileName) throws IOException, FileException.FileDownloadException;
    String delete(String fileName);
    ApiResponse listFilesFromS3();
}

