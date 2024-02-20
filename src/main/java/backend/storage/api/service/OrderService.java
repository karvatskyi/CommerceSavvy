package backend.storage.api.service;

import backend.storage.api.dto.OrderRequestDto;
import backend.storage.api.dto.OrderResponseDto;
import backend.storage.api.model.Order;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto requestDto);
    OrderResponseDto getOrderById(Long id);
}
