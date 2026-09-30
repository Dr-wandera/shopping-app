package Wandera.E_Commerce.App.Mapper;


import Wandera.E_Commerce.App.Dtos.ProductResponse;
import Wandera.E_Commerce.App.Entities.Product;

import java.util.Arrays;

public class ProductMapper {
    public static ProductResponse toDto(Product product) {
        ProductResponse productResponse= new ProductResponse();

        productResponse.setId(product.getProductId());
        productResponse.setProductName(product.getProductName());
        productResponse.setProductDescription(product.getProductDescription());
        productResponse.setProductPrice(product.getProductPrice());
        productResponse.setCategoryName(product.getCategoryName());
        productResponse.setBackgroundColor(product.getBackgroundColor());
        productResponse.setProductImageUrl(Arrays.toString(product.getProductImageUrl().toCharArray()));
        productResponse.setProductStock(product.getProductStock());
        return productResponse;
    }
}
