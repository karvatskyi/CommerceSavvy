package backend.storage.api.service.impl;

import backend.storage.api.dto.ProductRequestDto;
import backend.storage.api.dto.ProductResponseDto;
import backend.storage.api.exception.EntityNotFoundException;
import backend.storage.api.mapper.ProductMapper;
import backend.storage.api.model.Product;
import backend.storage.api.repository.ProductRepository;
import backend.storage.api.repository.ProductTypeRepository;
import backend.storage.api.service.ProductService;
import lombok.RequiredArgsConstructor;
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
    public List<ProductResponseDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponseFromEntity)
                .toList();
    }

    @Override
    public ProductResponseDto findById(Long id) {
        return productMapper.toResponseFromEntity(productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found")));
    }

    @Override
    public ProductResponseDto updateProductById(Long id, ProductRequestDto requestDto) {
        Product currentProduct = productRepository.getReferenceById(id);
        // .orElseThrow(() -> new EntityNotFoundException("Can't get product by id: " + id));
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
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponseDto> findProductByName(String name) {
        return productRepository.findAllByNameContainsIgnoreCase(name)
                .stream()
                .map(productMapper::toResponseFromEntity)
                .toList();
    }
}
