package ccs.com.feirca_buko_api.auth.controller;

import ccs.com.feirca_buko_api.auth.domain.dto.*;
import ccs.com.feirca_buko_api.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request){
        authService.register(request);
        return ResponseEntity.ok(new AuthResponse("Usuário registado com sucesso", null));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request){
        String token = authService.login(request);
        return ResponseEntity.ok(new AuthResponse("Login efectuado com sucesso", token));
    }

    @PostMapping("/2fa/verify")
    public ResponseEntity<AuthResponse> verify2FA(@Valid @RequestBody TwoFARequest request) {
        return ResponseEntity.ok(authService.verify2FA(request));
    }

    @PostMapping("/password/reset")
    public ResponseEntity<AuthResponse> requestPasswordReset(@Valid @RequestBody PasswordResetRequest request) {
        authService.requestPasswordReset(request);
        return ResponseEntity.ok(new AuthResponse("Se a conta existir, enviaremos instruções para redefinição da senha", null));
    }

    @PostMapping("/password/reset/confirm")
    public ResponseEntity<AuthResponse> confirmPasswordReset(@Valid @RequestBody PasswordResetConfirmRequest request) {
        authService.confirmPasswordReset(request);
        return ResponseEntity.ok(new AuthResponse("Senha redefinida com sucesso", null));
    }
}
