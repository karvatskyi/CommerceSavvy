package backend.storage.api.controller;

import backend.storage.api.dto.TaskRequestDto;
import backend.storage.api.dto.TaskResponseDto;
import backend.storage.api.mapper.TaskMapper;
import backend.storage.api.model.Item;
import backend.storage.api.model.Task;
import backend.storage.api.repository.TaskRepository;
import backend.storage.api.service.ParserService;
import backend.storage.api.service.ReaderService;
import backend.storage.api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    private final ReaderService<String> readerService;

    private final ParserService<Item> parserService;

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    @PostMapping("/file")
    public List<Task> orderingProducts(@RequestPart("file") MultipartFile file) {
        List<Item> parsedList = parserService.parse(readerService.readFromFile(file));
        return taskService.createTasks(parsedList);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/create/order")
    public TaskResponseDto createOrder(@RequestBody TaskRequestDto requestDto) {
        return taskService.createOrder(requestDto);
    }

    @GetMapping("/get{id}")
    public TaskResponseDto getById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }
}
