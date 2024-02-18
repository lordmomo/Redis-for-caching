package com.momo.redisandmysqlcache.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDto implements Serializable {
    private String prodName;
    private int quantity;
    private Long price;
}
