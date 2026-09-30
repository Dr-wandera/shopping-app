package Wandera.E_Commerce.App.Services.Interfaces;

import Wandera.E_Commerce.App.Dtos.ProductRequest;
import Wandera.E_Commerce.App.Dtos.ProductResponse;

public interface ProductInterface {
    ProductResponse addProduct(ProductRequest productRequest);
      ProductResponse updateProduct(ProductRequest productRequest);


}
