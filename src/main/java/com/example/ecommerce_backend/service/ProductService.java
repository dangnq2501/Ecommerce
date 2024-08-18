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
import java.util.UUID;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;

    public List<ProductResponse> getAll(){
        return productRepository.findAll().stream().map(productMapper::toProductResponse).toList();
    }

    public ProductResponse findById(UUID id){
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
        productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse delete(UUID id){
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException("Product not existed"));
        if(product.getStockQuantity() > 0){
            product.setStockQuantity(product.getStockQuantity()-1);
        }
        else{
            throw  new AppException(("Product is out of stock"));
        }
        return productMapper.toProductResponse(productRepository.save(product));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse update(UUID id, ProductRequest productRequest){
        System.out.println(id);
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException("Product not existed"));
        productMapper.updateItem(product, productRequest);
        productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

}
