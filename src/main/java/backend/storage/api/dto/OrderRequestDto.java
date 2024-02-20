package backend.storage.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class OrderRequestDto {
    @NotNull
    private Long sellerId;
    @NotNull
    private String buyer;
    @NotNull
    private String deliveryAddress;
    @NotNull
    private List<ItemRequestDto> items;
    private String notes;
}
