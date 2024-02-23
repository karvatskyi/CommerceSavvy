package backend.storage.api.mapper;

import backend.storage.api.config.MapperConfig;
import backend.storage.api.dto.TaskRequestDto;
import backend.storage.api.dto.TaskResponseDto;
import backend.storage.api.model.Employee;
import backend.storage.api.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface TaskMapper {
    @Mapping(target = "authorTaskId", source = "authorTaskId", qualifiedByName = "mapToEmployee")
    Task toEntityFromRequest(TaskRequestDto requestDto);

    @Mapping(target = "authorTask", ignore = true)
    TaskResponseDto toResponseFromEntity(Task task);

    @Named("mapToEmployee")
    default Employee mapToEmployee(Long authorTaskId) {
        Employee employee = new Employee();
        employee.setId(authorTaskId);
        return employee;
    }
}
