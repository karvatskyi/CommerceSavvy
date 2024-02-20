package backend.storage.api.mapper;

import backend.storage.api.config.MapperConfig;
import backend.storage.api.dto.EmployeeRequestDto;
import backend.storage.api.dto.EmployeeResponseDto;
import backend.storage.api.model.Employee;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface EmployeeMapper {
    Employee toEntityFromRequest(EmployeeRequestDto requestDto);
    EmployeeResponseDto toResponseFromEntity(Employee employee);
}
