package Wandera.E_Commerce.App.Services.ServiceImpl;

import Wandera.E_Commerce.App.Dtos.CategoryRequest;
import Wandera.E_Commerce.App.Dtos.CategoryResponse;
import Wandera.E_Commerce.App.Entities.Category;
import Wandera.E_Commerce.App.Enum.Role;
import Wandera.E_Commerce.App.Entities.UserEntity;
import Wandera.E_Commerce.App.Exceptions.UnauthorizedException;
import Wandera.E_Commerce.App.Mapper.CategoryMapper;
import Wandera.E_Commerce.App.Repositories.CategoryRepository;
import Wandera.E_Commerce.App.Services.Interfaces.CategoryInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryInterface {

    private final CategoryRepository categoryRepository;
    private final UserEntityImplementation userEntityImplementation;

    @CacheEvict(value = "CategoryResponse", allEntries = true)
    @Override
    public CategoryResponse addCategory(CategoryRequest categoryRequest) {

        UserEntity user= userEntityImplementation.getLoggedInUser();

        if (user.getRole()== Role.USER){
            throw new UnauthorizedException("You are not allowed to add category");

        }

        log.info("addCategory "+ categoryRequest);

        var toEntity = CategoryMapper.toEntity(categoryRequest);
        Category saveCategory = categoryRepository.save(toEntity);

        return CategoryMapper.toDto(saveCategory);
    }

    @Override
    public void deleteCategory(String categoryName) {

        Category existsCategory=categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(()->new RuntimeException("Category by name not found"));
        categoryRepository.delete(existsCategory);

    }

    @Cacheable(value = "CategoryResponse")
    @Override
    public List<CategoryResponse> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(CategoryMapper::toDto)
                .toList();
   }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category by id is not available"));

        if (category != null) {
            return CategoryMapper.toDto(category);

        }
        return null;
    }

    @Cacheable(value = "CategoryResponse",key = "#categoryName")
    @Override
    public CategoryResponse getCategoryByCategoryName(String categoryName) {

        log.info("Searching by category name: {}",categoryName );


        Category existsCategory= categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(()->new RuntimeException("Category by name not found"));
        try {
            if(existsCategory!=null){
                return CategoryMapper.toDto(existsCategory);
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }

    @CachePut(value = "CategoryResponse",key = "#categoryName")
    @Override
    public CategoryResponse updateCategory(String categoryName, CategoryRequest categoryRequest) {

        log.info("Updating category: {}",categoryName);

        Category category=categoryRepository.findByCategoryName(categoryName)
                .orElseThrow(()->new RuntimeException("Category by name not found"));

        if (Objects.nonNull(category.getCategoryName())&&!"".equalsIgnoreCase(category.getCategoryName())) {
            category.setCategoryName(categoryRequest.getCategoryName());
        }
        if (Objects.nonNull(categoryRequest.getDescription())&&!"".equalsIgnoreCase(categoryRequest.getDescription())) {
            category.setDescription(categoryRequest.getDescription());
        }

         categoryRepository.save(category);
       return CategoryMapper.toDto(category);
    }
}
