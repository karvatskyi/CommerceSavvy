package backend.storage.api.dto;

import backend.storage.api.model.Status;
import backend.storage.api.model.TaskType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class TaskRequestDto {
    @NotNull
    private Long authorTaskId;
    @NotNull
    private Status status;
    @NotNull
    private TaskType taskType;
    @NotNull
    private List<ItemRequestDto> items;
    @NotNull
    private String buyer;
    @NotNull
    private String deliveryAddress;
    private String notes;
}
