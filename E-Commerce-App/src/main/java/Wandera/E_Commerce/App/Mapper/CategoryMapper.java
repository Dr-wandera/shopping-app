package Wandera.E_Commerce.App.Mapper;


import Wandera.E_Commerce.App.Dtos.CategoryRequest;
import Wandera.E_Commerce.App.Dtos.CategoryResponse;
import Wandera.E_Commerce.App.Entities.Category;

public class CategoryMapper {

    public static CategoryResponse toDto(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setCategoryName(category.getCategoryName());
        categoryResponse.setDescription(category.getDescription());
        // Add products inside category response
        categoryResponse.setProducts(
                category.getProducts()
                        .stream()
                        .map(ProductMapper::toDto)
                        .toList()
        );
        return categoryResponse;
    }
    public static Category toEntity(CategoryRequest categoryRequest) {
        Category category = new Category();

        category.setCategoryName(categoryRequest.getCategoryName());
        category.setDescription(categoryRequest.getDescription());
        return category;

    }
}
