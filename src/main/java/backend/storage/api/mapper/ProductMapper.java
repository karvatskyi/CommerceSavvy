package backend.storage.api.mapper;

import backend.storage.api.config.MapperConfig;
import backend.storage.api.dto.ProductRequestDto;
import backend.storage.api.dto.ProductResponseDto;
import backend.storage.api.model.Product;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface ProductMapper {
    Product toEntityFromRequest(ProductRequestDto requestDto);
    ProductResponseDto toResponseFromEntity(Product product);
}
