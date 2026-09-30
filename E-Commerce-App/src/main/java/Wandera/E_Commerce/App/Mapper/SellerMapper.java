package Wandera.E_Commerce.App.Mapper;


import Wandera.E_Commerce.App.Dtos.SellerResponse;
import Wandera.E_Commerce.App.Entities.SellerProfile;

public class SellerMapper {
    public static SellerResponse toDto(SellerProfile sellerProfile) {
        SellerResponse sellerResponse = new SellerResponse();


        sellerResponse.setStoreAddress(sellerProfile.getStoreAddress());
        sellerResponse.setStoreName(sellerProfile.getStoreName());
        sellerResponse.setIdNumber(String.valueOf(sellerProfile.getIdNumber()));
        sellerResponse.setPhoneNumber(String.valueOf(sellerProfile.getPhoneNumber()));
        sellerResponse.setAccountNumber(String.valueOf(sellerProfile.getAccountNumber()));
        return sellerResponse;

    }
}
