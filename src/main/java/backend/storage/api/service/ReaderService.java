package backend.storage.api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public interface ReaderService<T> {
    List<T> readFromFile(MultipartFile file);
}
