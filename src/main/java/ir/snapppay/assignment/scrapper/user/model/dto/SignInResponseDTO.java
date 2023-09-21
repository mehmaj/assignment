package ir.snapppay.assignment.scrapper.user.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInResponseDTO {
    private String email;
    private String token;
}
