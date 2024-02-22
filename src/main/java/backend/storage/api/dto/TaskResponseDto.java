package backend.storage.api.dto;

import backend.storage.api.model.Employee;
import backend.storage.api.model.Item;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskResponseDto {
    private List<Item> items;
    private LocalDateTime creationTime;
    private EmployeeResponseDto authorTaskId;
    private String deliveryAddress;
    private String buyer;
    private String description;
}
