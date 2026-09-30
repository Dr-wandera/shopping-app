package Wandera.E_Commerce.App.Controllers;

import Wandera.E_Commerce.App.Dtos.AddStockRequest;
import Wandera.E_Commerce.App.Dtos.ProductRequest;
import Wandera.E_Commerce.App.Dtos.ProductResponse;
import Wandera.E_Commerce.App.Services.ServiceImpl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductServiceImpl productService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ProductResponse addProduct(@RequestBody ProductRequest productRequest) {

        return productService.addProduct(productRequest);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProduct(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size

    ){
        return productService.getAllProduct(size, page);

    }
    @GetMapping("/name/{productName}")
    public List<ProductResponse> getProductByName(@PathVariable String productName) {
        return productService.getProductByName(productName);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @PutMapping("/updateProduct/{productId}")
    public ProductResponse updateProduct(@PathVariable Long productId,@RequestBody ProductRequest productRequest) {
        return productService.updateProduct(productId,productRequest);
    }

    @PatchMapping("/stock/{productCode}")
    @ResponseStatus(HttpStatus.CREATED)
    public String addStock(@PathVariable String productCode, @RequestBody AddStockRequest addStockRequest){
        productService.addStock(productCode,addStockRequest);
        return "Stock updated successful";
    }
}


