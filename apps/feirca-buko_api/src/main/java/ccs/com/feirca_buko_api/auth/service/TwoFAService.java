package ccs.com.feirca_buko_api.auth.service;

import ccs.com.feirca_buko_api.auth.domain.entity.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class TwoFAService {

    private final SecureRandom secureRandom = new SecureRandom();

    public void send2FACode(User user) {
        if (user.getTwoFASecret() == null || user.getTwoFASecret().isBlank()) {
            user.setTwoFASecret(String.format("%06d", secureRandom.nextInt(1_000_000)));
        }
    }

    public boolean verifyCode(User user, int code) {
        if (user.getTwoFASecret() == null || user.getTwoFASecret().isBlank()) {
            return false;
        }

        return user.getTwoFASecret().equals(String.format("%06d", code));
    }
}
