package backend.storage.api.controller;

import backend.storage.api.dto.OrderRequestDto;
import backend.storage.api.dto.OrderResponseDto;
import backend.storage.api.mapper.OrderMapper;
import backend.storage.api.model.Item;
import backend.storage.api.model.Order;
import backend.storage.api.model.Task;
import backend.storage.api.service.OrderService;
import backend.storage.api.service.ParserService;
import backend.storage.api.service.ReaderService;
import backend.storage.api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/orders")
@RequiredArgsConstructor
public class OrderController {
    private final TaskService taskService;

    private final ReaderService<String> readerService;

    private final ParserService<Item> parserService;

    private final OrderService orderService;

    @PostMapping("/file")
    public List<Task> orderingProducts(@RequestPart("file") MultipartFile file) {
        List<Item> parsedList = parserService.parse(readerService.readFromFile(file));
        return taskService.createTasks(parsedList);
    }

    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/create")
    public OrderResponseDto createOrder(@RequestBody OrderRequestDto requestDto) {
        return orderService.createOrder(requestDto);
    }

    @GetMapping("/get{id}")
    public OrderResponseDto getById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }
}
