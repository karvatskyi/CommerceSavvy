package backend.storage.api.service;

import backend.storage.api.dto.ProductRequestDto;
import backend.storage.api.dto.ProductResponseDto;
import backend.storage.api.mapper.ProductMapper;
import backend.storage.api.model.Product;
import backend.storage.api.model.ProductType;
import backend.storage.api.repository.ProductRepository;
import backend.storage.api.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void save_validRequestDto_returnResponseDto() {
        Product product = initProduct();
        ProductResponseDto responseDto = initProductResponseDto();
        ProductRequestDto requestDto = initProductRequestDto();
        when(productMapper.toEntityFromRequest(requestDto)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toResponseFromEntity(product)).thenReturn(responseDto);

        ProductResponseDto actual = productService.save(requestDto);

        assertThat(actual).isEqualTo(responseDto);
    }

    @Test
    public void findById_validId_returnResponseDto() {
        Product product = initProduct();
        ProductResponseDto responseDto = initProductResponseDto();
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productMapper.toResponseFromEntity(product)).thenReturn(responseDto);

        ProductResponseDto actual = productService.findById(anyLong());

        assertThat(actual).isEqualTo(responseDto);
    }

    @Test
    public void findById_notExistingId_shouldThrowsException() {
        Long nonExistingId = -10L;
        RuntimeException runtimeException = assertThrows(
                RuntimeException.class, () -> {
                    productService.findById(nonExistingId);
                }
        );
        String actual = runtimeException.getMessage();
        String expected = "Product with id: " + nonExistingId + " not found";
        assertEquals(expected, actual);
    }

    @Test
    public void updateProductById_nonExistingId_ShouldThrowException() {
        Long nonExistingId = -10L;
        RuntimeException runtimeException = assertThrows(
                RuntimeException.class, () -> {
                    productService.updateProductById(nonExistingId, initProductRequestDto());
                }
        );
        String actual = runtimeException.getMessage();
        String expected = "Can't find product with id: " + nonExistingId;
        assertEquals(expected, actual);
    }

    @Test
    public void updateProductById_validRequestDto_shouldReturnResponseDto() {
        Product product = initProduct();
        ProductRequestDto requestDto = initProductRequestDto();
        ProductResponseDto responseDto = initProductResponseDto();

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toResponseFromEntity(product)).thenReturn(responseDto);

        ProductResponseDto actual = productService.updateProductById(anyLong(), requestDto);
        assertThat(actual).isEqualTo(responseDto);
    }

    @Test
    public void deleteById_nonExistingId_shouldThrowException() {
        Long nonExistingId = -10L;
        RuntimeException runtimeException = assertThrows(
                RuntimeException.class, () -> {
                    productService.deleteById(nonExistingId);
                }
        );

        String actual = runtimeException.getMessage();
        String expected = "Can't find product with id: " + nonExistingId;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void deleteById_validId_callsDeleteByIdOnRepository() {
        Long validId = 1L;
        when(productRepository.count()).thenReturn(10L);
        productService.deleteById(validId);
        verify(productRepository, times(1)).deleteById(validId);
    }

    @Test
    public void deleteById_nullInput_shouldThrowException() {
        RuntimeException runtimeException = assertThrows(
                RuntimeException.class, () -> {
                    productService.deleteById(null);
                }
        );

        String actual = runtimeException.getMessage();
        String expected = "Can't find product with id: " + null;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void findByProductName_emptyInput_shouldReturnEmptyList() {
        String name = "";
        when(productRepository.findAllByNameContainsIgnoreCase(name)).thenReturn(new ArrayList<>());
        List<ProductResponseDto> actual = productService.findProductByName(name);
        List<ProductResponseDto> expected = new ArrayList<>();
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    public void findProductByName_validInput_shouldReturnListOfResponseDto() {
        String name = "o";
        Product product = initProduct();
        ProductResponseDto responseDto = initProductResponseDto();
        List<Product> productList = List.of(product);
        when(productRepository.findAllByNameContainsIgnoreCase(name)).thenReturn(productList);
        when(productMapper.toResponseFromEntity(product)).thenReturn(responseDto);
        List<ProductResponseDto> actual = productService.findProductByName(name);
        assertThat(List.of(responseDto)).isEqualTo(actual);
    }

    @Test
    public void findProductByName_nullInput_shouldThrowException() {
        RuntimeException runtimeException = assertThrows(
                RuntimeException.class, () -> {
                    productService.findProductByName(null);
                }
        );

        String actual = runtimeException.getMessage();
        String expected = "Name can't be null";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void updatePlace_nonExistingId_ShouldThrowException() {
        Long nonExistingId = -10L;
        RuntimeException runtimeException = assertThrows(
                RuntimeException.class, () -> {
                    productService.updatePlace(nonExistingId, "123");
                }
        );
        String actual = runtimeException.getMessage();
        String expected = "Can't find product by id: " + nonExistingId;
        assertEquals(expected, actual);
    }

    @Test
    public void updatePlace_emptyNewPlace_shouldThrowException() {
        RuntimeException runtimeException = assertThrows(
                RuntimeException.class, () -> {
                    productService.updatePlace(1L, "");
                }
        );
        String actual = runtimeException.getMessage();
        String expected = "New place can't be empty";
        assertEquals(expected, actual);
    }

    @Test
    public void updatePlace_validInput_shouldReturnTrue() {
        Long validId = 1L;
        String newPlace = "01-01-10";
        Product product = initProduct();
        Product updatedProduct = initProduct();
        updatedProduct.setPlace(newPlace);
        when(productRepository.findById(validId)).thenReturn(Optional.of(product));
        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);
        boolean actual = productService.updatePlace(validId, newPlace);
        boolean expected = true;
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    public void findAdd_validPageable_shouldReturnListOfProducts() {
        Product product = initProduct();
        ProductResponseDto responseDto = initProductResponseDto();
        Pageable pageable = PageRequest.of(0, 10);
        List<Product> productList = List.of(product);
        Page<Product> productPage = new PageImpl<>(productList, pageable, productList.size());
        when(productRepository.findAll()).thenReturn(productList);
        when(productMapper.toResponseFromEntity(product)).thenReturn(responseDto);
        List<ProductResponseDto> actual = productService.findAll(pageable);
        assertThat(actual).hasSize(1);
        assertThat(actual.get(0)).isEqualTo(responseDto);
    }

    private ProductRequestDto initProductRequestDto() {
        ProductRequestDto requestDto = new ProductRequestDto();
        requestDto.setName("name dto");
        requestDto.setPrice(122.00);
        requestDto.setPlace("01-02-03");
        requestDto.setProductType(ProductType.getByCode(1));
        requestDto.setWeight(12.00);
        requestDto.setDescription("desc dto");
        requestDto.setDeleted(false);
        return requestDto;
    }

    private ProductResponseDto initProductResponseDto() {
        ProductResponseDto responseDto = new ProductResponseDto();
        responseDto.setName("name dto");
        responseDto.setPrice(122.00);
        responseDto.setPlace("01-02-03");
        responseDto.setProductType(ProductType.getByCode(1));
        responseDto.setWeight(12.00);
        responseDto.setDescription("desc dto");
        responseDto.setDeleted(false);
        return responseDto;
    }

    private Product initProduct() {
        Product product = new Product();
        product.setName("name dto");
        product.setPrice(122.00);
        product.setPlace("01-02-03");
        product.setProductType(ProductType.getByCode(1));
        product.setWeight(12.00);
        product.setDescription("desc dto");
        product.setDelete(false);
        return product;
    }
}
