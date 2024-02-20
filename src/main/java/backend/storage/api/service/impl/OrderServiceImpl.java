package backend.storage.api.service.impl;

import backend.storage.api.dto.ItemRequestDto;
import backend.storage.api.dto.OrderRequestDto;
import backend.storage.api.dto.OrderResponseDto;
import backend.storage.api.mapper.OrderMapper;
import backend.storage.api.model.Item;
import backend.storage.api.model.Order;
import backend.storage.api.model.Product;
import backend.storage.api.repository.EmployeeRepository;
import backend.storage.api.repository.ItemRepository;
import backend.storage.api.repository.OrderRepository;
import backend.storage.api.repository.ProductRepository;
import backend.storage.api.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final ProductRepository productRepository;

    private final EmployeeRepository employeeRepository;

    private final ItemRepository itemRepository;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
        Order order = new Order();
        order.setLocalDateTime(LocalDateTime.now());
        order.setSeller(employeeRepository.findById(requestDto.getSellerId()).orElseThrow(RuntimeException::new));
        order.setItems(parseDtoListToEntityList(requestDto.getItems()));
        order.setBuyer(requestDto.getBuyer());
        order.setDeliveryAddress(requestDto.getDeliveryAddress());
        order.setNotes(requestDto.getNotes());
        return orderMapper.toResponseFromEntity(orderRepository.save(order));
    }

    @Override
    public OrderResponseDto getOrderById(Long id) {
        return orderMapper.toResponseFromEntity(
                orderRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    private List<Item> parseDtoListToEntityList(List<ItemRequestDto> requestDtos) {
        List<Item> result = new LinkedList<>();
        for (ItemRequestDto itemDto : requestDtos) {
            Item item = new Item();
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Can't create item with this product, because it not exist"));
            item.setProduct(product);
            item.setQuantity(itemDto.getQuantity());
            itemRepository.save(item);
            result.add(item);
        }
        return result;
    }
}
