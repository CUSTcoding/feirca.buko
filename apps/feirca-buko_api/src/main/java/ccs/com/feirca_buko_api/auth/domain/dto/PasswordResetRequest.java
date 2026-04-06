package ccs.com.feirca_buko_api.auth.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetRequest {
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email invalido")
    private String email;
}
