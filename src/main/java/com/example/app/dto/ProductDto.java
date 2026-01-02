package com.example.app.dto;

import lombok.Data;

@Data
public class ProductDto {
    private String name;
    private String description;
    private String serialNumber;
    private Long sellerId;
}
