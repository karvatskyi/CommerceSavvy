package backend.storage.api.dto;

import backend.storage.api.model.ProductType;
import lombok.Data;
import org.springframework.boot.SpringApplication;

@Data
public class ProductResponseDto {
    private Long id;
    private String name;
    private double price;
    private double weight;
    private ProductType productType;
    private String description;
    private String place;
    private boolean isDeleted;
}
