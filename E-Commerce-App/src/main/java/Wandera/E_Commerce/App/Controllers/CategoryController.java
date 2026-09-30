package Wandera.E_Commerce.App.Controllers;

import Wandera.E_Commerce.App.Dtos.CategoryRequest;
import Wandera.E_Commerce.App.Dtos.CategoryResponse;
import Wandera.E_Commerce.App.Services.ServiceImpl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryServiceImpl categoryServiceImpl;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryServiceImpl.addCategory(categoryRequest);
    }
    @DeleteMapping("/delete/{categoryName}")
    public void deleteCategory(@PathVariable  String categoryName) {
        try {
            categoryServiceImpl.deleteCategory(categoryName);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
        }
    }
    @GetMapping("/all")
    public List<CategoryResponse> getAllCategory(){
        return categoryServiceImpl.getAllCategory();
    }
    @GetMapping("/byId/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id){
        return categoryServiceImpl.getCategoryById(id);
    }
    @GetMapping("/byName/{categoryName}")
    public CategoryResponse getCategoryByCategoryName(@PathVariable String categoryName){
        return categoryServiceImpl.getCategoryByCategoryName(categoryName);
    }

    @PutMapping("/update/{categoryName}")
    public CategoryResponse updateCategory(@PathVariable String categoryName ,@RequestBody CategoryRequest categoryRequest){
        return categoryServiceImpl.updateCategory(categoryName,categoryRequest);

    }
}
