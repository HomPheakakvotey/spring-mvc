package org.example.webmvc.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Product {
    private int id;
    private String name;
    private String description;
    private float price;
    private String imageUrl;
}
