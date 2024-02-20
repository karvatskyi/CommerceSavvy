package backend.storage.api.dto;

import backend.storage.api.model.ProductType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequestDto {
    @NotNull
    private String name;
    @NotNull
    private double price;
    @NotNull
    private double weight;
    @NotNull
    private ProductType productType;
    private String description;
    @NotNull
    private String place;
    @NotNull
    private boolean isDeleted;
}
