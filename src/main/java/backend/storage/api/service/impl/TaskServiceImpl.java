package backend.storage.api.service.impl;

import backend.storage.api.dto.ItemRequestDto;
import backend.storage.api.dto.TaskRequestDto;
import backend.storage.api.dto.TaskResponseDto;
import backend.storage.api.mapper.EmployeeMapper;
import backend.storage.api.mapper.TaskMapper;
import backend.storage.api.model.Item;
import backend.storage.api.model.Task;
import backend.storage.api.repository.EmployeeRepository;
import backend.storage.api.repository.ItemRepository;
import backend.storage.api.repository.TaskRepository;
import backend.storage.api.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private static final int MAX_QUANTITY = 30;
    private final static int FIRST_ELEMENT = 0;
    private static final int MAX_SIZE_TASK = 200;

    private final TaskMapper taskMapper;

    private final TaskRepository taskRepository;

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    private final ItemRepository itemRepository;

    @Override
    public List<Task> createTasks(List<Item> input) {
        List<Task> tasks = new ArrayList<>();
        List<Item> sortedInput = split(input);
        while (!sortedInput.isEmpty()) {
            Task task = new Task();
            TreeSet<Item> items = new TreeSet<>();
            while (task.getSizeTask() <= 100
                    && items.size() < 10
                    && !sortedInput.isEmpty()
                    && !items.contains(sortedInput.get(FIRST_ELEMENT))) {
                items.add(sortedInput.get(FIRST_ELEMENT));
                task.setSizeTask(sortedInput.get(FIRST_ELEMENT).getQuantity());
                sortedInput.remove(FIRST_ELEMENT);
            }
            task.setItemList(items);
            tasks.add(task);
        }
        return tasks;
    }

    @Override
    public TaskResponseDto createOrder(TaskRequestDto requestDto) {
        Task task = initEntityFromRequest(requestDto);
        taskRepository.save(task);
        return initResponseFromEntity(requestDto, task);
    }

    @Override
    public TaskResponseDto getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task doesn't exist"));
        TaskResponseDto response = taskMapper.toResponseFromEntity(task);
        response.setAuthorTask(employeeMapper.toResponseFromEntity(task.getAuthorTaskId()));
        return response;
    }

    private List<Item> split(List<Item> items) {
        List<Item> result = new LinkedList<>();
        for (int i = 0; i < items.size(); i++) {
            Item current = items.get(i);
            if (current.getQuantity() > 30) {
                List<Item> currentList = new LinkedList<>();
                while (current.getQuantity() >= 30) {
                    Item item = new Item();
                    item.setQuantity(MAX_QUANTITY);
                    item.setProduct(current.getProduct());
                    currentList.add(item);
                    current.setQuantity(current.getQuantity() - MAX_QUANTITY);
                }
                currentList.add(current);
                result.addAll(currentList);
            } else {
                result.add(current);
            }
        }
        return result;
    }

    private Task initEntityFromRequest(TaskRequestDto requestDto) {
        Task task = taskMapper.toEntityFromRequest(requestDto);
        task.setCreationTime(LocalDateTime.now());
        Set<Item> collect = requestDto.getItems().stream().map(itemRepository::save).collect(Collectors.toSet());
        task.setItemList(collect);
        task.setSizeTask(calculateSizeTask(requestDto.getItems()));
        return task;
    }

    private TaskResponseDto initResponseFromEntity(TaskRequestDto requestDto, Task task) {
        TaskResponseDto response = taskMapper.toResponseFromEntity(task);
        response.setAuthorTask(employeeMapper.toResponseFromEntity(employeeRepository
                .findById(requestDto.getAuthorTaskId())
                .orElseThrow(() -> new RuntimeException("Employee with id: " + requestDto
                        .getAuthorTaskId() + " doesn't exist"))));
        return response;
    }

    private int calculateSizeTask(Set<Item> items) {
        return items.stream()
                .mapToInt(Item::getQuantity)
                .sum();
    }
}
