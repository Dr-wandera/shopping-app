package Wandera.E_Commerce.App.Mapper;


import Wandera.E_Commerce.App.Dtos.UserResponse;
import Wandera.E_Commerce.App.Entities.UserEntity;

public class UserMapper {
    public static UserResponse toDto(UserEntity userEntity) {
        UserResponse userResponse = new UserResponse();
        userResponse.setCountry(userEntity.getCountry());
        userResponse.setFirstName(userEntity.getFirstName());
        userResponse.setLastName(userEntity.getLastName());
        userResponse.setEmail(userEntity.getEmail());

        return userResponse;
    }
}
