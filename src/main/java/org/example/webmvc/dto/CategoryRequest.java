package org.example.webmvc.dto;

import lombok.Builder;

@Builder
public record CategoryRequest (String title, String description){
}
