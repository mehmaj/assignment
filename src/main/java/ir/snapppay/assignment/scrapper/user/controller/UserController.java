package ir.snapppay.assignment.scrapper.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.snapppay.assignment.scrapper.exception.InvalidInputException;
import ir.snapppay.assignment.scrapper.exception.UnauthorizedException;
import ir.snapppay.assignment.scrapper.user.model.dto.ResponseDTO;
import ir.snapppay.assignment.scrapper.user.model.dto.SignInDTO;
import ir.snapppay.assignment.scrapper.user.model.dto.SignupDTO;
import ir.snapppay.assignment.scrapper.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "the authorization Api")
@RestController
@RequestMapping("/auth/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "User signUp", description = "An api for user registration")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User registered successfully!")})
    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDTO> signUp(@Valid @RequestBody SignupDTO dto) {
        try {
            return ResponseEntity.ok(userService.signUp(dto));
        } catch (InvalidInputException e) {
            return ResponseEntity.status(e.getHttpStatus().value()).body(new ResponseDTO(e.getMessage()));
        }
    }

    @Operation(summary = "User signIn", description = "An api for user authorization")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "return SignInResponseDTO model")})
    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInDTO dto) {
        try {
            return ResponseEntity.ok().body(userService.signIn(dto));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(e.getHttpStatus().value()).body(e.getMessage());
        }
    }

}
