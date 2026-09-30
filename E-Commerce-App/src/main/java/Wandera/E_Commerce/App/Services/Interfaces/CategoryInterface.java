package Wandera.E_Commerce.App.Services.Interfaces;

import Wandera.E_Commerce.App.Dtos.CategoryRequest;
import Wandera.E_Commerce.App.Dtos.CategoryResponse;

import java.util.List;

public interface CategoryInterface {
    CategoryResponse addCategory(CategoryRequest category);

    void deleteCategory(String categoryName);

    List<CategoryResponse> getAllCategory();

    CategoryResponse getCategoryById(Long id);

    CategoryResponse getCategoryByCategoryName(String categoryName);

    CategoryResponse updateCategory(String categoryName, CategoryRequest categoryRequest);
}
