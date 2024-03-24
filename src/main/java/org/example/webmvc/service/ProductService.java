package org.example.webmvc.service;

import org.example.webmvc.dto.ProductRequest;
import org.example.webmvc.dto.ProductResponse;
import org.example.webmvc.model.Product;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAllProduct();

    ProductResponse createProduct(ProductRequest productRequest);

    void deleteProduct(int productId);

    ProductResponse findProductByID(int id);
    ProductResponse updateProduct(int id ,  ProductRequest productRequest);



}
