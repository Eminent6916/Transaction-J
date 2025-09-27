package com.UserService.Service.Implement;

import com.UserService.Constants.Messages;
import com.UserService.Constants.VerificationStatus;
import com.UserService.Dto.Response.ApiResponse;
import com.UserService.Dto.Response.VerifyMailResponse;
import com.UserService.Model.User;
import com.UserService.Repository.UserRepo;
import com.UserService.Service.VerificationService;
import com.UserService.Utils.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VerificationServiceImplement implements VerificationService {

    @Autowired
    private UserRepo userRepository;

    public String generateVerificationToken(User user) {
        String token = Common.generateToken(6);
        user.setVerificationToken(token);
        user.setVerificationTokenExpiry(LocalDateTime.now().plusMinutes(15));
        userRepository.save(user);
        return token;
    }

    public ResponseEntity<ApiResponse<VerifyMailResponse>> initiateVerification(String email) {
        Optional<User> existingUserOpt = userRepository.findByEmail(email);

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            if (Boolean.TRUE.equals(existingUser.getIsMailVerified())) {
                return ResponseEntity.ok(ApiResponse.success(Messages.MAIL_ALREADY_VERIFIED, null));
            }

            String token = generateVerificationToken(existingUser);

            VerifyMailResponse response = new VerifyMailResponse(
                    token,
                    existingUser.getEmail(),
                    existingUser.getVerificationTokenExpiry().toString()
            );

            return ResponseEntity.ok(ApiResponse.success(Messages.VERIFICATION_TOKEN_GENERATED, response));

        } else {
            User newUser = User.builder()
                    .email(email)
                    .isMailVerified(false)
                    .verificationStatus(VerificationStatus.PENDING)
                    .build();

            String token = generateVerificationToken(newUser);

            VerifyMailResponse response = new VerifyMailResponse(
                    token,
                    newUser.getEmail(),
                    newUser.getVerificationTokenExpiry().toString()
            );

            return ResponseEntity.ok(ApiResponse.success(Messages.VERIFICATION_TOKEN_GENERATED, response));
        }
    }
    @Override
    public ResponseEntity<ApiResponse<String>> verifyEmail(String token) {
        Optional<User> existingUserOpt = userRepository.findByVerificationToken(token);
        if (existingUserOpt.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.error(Messages.INVALID_USER, null));
        }

        User user = existingUserOpt.get();

        if (user.getVerificationTokenExpiry().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Verification token has expired", null));
        }

        user.setIsMailVerified(true);
        user.setVerificationToken(null);
        user.setVerificationTokenExpiry(null);
        userRepository.save(user);

        return ResponseEntity.ok(ApiResponse.success("Email verified successfully. Signup", null));
    }


}
