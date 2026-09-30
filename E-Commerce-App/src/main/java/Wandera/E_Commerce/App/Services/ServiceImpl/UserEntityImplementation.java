package Wandera.E_Commerce.App.Services.ServiceImpl;

import Wandera.E_Commerce.App.Dtos.ProfileRequest;
import Wandera.E_Commerce.App.Dtos.UserResponse;
import Wandera.E_Commerce.App.Enum.Role;
import Wandera.E_Commerce.App.Entities.UserEntity;
import Wandera.E_Commerce.App.Mapper.UserMapper;
import Wandera.E_Commerce.App.Repositories.UserEntityRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserEntityImplementation {

    private final PasswordEncoder passwordEncoder;
    private final UserEntityRepository userEntityRepository;
    private final OtpVerificationServiceImplementation otpVerificationServiceImplementation;

    @CacheEvict(value = "ProfileResponse",allEntries = true)
    @Cacheable(value = "ProfileResponse", key = "#profileRequest.getEmail()") //cache
    public void registerUser(ProfileRequest profileRequest) throws MessagingException, IOException {

        //this check if the email already in the database
        Optional<UserEntity> existingUser = userEntityRepository.findByEmail(profileRequest.getEmail());

        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists! Try to login");
        }

        Role assignedRole;

        // If role is not provided → automatically assign it to USER
        if (profileRequest.getRole() == null) {
            assignedRole = Role.USER;
        }
        // If role is provided → accept it
        else {
            assignedRole = Role.valueOf(profileRequest.getRole());
        }

        //creates instance of  the userEntity
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setFirstName(profileRequest.getFirstName());
        userEntity.setLastName(profileRequest.getLastName());
        userEntity.setEmail(profileRequest.getEmail());
        userEntity.setPassword(passwordEncoder.encode(profileRequest.getPassword()));
        userEntity.setCountry(profileRequest.getCountry());
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setUpdatedAt(LocalDateTime.now());
        userEntity.setVerified(false);
        userEntity.setPhoneNumber(profileRequest.getPhoneNumber());
        userEntity.setRole(assignedRole);

         var savedUser=userEntityRepository.save(userEntity);

        // Delegate OTP logic to generate  otp to verify the user account
        otpVerificationServiceImplementation.verifyEmailAdress(savedUser);

        ResponseEntity.status(HttpStatus.CREATED).body(savedUser);

    }

    //this get the logged-in user from security context holder
    public UserEntity getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() ||
                Objects.equals(auth.getPrincipal(), "anonymousUser")) {
            return null;
        }

        String email = auth.getName();
        return userEntityRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<UserResponse> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
         var users= userEntityRepository.findAll(pageable);

        // Convert each UserEntity to UserResponse
        return users.stream()
                .map(UserMapper::toDto)
                .toList();

    }
}
