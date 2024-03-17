package blog.be.service.impl;

import blog.be.entity.FileDB;
import blog.be.repository.FileDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FileStorageDbService {
    @Autowired
    FileDBRepository fileDBRepository;

    public FileDB storeFiles(MultipartFile file) throws IOException {
        String name = StringUtils.cleanPath(file.getOriginalFilename());
        FileDB fileDB = new FileDB(name,file.getContentType(),file.getBytes());
        return fileDBRepository.save(fileDB);
    }
    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }
    public FileDB getFile(String id) {
        return fileDBRepository.findById(id).get();
    }

    public FileDB getFileDowLoad(String id) throws FileNotFoundException {
        return fileDBRepository.findById(id).orElseThrow(() -> new FileNotFoundException("File not found"));
    }
}
