package backend.storage.api.controller;

import backend.storage.api.dto.ProductRequestDto;
import backend.storage.api.dto.ProductResponseDto;
import backend.storage.api.model.Product;
import backend.storage.api.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public List<ProductResponseDto> findAll(Pageable pageable) {
        return productService.findAll(pageable);
    }

    @PostMapping("/save")
    public ProductResponseDto save(@RequestBody @Valid ProductRequestDto product) {
        return productService.save(product);
    }

    @GetMapping("/id/{id}")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/update/{id}")
    public ProductResponseDto updateProductById(@PathVariable Long id, @RequestBody ProductRequestDto requestDto) {
        return productService.updateProductById(id, requestDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @GetMapping("/find{name}")
    public List<ProductResponseDto> findByName(@RequestParam String name) {
        return productService.findProductByName(name);
    }

    @PreAuthorize("hasAuthority('STOREKEEPER')")
    @PostMapping("/{productId}/update-place")
    public boolean updatePlace(@PathVariable Long productId, @RequestBody String newPlace) {
        return productService.updatePlace(productId, newPlace);
    }
}
