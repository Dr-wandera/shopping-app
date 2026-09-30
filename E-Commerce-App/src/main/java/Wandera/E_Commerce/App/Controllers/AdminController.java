package Wandera.E_Commerce.App.Controllers;

import Wandera.E_Commerce.App.Dtos.*;
import Wandera.E_Commerce.App.Services.ServiceImpl.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final CategoryServiceImpl categoryServiceImpl;
    private final ProductServiceImpl productService;
    private final CartServiceImplementation cartServiceImplementation;
    private final OrderEntityServiceImplementation orderEntityServiceImplementation;
    private final UserEntityImplementation userEntityImplementation;

    @PostMapping("/addCategory")
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
    @PutMapping("/updateCategory/{categoryName}")
    public CategoryResponse updateCategory(@PathVariable String categoryName ,@RequestBody CategoryRequest categoryRequest){
        return categoryServiceImpl.updateCategory(categoryName,categoryRequest);

    }
    @PostMapping("/addProduct")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse addProduct(@RequestBody ProductRequest productRequest) {
        try {
            return  productService.addProduct(productRequest);
        }catch (Exception ex){
            throw new RuntimeException("adding product failed try again");

        }

    }
    @GetMapping("/allCategory")
    public List<CategoryResponse> getAllCategory(){
        return categoryServiceImpl.getAllCategory();
    }
    @DeleteMapping("/deleteItem/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteCartItem(@PathVariable Long id) {
        cartServiceImplementation.deleteCartItem(id);
        return ResponseEntity.ok("Cart item deleted successfully");
    }
    @GetMapping("/getAllOrder")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<OrderResponse> getAllOrder(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return orderEntityServiceImplementation.getAllOrder(page, size);
    }
    @GetMapping("/getByOrderId/{orderNumber}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OrderResponse findByOrderId(@PathVariable String orderNumber) {
        return orderEntityServiceImplementation.getByOrderId(orderNumber);
    }
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<UserResponse> getAllUser(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size

    ){
       return userEntityImplementation.getAllUsers(page,size);
    }
}
