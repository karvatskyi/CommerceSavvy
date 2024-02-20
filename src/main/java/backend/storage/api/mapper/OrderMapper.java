package backend.storage.api.mapper;

import backend.storage.api.config.MapperConfig;
import backend.storage.api.dto.OrderRequestDto;
import backend.storage.api.dto.OrderResponseDto;
import backend.storage.api.model.Order;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface OrderMapper {
    Order toEntityFromRequest(OrderRequestDto requestDto);
    OrderResponseDto toResponseFromEntity(Order order);
}
