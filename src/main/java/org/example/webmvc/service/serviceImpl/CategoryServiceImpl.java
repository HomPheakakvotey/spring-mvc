package org.example.webmvc.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.webmvc.dto.CatagoryResponse;
import org.example.webmvc.dto.CategoryRequest;
import org.example.webmvc.model.Category;
import org.example.webmvc.repository.CategoryRepository;
import org.example.webmvc.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Comparator;
import java.util.List;
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    private Category searchCategoryById(int id){
        return categoryRepository.getCategories()
                .stream().filter(c -> c.getId()==id)
                .findFirst()
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND,"No Category found"));
    }

    private CatagoryResponse mapCategoryToResponse(Category category){
        return CatagoryResponse.builder()
                .id(category.getId())
                .title(category.getTitle())
                .description(category.getDescription())
                .build();
    }

    private Category mapRequestToCategory(CategoryRequest categoryRequest){
        return Category
                .builder()
                .title(categoryRequest.title())
                .description(categoryRequest.description())
                .build();
    }

    @Override
    public List<CatagoryResponse> getAllCategory() {
        return categoryRepository.getCategories()
                .stream()
                .map(c -> {
                    return CatagoryResponse.builder()
                            .id(c.getId())
                            .title(c.getTitle())
                            .description(c.getDescription())
                            .build();
                }).toList();
    }

    @Override
    public CatagoryResponse createCategory(CategoryRequest categoryRequest) {
        Category newCategory = Category.builder()
                .title(categoryRequest.title())
                .description(categoryRequest.description())
                .build();

        var maxID = categoryRepository.getCategories()
                .stream().max(Comparator.comparingInt(Category::getId)).map(Category::getId);
        int newID = 1;
        if (maxID.isPresent()) {
            newID = maxID.get() + 1;
        }
        newCategory.setId(newID);
        categoryRepository.addCatagory(newCategory);

        return CatagoryResponse.builder()
                .id(newCategory.getId())
                .title(newCategory.getTitle())
                .description(newCategory.getDescription())
                .build();

    }
    @Override
    public void deleteCategory(int id) {
        categoryRepository.deleteCategory(searchCategoryById(id).getId());
    }

    @Override
    public CatagoryResponse findCategoryByID(int id) {
        Category category = categoryRepository.getCategories()
                .stream()
                .filter( c -> c.getId()==id)
                .findFirst()
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND,"Category not found!!"));
        return mapCategoryToResponse(category);
    }

    @Override
    public CatagoryResponse updateCategoryByID(int id, CategoryRequest categoryRequest) {
        var result = searchCategoryById(id);
        result = mapRequestToCategory(categoryRequest);
        result.setId(id);
        categoryRepository.updateCategory(result);
        return mapCategoryToResponse(result);
    }
}
