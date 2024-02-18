package com.momo.redisandmysqlcache.controller;

import com.momo.redisandmysqlcache.dto.ProductDto;
import com.momo.redisandmysqlcache.dto.UpdateProductDto;
import com.momo.redisandmysqlcache.service.interfaces.ProductService;
import com.momo.redisandmysqlcache.service.interfaces.RateLimiterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/shop")
@Slf4j
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    RateLimiterService rateLimiterService;

    @PostMapping("/add-product")
    public ResponseEntity<String> saveProduct(@RequestBody ProductDto productDto){
        productService.addProduct(productDto);
        return ResponseEntity.ok("Product Added Successfully.");
    }

        @GetMapping("/view-product-list")
    public ResponseEntity<List<ProductDto>> viewProductList(){
        return ResponseEntity.ok(productService.viewALlProducts());
    }

    @GetMapping("/view-product-by-id/{prodId}")
    public ResponseEntity<?> productDetailsById(@PathVariable("prodId") int prodId){
        if(rateLimiterService.allowRequest(prodId,5,60)){
            ProductDto productDto = productService.findProductById(prodId);
            if (Objects.nonNull(productDto)) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(productDto);
            }
            log.info("Request is allowed");
        }
            log.info("Request not allowed to process. Max request number reached.");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Product id not found");

    }

    @GetMapping("/view-product-by-name/{prodName}")
    public ResponseEntity<?> productDetailsByName(@PathVariable("prodName") String prodName){
        ProductDto productDto= productService.findProductByName(prodName);
        if(Objects.nonNull(productDto)){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(productDto);
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Product name not found");
    }
    @PutMapping("/update-product/{prodId}")
    public ResponseEntity<String> updateProductDetails(@PathVariable("prodId") int prodId, @RequestBody UpdateProductDto updateProductDto){
        Boolean checkProduct = productService.updateProduct(prodId,updateProductDto);

        if(!checkProduct){
            return ResponseEntity.
                    status(HttpStatus.BAD_REQUEST)
                    .body("Product id not found!!!");
        }

        return ResponseEntity.
                status(HttpStatus.OK)
                .body("Product Updated successfully !!!");
    }

    @DeleteMapping("/remove-product/{prodId}")
    public ResponseEntity<String> removeProduct(@PathVariable("prodId") int prodId){
        Boolean checkProduct = productService.deleteProduct(prodId);
        if(!checkProduct){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Product id not found !!!");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Product Deleted Successfully!!!");
    }
}
