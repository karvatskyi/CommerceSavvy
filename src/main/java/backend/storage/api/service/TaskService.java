package backend.storage.api.service;

import backend.storage.api.model.Item;
import backend.storage.api.model.Task;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface TaskService {
    List<Task> createTasks(List<Item> items);
}
