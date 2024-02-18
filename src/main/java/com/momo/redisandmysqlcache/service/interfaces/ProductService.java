package com.momo.redisandmysqlcache.service.interfaces;

import com.momo.redisandmysqlcache.dto.ProductDto;
import com.momo.redisandmysqlcache.dto.UpdateProductDto;

import java.util.List;

public interface ProductService {
    void addProduct(ProductDto productDto);

    List<ProductDto> viewALlProducts();

    Boolean updateProduct(int prodId, UpdateProductDto updateProductDto);

    Boolean deleteProduct(int prodId);

    ProductDto findProductById(int prodId);

    ProductDto findProductByName(String prodName);
}
