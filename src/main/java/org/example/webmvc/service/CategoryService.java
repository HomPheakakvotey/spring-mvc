package org.example.webmvc.service;

import org.example.webmvc.dto.CatagoryResponse;
import org.example.webmvc.dto.CategoryRequest;

import java.util.List;

public interface CategoryService {
    List<CatagoryResponse> getAllCategory();

    CatagoryResponse createCategory(CategoryRequest categoryRequest);

    void deleteCategory(int id);
    CatagoryResponse findCategoryByID(int id);
    CatagoryResponse updateCategoryByID(int id,CategoryRequest categoryRequest);

}
