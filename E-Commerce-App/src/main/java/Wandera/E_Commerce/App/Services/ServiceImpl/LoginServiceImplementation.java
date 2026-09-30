package Wandera.E_Commerce.App.Services.ServiceImpl;

import Wandera.E_Commerce.App.Dtos.LoginRequest;
import Wandera.E_Commerce.App.Dtos.LoginResponse;
import Wandera.E_Commerce.App.Entities.UserEntity;
import Wandera.E_Commerce.App.Jwt.JwtService;
import Wandera.E_Commerce.App.Repositories.UserEntityRepository;
import Wandera.E_Commerce.App.SecurityConfiguration.AppUserDetailService;
import Wandera.E_Commerce.App.Services.Interfaces.LoginServiceInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImplementation implements LoginServiceInterface {
    private final UserEntityRepository userEntityRepository;
    private final AppUserDetailService userDetailsService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        UserEntity user=userEntityRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()->new RuntimeException("invalid email! check and try again "));

        /*
              .this check first if user is verified in if not, not allowed to log in
              .this  prevents log in if you have not verified the account
         */

        if (!user.isVerified()) {
            throw new RuntimeException("Please verify your account before logging in.");
        }

        log.info("logged in:{}", loginRequest);
        try {
            authenticate(loginRequest.getEmail(), loginRequest.getPassword());

            final UserDetails userDetails =
                    userDetailsService.loadUserByUsername(loginRequest.getEmail());

            String jwtToken = jwtService.generateToken(userDetails);

            return ResponseEntity.ok()
                    .body(new LoginResponse(loginRequest.getEmail(), jwtToken));

        } catch (BadCredentialsException ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", true);
            response.put("message", "Incorrect email or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( response);

        }
        catch (DisabledException ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", true);
            response.put("message", "account is disabled");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body( response);

        }
        catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", true);
            response.put("message", "Authentication Failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body( response);

        }
    }

    private void authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }

}
