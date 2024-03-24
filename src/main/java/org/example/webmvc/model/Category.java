package org.example.webmvc.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Category {
    private int id;
    private String title;
    private String description;
}
