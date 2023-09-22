package ir.snapppay.assignment.scrapper.user.service;

import ir.snapppay.assignment.scrapper.app.security.jwt.JwtUtils;
import ir.snapppay.assignment.scrapper.user.model.ERole;
import ir.snapppay.assignment.scrapper.user.model.Role;
import ir.snapppay.assignment.scrapper.user.model.UserDomain;
import ir.snapppay.assignment.scrapper.user.model.dto.ResponseDTO;
import ir.snapppay.assignment.scrapper.user.model.dto.SignInDTO;
import ir.snapppay.assignment.scrapper.user.model.dto.SignInResponseDTO;
import ir.snapppay.assignment.scrapper.user.model.dto.SignupDTO;
import ir.snapppay.assignment.scrapper.user.repository.RoleRepository;
import ir.snapppay.assignment.scrapper.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    final private AuthenticationManager authenticationManager;
    final private JwtUtils jwtUtils;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public ResponseDTO signUp(SignupDTO dto) {
        //Check if user already exists?
        if (userRepository.existsUserDomainByEmail(dto.getEmail())) {
            //TODO error handling
            return null;
        }
        // Create new user's account
        UserDomain user =
                UserDomain.builder()
                        .email(dto.getEmail())
                        .password(encoder.encode(dto.getPassword()))
                        .build();
        //Set USER role as default for any registration
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRoles(roles);
        roles.add(userRole);
        userRepository.save(user);
        return new ResponseDTO("User registered successfully!");
    }

    public SignInResponseDTO signIn(SignInDTO dto) {
        UserDomain user = userRepository.findUserDomainByEmail(dto.getEmail());
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getId(), dto.getPassword()));
        } catch (Exception exception) {
            //TODO handle error
            return null;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        return SignInResponseDTO.builder()
                .email(user.getEmail())
                .token(jwt)
                .build();
    }
}
