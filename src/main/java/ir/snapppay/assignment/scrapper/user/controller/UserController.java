package ir.snapppay.assignment.scrapper.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import ir.snapppay.assignment.scrapper.user.model.dto.ResponseDTO;
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

    @Operation(summary = "User signup", description = "An api for user registration")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User registered successfully!")})
    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO> signup(@Valid @RequestBody SignupDTO dto) {
        return ResponseEntity.ok(userService.signup(dto));
    }


}
