package ir.snapppay.assignment.scrapper.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInResponseDTO {
    @Schema( example ="sample@gmail.com")
    private String email;
    @Schema( example ="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2NTBjOGE3N2VjZTIyZDM4OWM3ZjQxYzUiLCJpYXQiOjE2OTUzMjU3NTIsImV4cCI6MTY5NTQxMjE1Mn0.BsbVTSseG5QlmLJO5mvIkTmBczZvUOP5RzBxnb5QtitNhKqEykPGbYnnaKjfQcHD4QNaukMxdHHYOzK-rv72qQ")
    private String token;
}
