package blog.be.service.impl;

import blog.be.dtos.ApiResponse;
import blog.be.dtos.ResponseFileS3;
import blog.be.exception.FileException;
import blog.be.service.FileService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    @Value("${aws.bucket.name}")
    private String bucketName;

    private final AmazonS3 s3Client;

    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        // convert multipart file  to a file
        File file = new File(multipartFile.getOriginalFilename());
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)){
            fileOutputStream.write(multipartFile.getBytes());
        }

        // generate file name
        String fileName = generateFileName(multipartFile);

        // upload file
        PutObjectRequest request = new PutObjectRequest(bucketName, fileName, file);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType("plain/"+ FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
        metadata.addUserMetadata("Title", "File Upload - " + fileName);
        metadata.setContentLength(file.length());
        request.setMetadata(metadata);
        s3Client.putObject(request);

        // delete file
        file.delete();

        return fileName;
    }

    @Override
    public Object downloadFile(String fileName) throws IOException, FileException.FileDownloadException {
        if (bucketIsEmpty()) {
            throw new FileException.FileDownloadException("Requested bucket does not exist or is empty");
        }
        S3Object object = s3Client.getObject(bucketName, fileName);
        try (S3ObjectInputStream s3is = object.getObjectContent()) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
                byte[] read_buf = new byte[1024];
                int read_len = 0;
                while ((read_len = s3is.read(read_buf)) > 0) {
                    fileOutputStream.write(read_buf, 0, read_len);
                }
            }
            Path pathObject = Paths.get(fileName);
            Resource resource = new UrlResource(pathObject.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileException.FileDownloadException("Could not find the file!");
            }
        }
    }

    @Override
    public String delete(String fileName) {
        if(fileName.isEmpty()){
            return "Could not find this file from s3";
        }
        s3Client.deleteObject(bucketName,fileName);
        return "Delete file" + fileName;
    }

    @Override
    public ApiResponse listFilesFromS3() {
//        ListObjectsRequest listObjectsRequest =
//                new ListObjectsRequest()
//                        .withBucketName(bucketName);
        ListObjectsV2Result result = s3Client.listObjectsV2(this.bucketName);
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        if(objects == null){
            return new ApiResponse(false, "Don't have any files from bucket S3", 0, null);
        }
//      objects = s3Client.listNextBatchOfObjects(result);
        return new ApiResponse(true, "Files have successfully", 1, new ResponseFileS3(objects));
    }

    private boolean bucketIsEmpty() {
        ListObjectsV2Result result = s3Client.listObjectsV2(this.bucketName);
        if (result == null){
            return false;
        }
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        return objects.isEmpty();
    }
    private boolean fileIsExist(String bucketName, String key){
        return true;
    }
    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

}
