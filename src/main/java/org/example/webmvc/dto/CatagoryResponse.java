package org.example.webmvc.dto;

import lombok.Builder;

@Builder
public record CatagoryResponse(int id,String title,String description) {
}
