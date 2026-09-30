package Wandera.E_Commerce.App.Services.Interfaces;

import Wandera.E_Commerce.App.Dtos.SellerProfileRequest;
import Wandera.E_Commerce.App.Dtos.SellerResponse;

public interface SellerProfileInterface {
    SellerResponse addSellerProfile(SellerProfileRequest sellerProfileRequest);
}
