package com.example.ecommerce_backend.controller;

import com.example.ecommerce_backend.dto.request.ProductRequest;
import com.example.ecommerce_backend.dto.response.ApiResponse;
import com.example.ecommerce_backend.dto.response.ProductResponse;
import com.example.ecommerce_backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;

    @GetMapping("/getAll")
    ApiResponse<List<ProductResponse>> getAll(){
        ApiResponse<List<ProductResponse>> apiResponse = new ApiResponse<>();
        List<ProductResponse> products = productService.getAll();
        apiResponse.setResult(products);
        return apiResponse;
    }

    @PostMapping()
    ApiResponse<ProductResponse> create(@RequestBody @Valid ProductRequest itemRequest){
        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>();
        ProductResponse itemResponse = productService.create(itemRequest);
        apiResponse.setResult(itemResponse);
        return apiResponse;

    }
    @DeleteMapping(("{id}"))
    ApiResponse<Object> detele(@PathVariable("id") UUID id){
        ApiResponse< Object> apiResponse = new ApiResponse<>();
        productService.delete(id);
        apiResponse.setMessage("Item has been deleted!");
        return apiResponse;

    }

    @PutMapping("{id}")
    ApiResponse<ProductResponse> update(@PathVariable("id") UUID id, @RequestBody @Valid ProductRequest productRequest){
        ProductResponse product = productService.findById(id);
        ApiResponse<ProductResponse> apiResponse = new ApiResponse<>();
        ProductResponse productResponse = productService.update(id, productRequest);
        apiResponse.setResult(productResponse);
        return apiResponse;

    }
}
