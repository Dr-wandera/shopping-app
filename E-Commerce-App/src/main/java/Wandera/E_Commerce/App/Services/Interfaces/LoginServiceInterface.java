package Wandera.E_Commerce.App.Services.Interfaces;

import Wandera.E_Commerce.App.Dtos.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface LoginServiceInterface {

    ResponseEntity<?> login(LoginRequest loginRequest);
}
