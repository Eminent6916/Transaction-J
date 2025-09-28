package com.UserService.Service.Implement;

import com.UserService.Config.CustomUserDetailsService;
import com.UserService.Constants.Messages;
import com.UserService.Dto.Request.*;
import com.UserService.Dto.Response.ApiResponse;
import com.UserService.Dto.Response.JwtData;
import com.UserService.Dto.Response.LoginResponse;
import com.UserService.Model.AccountDetails;
import com.UserService.Model.Tier;
import com.UserService.Model.User;
import com.UserService.Repository.TierRepository;
import com.UserService.Repository.UserRepo;
import com.UserService.Service.UserServ;
import com.UserService.Utils.Common;
import com.UserService.Utils.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class UserServImplement implements UserServ {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final TierRepository tierRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);


    public UserServImplement(UserRepo userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, TierRepository tierRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.tierRepository = tierRepository;
    }


    @Override
    public Optional<User> fetchByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public ResponseEntity<ApiResponse<String>> createPassword(CreatePasswordRequest request) {
        Optional<User> existingUserOpt = userRepository.findByEmail(request.getEmail());

        if (existingUserOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(Messages.INVALID_USER, null));
        }

        User user = existingUserOpt.get();

        if (!user.getIsMailVerified()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(Messages.MAIL_NOT_VERIFIED, null));
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        return ResponseEntity.ok(ApiResponse.success(Messages.PASSWORD_CREATED, null));
    }


    @Override
    public ResponseEntity<ApiResponse<LoginResponse>> login(LoginRequest request) {
        Optional<User> existingUserOpt = userRepository.findByEmail(request.getEmail());

        if (existingUserOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(Messages.LOGIN_ERROR, null));
        }

        User user = existingUserOpt.get();

        if (!user.getIsMailVerified()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(Messages.LOGIN_ERROR, null));
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(Messages.LOGIN_ERROR, null));
        }

        JwtData payload = new JwtData(
                user.getId(),
                user.getEmail(),
                user.getStatus(),
                user.getIsMailVerified()
        );

        String token = jwtUtil.generateToken(payload);

        LoginResponse response = new LoginResponse(
                token,
                user.getIsOnboardingComplete(),
                user.getIsPinCreated()
        );

        return ResponseEntity.ok(ApiResponse.success(Messages.USER_LOGIN, response));
    }


    @Override
    @Transactional
    public ResponseEntity<ApiResponse<Object>> personalDetails(PersonalDataRequest request) {
        Optional<User> existingUserOpt = userRepository.findByEmail(request.getEmail());

        if (existingUserOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(Messages.INVALID_USER, null));
        }

        User user = existingUserOpt.get();

        if (!Boolean.TRUE.equals(user.getIsMailVerified())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(Messages.MAIL_NOT_VERIFIED, null));
        }

        if (Boolean.TRUE.equals(user.getIsOnboardingComplete())) {
            return ResponseEntity.ok(ApiResponse.success(Messages.PROFILE_COMPLETED, user.getAccountDetails()));
        }

        AccountDetails accountDetails = user.getAccountDetails();
        if (accountDetails == null) {
            accountDetails = new AccountDetails();
            accountDetails.setUser(user);
        }

        accountDetails.setFirstName(request.getFirstName());
        accountDetails.setLastName(request.getLastName());
        accountDetails.setMiddleName(request.getMiddleName());
        accountDetails.setPhone(request.getPhone());
        accountDetails.setCountry(request.getCountry());
        accountDetails.setAddress(request.getAddress());

        if (accountDetails.getTier() == null) {
            Optional<Tier> baseTierOpt = tierRepository.findByNameIgnoreCase("Gold");

            if (baseTierOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(Messages.TIER_NOT_FOUND, null));
            }

            Tier baseTier = baseTierOpt.get();
            accountDetails.setTier(baseTier);
        }

        // Link and save
        user.setAccountDetails(accountDetails);
        user.setIsOnboardingComplete(true);

        userRepository.save(user);

        return ResponseEntity.ok(ApiResponse.success(Messages.PROFILE_UPDATED, accountDetails));
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> verifyIdentity(VerifyIdentityRequest request) {
        Optional<User> existingUserOpt = userRepository.findByEmail(request.getEmail());

        if (existingUserOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(Messages.INVALID_USER, null));
        }

        User user = existingUserOpt.get();

        if (!Boolean.TRUE.equals(user.getIsOnboardingComplete())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(Messages.PROFILE_NOT_COMPLETED, null));
        }

        AccountDetails accountDetails = user.getAccountDetails();

        String bvn = request.getBvn();
        if (bvn != null && !bvn.trim().isEmpty()) {
            Boolean bvnValidator = Common.bvnValidator(bvn);
            if (!bvnValidator) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error(Messages.INVALID_BVN, null));
            }

            accountDetails.setPhone(bvn);

            Optional<Tier> baseTierOpt = tierRepository.findByNameIgnoreCase("Diamond");
            if (baseTierOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.error(Messages.TIER_NOT_FOUND, null));
            }

            Tier baseTier = baseTierOpt.get();
            accountDetails.setTier(baseTier);
        }

        String identity = request.getIdentity();
        if (identity != null && !identity.trim().isEmpty()){
            user.setIdentity(identity);
            user.setIdentityType(request.getIdentityType());
        }

        user.setAccountDetails(accountDetails);
        userRepository.save(user);

        return ResponseEntity.ok(ApiResponse.success(Messages.IDENTITY_SUBMITED, accountDetails));
    }


    @Override
    public ResponseEntity<ApiResponse<Object>> createPin(CreatePinRequest request) {
            if (request.getPin() == null || request.getPin().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("PIN is required", null));
            }

            if (request.getPin().length() != 6) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("PIN must be exactly 6 digits", null));
            }

            if (!request.getPin().matches("\\d+")) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("PIN must contain only digits", null));
            }

            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Email is required", null));
            }

            // Find user by email
            Optional<User> existingUserOpt = userRepository.findByEmail(request.getEmail());

            if (existingUserOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(Messages.INVALID_USER, null));
            }
            User user =existingUserOpt.get();


            if (user.getAccountDetails() == null) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Account details not found. Please complete onboarding first.", null));
            }

            // Hash the PIN before storing (using BCrypt)
            String hashedPin = passwordEncoder.encode(request.getPin());

            // Update the PIN in account details
            AccountDetails accountDetails = user.getAccountDetails();
            accountDetails.setPin(hashedPin);

            // Update user PIN status
            user.setIsPinCreated(true);

            user.setAccountDetails(accountDetails);
            userRepository.save(user);


            return ResponseEntity.ok()
                    .body(ApiResponse.success("PIN created successfully",null));

    }

}