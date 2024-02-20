package backend.storage.api.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface ReaderService<T> {
    List<T> readFromFile(MultipartFile file);
}
