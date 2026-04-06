package ccs.com.feirca_buko_api.auth.domain.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TwoFARequest {

    @NotBlank(message = "Email é obrigatório")
    private String email;

    @Min(value = 0, message = "Código 2FA inválido")
    @Max(value = 999999, message = "Código 2FA inválido")
    private int code;

}
