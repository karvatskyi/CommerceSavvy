package backend.storage.api.dto;

import lombok.Data;

@Data
public class ItemRequestDto {
    private Long productId;
    private int quantity;
}
