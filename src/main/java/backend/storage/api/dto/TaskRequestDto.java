package backend.storage.api.dto;

import backend.storage.api.model.Item;
import backend.storage.api.model.Status;
import backend.storage.api.model.TaskType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;
import java.util.Set;

@Data
public class TaskRequestDto {
    @NotNull
    private Long authorTaskId;
    @NotNull
    private Status status;
    @NotNull
    private TaskType taskType;
    @NotNull
    private Set<Item> items;
    @NotNull
    private String buyer;
    @NotNull
    private String deliveryAddress;
    private String notes;
}
