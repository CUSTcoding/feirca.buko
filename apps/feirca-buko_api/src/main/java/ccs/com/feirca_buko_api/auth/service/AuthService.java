package ccs.com.feirca_buko_api.auth.service;

import ccs.com.feirca_buko_api.auth.domain.dto.*;
import ccs.com.feirca_buko_api.auth.domain.entity.Role;
import ccs.com.feirca_buko_api.auth.domain.entity.User;
import ccs.com.feirca_buko_api.auth.repository.UserRepository;
import ccs.com.feirca_buko_api.common.exception.BadRequestException;
import ccs.com.feirca_buko_api.common.exception.ConflictException;
import ccs.com.feirca_buko_api.common.exception.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TwoFAService twoFAService;
    private final EmailService emailService;
    private static final String AUTHENTICATION_FAILED_MESSAGE = "Credenciais inválidas";
    private static final String REGISTRATION_FAILED_MESSAGE = "Não foi possível concluir o registo";
    private static final String PASSWORD_RESET_REQUEST_MESSAGE = "Se a conta existir, enviaremos instruções para redefinição da senha";
    private static final String PASSWORD_RESET_CONFIRM_MESSAGE = "Não foi possível concluir a redefinição da senha";

    public AuthService(
            UserRepository repository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            TwoFAService twoFAService,
            EmailService emailService
    ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.twoFAService = twoFAService;
        this.emailService = emailService;
    }

    public void register(RegisterRequest request){
        if(repository.findByEmail(request.getEmail()).isPresent()){
            throw new ConflictException(REGISTRATION_FAILED_MESSAGE);
        }

        if(repository.findByUsername(request.getUsername()).isPresent()){
            throw new ConflictException(REGISTRATION_FAILED_MESSAGE);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user.setTwoFAActive(false);
        user.setTwoFASecret(null);

        repository.save(user);

    }

    public  String login(LoginRequest request){

        User user = repository.findByEmailOrUsername(request.getLogin(), request.getLogin())
                .orElseThrow(() -> new NotFoundException(AUTHENTICATION_FAILED_MESSAGE));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new BadRequestException(AUTHENTICATION_FAILED_MESSAGE);
        }

        String token = jwtService.generateToken(user);

        twoFAService.send2FACode(user);

        return token;

    }

    public AuthResponse verify2FA(TwoFARequest request) {
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException(AUTHENTICATION_FAILED_MESSAGE));

        boolean validCode = twoFAService.verifyCode(user, request.getCode());
        if (!validCode) {
            throw new BadRequestException(AUTHENTICATION_FAILED_MESSAGE);
        }

        String token = jwtService.generateToken(user);
        return new AuthResponse("2FA verificado com sucesso", token);
    }

    public void requestPasswordReset(PasswordResetRequest request) {
        User user = repository.findByEmail(request.getEmail()).orElse(null);
        if (user == null) {
            return;
        }

        String token = UUID.randomUUID().toString();
        user.setResetPasswordToken(token);
        user.setResetPasswordExpiry(System.currentTimeMillis() + 3600_000); // 1 hora
        repository.save(user);

        emailService.sendPasswordResetEmail(user.getEmail(), token, user.getUsername());
    }

    public void confirmPasswordReset(PasswordResetConfirmRequest request) {
        User user = repository.findByResetPasswordToken(request.getToken())
                .orElseThrow(() -> new BadRequestException(PASSWORD_RESET_CONFIRM_MESSAGE));

        if (user.getResetPasswordExpiry() == null || user.getResetPasswordExpiry() < System.currentTimeMillis()) {
            throw new BadRequestException(PASSWORD_RESET_CONFIRM_MESSAGE);
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setResetPasswordToken(null);
        user.setResetPasswordExpiry(null);
        repository.save(user);
    }
}
