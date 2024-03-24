package org.example.webmvc.dto;


import lombok.Builder;

//return value back to user
@Builder
public record ProductResponse
        (int id , String title , String description, float price, String imageUrl)
{
}
