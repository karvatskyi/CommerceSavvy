package backend.storage.api.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface ParserService<T> {
    List<T> parse(List<String> rows);
}
