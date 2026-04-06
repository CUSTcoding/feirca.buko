package ccs.com.feirca_buko_api.auth.domain.dto;

import lombok.Getter;

@Getter
public class AuthResponse {

    private final String status;
    private final String token;

    public AuthResponse(String status, String token){
        this.status = status;
        this.token = token;
    }
}
