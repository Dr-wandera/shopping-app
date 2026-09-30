package Wandera.E_Commerce.App.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CategoryRequest {
    private String categoryName;
    private String description;
}
