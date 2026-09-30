package Wandera.E_Commerce.App.Mapper;


import Wandera.E_Commerce.App.Dtos.ProfileResponse;
import Wandera.E_Commerce.App.Entities.UserEntity;

public class ProfileMapper {

    public static ProfileResponse toDto(UserEntity userEntity) {
        ProfileResponse profileResponse = new ProfileResponse();

        profileResponse.setId(userEntity.getId());
        profileResponse.setFirstName(userEntity.getFirstName());
        profileResponse.setLastName(userEntity.getLastName());
        profileResponse.setEmail(userEntity.getEmail());
        profileResponse.setCountry(userEntity.getCountry());
        return profileResponse;
    }
}
