package Wandera.E_Commerce.App.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponse {
    private String categoryName;
    private String description;
    private List<ProductResponse> products;

}
