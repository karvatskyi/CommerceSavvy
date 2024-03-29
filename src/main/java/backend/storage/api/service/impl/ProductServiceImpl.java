package backend.storage.api.service.impl;

import backend.storage.api.dto.ProductRequestDto;
import backend.storage.api.dto.ProductResponseDto;
import backend.storage.api.exception.EntityNotFoundException;
import backend.storage.api.mapper.ProductMapper;
import backend.storage.api.model.Product;
import backend.storage.api.repository.ProductRepository;
import backend.storage.api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Override
    public ProductResponseDto save(ProductRequestDto requestDto) {
        Product save = productRepository.save(productMapper.toEntityFromRequest(requestDto));
        return productMapper.toResponseFromEntity(save);
    }

    @Override
    public List<ProductResponseDto> findAll(Pageable pageable) {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponseFromEntity)
                .toList();
    }

    @Override
    public ProductResponseDto findById(Long id) {
        return productMapper.toResponseFromEntity(productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + id + " not found")));
    }

    @Override
    public ProductResponseDto updateProductById(Long id, ProductRequestDto requestDto) {
        Product currentProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Can't find product with id: " + id));
        currentProduct.setProductType(requestDto.getProductType());
        currentProduct.setName(requestDto.getName());
        currentProduct.setDescription(requestDto.getDescription());
        currentProduct.setWeight(requestDto.getWeight());
        currentProduct.setPlace(requestDto.getPlace());
        currentProduct.setPrice(requestDto.getPrice());
        return productMapper.toResponseFromEntity(productRepository.save(currentProduct));
    }

    @Override
    public void deleteById(Long id) {
        if (id == null || id <= 0 || productRepository.count() < id) {
            throw new RuntimeException("Can't find product with id: " + id);
        }
        productRepository.deleteById(id);
    }


    @Override
    public List<ProductResponseDto> findProductByName(String name) {
        if (name == null) {
            throw new RuntimeException("Name can't be null");
        }
        return productRepository.findAllByNameContainsIgnoreCase(name)
                .stream()
                .map(productMapper::toResponseFromEntity)
                .toList();
    }

    @Override
    public boolean updatePlace(Long productId, String newPlace) {
        if (newPlace.isEmpty()) {
            throw new RuntimeException("New place can't be empty");
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Can't find product by id: " + productId));
        product.setPlace(newPlace);
        productRepository.save(product);
        return true;
    }
}
