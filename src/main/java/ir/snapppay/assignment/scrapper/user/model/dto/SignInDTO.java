package ir.snapppay.assignment.scrapper.user.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignInDTO {
    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required!")
    @Size(max = 50,message = "Maximum length of email can be 50 characters!")
    private String email;
    @NotBlank(message = "Password is required!")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",message = "Ensure the entered password is at least 8 characters long, contains a letter, and a number")
    @Size(max = 120,message = "Maximum length of password can be 120 characters!")
    private String password;
}
