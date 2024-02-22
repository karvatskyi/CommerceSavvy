package backend.storage.api.mapper;

import backend.storage.api.config.MapperConfig;
import backend.storage.api.dto.ProductRequestDto;
import backend.storage.api.dto.ProductResponseDto;
import backend.storage.api.model.Employee;
import backend.storage.api.model.Product;
import backend.storage.api.repository.EmployeeRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface ProductMapper {
    Product toEntityFromRequest(ProductRequestDto requestDto);
    ProductResponseDto toResponseFromEntity(Product product);
}
