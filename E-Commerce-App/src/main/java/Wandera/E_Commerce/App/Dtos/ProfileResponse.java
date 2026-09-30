package Wandera.E_Commerce.App.Dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String country;
}
