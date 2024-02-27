package backend.storage.api.dto;

import backend.storage.api.model.Employee;
import backend.storage.api.model.Item;
import backend.storage.api.model.Status;
import backend.storage.api.model.TaskType;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class TaskResponseDto {
    private int sizeTask;
    private EmployeeResponseDto authorTask;
    private EmployeeResponseDto performerTask;
    private Status status;
    private TaskType taskType;
    private Set<Item> itemList;
    private LocalDateTime creationTime;
    private String buyer;
    private String deliveryAddress;
    private String notes;
}
