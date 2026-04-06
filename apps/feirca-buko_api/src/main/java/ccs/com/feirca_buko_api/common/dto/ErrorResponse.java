package ccs.com.feirca_buko_api.common.dto;

import lombok.Getter;

import java.time.Instant;
import java.util.Map;

@Getter
public class ErrorResponse {

    private final Instant timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final Map<String, String> validationErrors;

    public ErrorResponse(int status, String error, String message, Map<String, String> validationErrors) {
        this.timestamp = Instant.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.validationErrors = validationErrors;
    }
}
