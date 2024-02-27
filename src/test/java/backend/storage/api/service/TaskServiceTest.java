package backend.storage.api.service;

import backend.storage.api.dto.TaskRequestDto;
import backend.storage.api.dto.TaskResponseDto;
import backend.storage.api.mapper.EmployeeMapper;
import backend.storage.api.mapper.TaskMapper;
import backend.storage.api.model.Employee;
import backend.storage.api.model.Item;
import backend.storage.api.model.Task;
import backend.storage.api.repository.EmployeeRepository;
import backend.storage.api.repository.ItemRepository;
import backend.storage.api.repository.TaskRepository;
import backend.storage.api.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    public void getTaskById_nonExistingId_shouldThrowException() {
        Long nonExistingId = -10L;
        RuntimeException runtimeException = assertThrows(
                RuntimeException.class, () -> {
                    taskService.getTaskById(nonExistingId);
                }
        );
        String actual = runtimeException.getMessage();
        String expected = "Can't find task with id: " + nonExistingId;
        assertEquals(expected, actual);
    }

    @Test
    public void getTaskById_validInput_shouldReturnResponseDto() {
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(initTask()));
        when(taskMapper.toResponseFromEntity(initTask())).thenReturn(initTaskResponseDto());
        TaskResponseDto actual = taskService.getTaskById(anyLong());
        assertThat(actual).isEqualTo(initTaskResponseDto());
    }

    @Test
    public void createOrder_validInput_shouldReturnTaskResponseDto() {
        TaskRequestDto requestDto = initTaskRequestDto();
        Task task = initTask();
        TaskResponseDto expectedResponseDto = initTaskResponseDto();
        when(taskMapper.toEntityFromRequest(requestDto)).thenReturn(task);
        when(itemRepository.save(any(Item.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toResponseFromEntity(task)).thenReturn(expectedResponseDto);
        when(employeeRepository.findById(requestDto.getAuthorTaskId())).thenReturn(Optional.of(initEmployee()));

        TaskResponseDto actualResponseDto = taskService.createOrder(requestDto);

        assertThat(actualResponseDto).isEqualTo(expectedResponseDto);
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    public void createOrder_inValidAuthorId_shouldThrowsException() {
        TaskRequestDto requestDto = initTaskRequestDto();
        requestDto.setAuthorTaskId(-10L);
        Task task = initTask();
        when(taskMapper.toEntityFromRequest(requestDto)).thenReturn(task);
        when(employeeRepository.findById(requestDto.getAuthorTaskId())).thenReturn(Optional.empty()); // Мокуємо поведінку employeeRepository.findById для повернення пустого Optional

        assertThrows(RuntimeException.class, () -> taskService.createOrder(requestDto));
        verify(employeeRepository, times(1)).findById(requestDto.getAuthorTaskId()); // Перевіряємо, що метод findById був викликаний рівно один раз з переданим ідентифікатором користувача
    }
    private Employee initEmployee() {
        Employee employee = new Employee();
        employee.setName("bob");
        return employee;
    }
    private TaskRequestDto initTaskRequestDto() {
        Item item = new Item();
        item.setQuantity(10);
        TaskRequestDto requestDto = new TaskRequestDto();
        requestDto.setItems(Set.of(item));
        return requestDto;
    }
    private Task initTask() {
        Task task = new Task();
        task.setCreationTime(LocalDateTime.of(2024, 01,01,01,01));
        task.setSizeTask(10);
        return task;
    }

    private TaskResponseDto initTaskResponseDto() {
        TaskResponseDto responseDto = new TaskResponseDto();
        responseDto.setCreationTime(LocalDateTime.of(2024, 01,01,01,01));
        responseDto.setSizeTask(10);
        return responseDto;
    }
}
