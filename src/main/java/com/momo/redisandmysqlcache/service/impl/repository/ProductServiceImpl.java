package com.momo.redisandmysqlcache.service.impl.repository;

import com.momo.redisandmysqlcache.dto.ProductDto;
import com.momo.redisandmysqlcache.dto.UpdateProductDto;
import com.momo.redisandmysqlcache.model.Product;
import com.momo.redisandmysqlcache.repository.ProductRepository;
import com.momo.redisandmysqlcache.service.interfaces.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@EnableCaching
@CacheConfig(cacheNames = "demoProducts")
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public void addProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto,Product.class);
        productRepository.save(product);
    }

    @Override
    public List<ProductDto> viewALlProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();
        for( Product prod : productList){
            productDtoList.add(modelMapper.map(prod,ProductDto.class));
        }
        return productDtoList;
    }

    @Override
    @Transactional
    @CachePut(key = "#prodId",value ="demoProducts")
    public Boolean updateProduct(int prodId, UpdateProductDto updateProductDto) {
        Optional<Product> optProduct = productRepository.findById(prodId);
        if(optProduct.isPresent()){
            Product product = optProduct.get();

            product.setProdName(updateProductDto.getProdName());
            product.setQuantity(updateProductDto.getQuantity());
            product.setPrice(updateProductDto.getPrice());

            productRepository.save(product);

            return true;
        }

        return false;
    }

    @Override
    @Transactional
    @CacheEvict(key = "#prodId",value = "demoProducts")
    public Boolean deleteProduct(int prodId) {
        Optional<Product> optProduct = productRepository.findById(prodId);
        if (optProduct.isPresent()) {
            Product product = optProduct.get();
            productRepository.delete(product);
            return true;
        }

        return false;

    }

    @Override
    @Cacheable(key = "#prodId",value = "demoProducts")
    public ProductDto findProductById(int prodId) {

        System.out.println("Product fetched from Db!");

        Optional<Product> optionalProduct = productRepository.findById(prodId);
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
             return modelMapper.map(product,ProductDto.class);
        }
        return null;
    }


//       In the @Cacheable annotation,
//       the key specified in the key attribute ("#prodName")
//       should match the parameter name of the method (String prodNam).
//       This is how Spring determines the key for caching.
//       The key = "#prodName" indicates that the cache key should be derived
//       from the method parameter named prodNam.
//       Therefore, the parameter name in the method signature
//       should match the name used in the key attribute.
    @Override
    @Cacheable(key = "#prodName",value = "demoProducts")
    public ProductDto findProductByName(String prodName) {

        System.out.println("Product fetched from Db!");

        Optional<Product> optionalProduct = productRepository.findByProdName(prodName);
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            return modelMapper.map(product,ProductDto.class);
        }
        return null;
    }
}
