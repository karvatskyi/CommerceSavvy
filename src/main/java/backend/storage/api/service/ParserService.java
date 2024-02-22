package backend.storage.api.service;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public interface ParserService<T> {
    List<T> parse(List<String> rows);
}
