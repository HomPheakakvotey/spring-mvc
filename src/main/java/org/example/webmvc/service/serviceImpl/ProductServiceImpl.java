package org.example.webmvc.service.serviceImpl;


import lombok.RequiredArgsConstructor;
import org.example.webmvc.dto.ProductRequest;
import org.example.webmvc.dto.ProductResponse;
import org.example.webmvc.model.Product;
import org.example.webmvc.repository.ProductRepository;
import org.example.webmvc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private Product searchProductByID(int id){
        return  productRepository.getAllProducts()
                .stream().filter(p->p.getId()==id)
                .findFirst()
                .orElseThrow(()->new HttpClientErrorException(HttpStatus.NOT_FOUND,"Product doesn't exist!!"));
    }

    private ProductResponse mapProductToResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .imageUrl(product.getImageUrl())
                .title(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
    private Product mapRequestToProduct(ProductRequest request) {
        return Product.builder()
                .name(request.title())
                .price(request.price())
                .imageUrl(request.imageUrl())
                .description(request.description())
                .build();
    }

        @Override
        public List<ProductResponse> getAllProduct() {
            return  productRepository
                    .getAllProducts()
                    .stream()
                    .map(pro->{
                        return ProductResponse.builder()
                                .id(pro.getId())
                                .imageUrl(pro.getImageUrl())
                                .price(pro.getPrice())
                                .title(pro.getName())
                                .description(pro.getDescription())
                                .build();
                    }).toList();

        }
        @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product newProduct = Product.builder()
                .name(productRequest.title())
                .price(productRequest.price())
                .imageUrl(productRequest.imageUrl())
                .description(productRequest.description())
                .build();
        // increase the new product ID
        // find max of ID
        var maxID = productRepository.getAllProducts()
                .stream()
                .max(Comparator.comparingInt(Product::getId))
                .map(Product::getId);
        int newID=1;
        if(maxID.isPresent()) {
            newID = maxID.get() + 1;
        }
        newProduct.setId(newID);
        productRepository.addProduct(newProduct);

        return ProductResponse.builder()
                .id(newProduct.getId())
                .imageUrl(newProduct.getImageUrl())
                .title(newProduct.getName())
                .description(newProduct.getDescription())
                .price(newProduct.getPrice())
                .build();

    }

    @Override
    public void deleteProduct(int productId) {
        productRepository.deleteProduct(searchProductByID(productId).getId());
    }

    @Override
    public ProductResponse findProductByID(int id) {
        Product product  =  productRepository.getAllProducts()
                .stream().filter(p->p.getId()==id)
                .findFirst()
                .orElseThrow(()->new HttpClientErrorException(HttpStatus.NOT_FOUND,"Product doesn't exist!!"));
        return mapProductToResponse(product);
    }

    @Override
    public ProductResponse updateProduct(int id, ProductRequest productRequest) {
        // find if the product exist
        var result = searchProductByID(id);
        result= mapRequestToProduct(productRequest);
        result.setId(id);
        productRepository.updateProduct(result);
        return mapProductToResponse(result);
    }



}
