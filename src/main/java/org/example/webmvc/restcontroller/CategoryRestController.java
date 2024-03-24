package org.example.webmvc.restcontroller;

import lombok.RequiredArgsConstructor;
import org.example.webmvc.dto.CategoryRequest;
import org.example.webmvc.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryRestController {
    private final CategoryService categoryService;

    private Map<String, Object> response(Object object, String message, int status){
        HashMap<String, Object> response = new HashMap<>();
        response.put("payload",object);
        response.put("message", message);
        response.put("status", status);
        return response;
    }

    @GetMapping("/get-all")
    public Map<String, Object> getAllCategory(
            @RequestParam(defaultValue = "" ) String title) {
        return response(
                categoryService.getAllCategory(),
                "Successfully Retrieved all data!",
                HttpStatus.OK.value()
        );

    }
    @PostMapping("/new-category")
    public Map<String, Object> createNewProduct(@RequestBody CategoryRequest request) {
        return response(
                categoryService.createCategory(request),
                "Created New Category Successfully!",
                HttpStatus.CREATED.value());
    }


    @PatchMapping("/{id}")
    public Map<String, Object> updateProduct(@PathVariable int id , @RequestBody CategoryRequest request){
        return response(
                categoryService.updateCategoryByID(id,request),
                "Update Product Successfully",
                HttpStatus.OK.value()
        );
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteProduct(@PathVariable int id){
        categoryService.deleteCategory(id);
        return response(new ArrayList<>(),"Delete Successfully",HttpStatus.OK.value());
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Map<String,Object> findCategoryById(@PathVariable int id){
        return response(categoryService.findCategoryByID(id),"Successfully Retrieved the record!",HttpStatus.FOUND.value());
    }
}
