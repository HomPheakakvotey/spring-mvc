package org.example.webmvc.dto;

// for user input
public record ProductRequest(     String title,
                                  String description,
                                  float price,
                                  String imageUrl) {
}
