package org.example.webmvc.repository;

import org.example.webmvc.model.Category;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class CategoryRepository {

    private final List<Category> categories = new ArrayList<>() {{
        add(Category.builder()
                .id(1)
                .title("Food")
                .description("All you can eat")
                .build());

        add(Category.builder()
                .id(2)
                .title("Drink")
                .description("All you can drink")
                .build());
    }};

    public List<Category> getCategories(){
        return categories;
    }

    public void addCatagory(Category category){
        categories.add(category);
    }

    public void deleteCategory(int id){
        int index = categories.indexOf(
                categories.stream()
                        .filter(cat -> cat.getId()==id)
                        .findFirst()
                        .orElse(null)
        );
        categories.remove(index);
    }

    public void updateCategory(Category category){
        int index = categories.indexOf(
                categories.stream()
                        .filter(cat -> cat.getId()==category.getId())
                        .findFirst()
                        .orElse(null)
        );
        categories.set(index,category);
    }

}
