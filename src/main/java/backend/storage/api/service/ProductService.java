package backend.storage.api.service;

import backend.storage.api.dto.ProductRequestDto;
import backend.storage.api.dto.ProductResponseDto;
import backend.storage.api.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {
    ProductResponseDto save(ProductRequestDto product);
    List<ProductResponseDto> findAll();
    ProductResponseDto findById(Long id);
    ProductResponseDto updateProductById(Long id, ProductRequestDto requestDto);
    void deleteById(Long id);
    List<ProductResponseDto> findProductByName(String name);
    boolean updatePlace(Long productId, String newPlace);
}
