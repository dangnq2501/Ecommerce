package com.example.ecommerce_backend.service;

import com.example.ecommerce_backend.dto.request.ProductRequest;
import com.example.ecommerce_backend.dto.response.ProductResponse;
import com.example.ecommerce_backend.exception.AppException;
import com.example.ecommerce_backend.mapper.ProductMapper;
import com.example.ecommerce_backend.models.Product;
import com.example.ecommerce_backend.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;

    public List<ProductResponse> getAll(){
        return productRepository.findAll().stream().map(productMapper::toProductResponse).toList();
    }

    public ProductResponse findById(String id){
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException("Product not existed"));
        return productMapper.toProductResponse(product);
    }
    private String generateProductId(String name, String category) {

        return cleanString(name) + "_" + cleanString(category);
    }

    private String cleanString(String input) {
        return (input == null ? "" : input.replaceAll("\\s+", "_"));
    }
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse create(ProductRequest productRequest){
        Product product = productMapper.toProduct(productRequest);
        System.out.println(product.getImageFile());
        System.out.println(product.getCategory());
        String id = generateProductId(product.getCategory(), product.getName());
        if(productRepository.existsById(id)){
            throw new AppException("Product already existed");
        }
        product.setId(id);
        productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String id){
        productRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse update(String id, ProductRequest productRequest){
        System.out.println(id);
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException("Item not existed"));
        productMapper.updateItem(product, productRequest);
        productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

}
