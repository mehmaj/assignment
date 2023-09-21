package ir.snapppay.assignment.scrapper.user.service;

import ir.snapppay.assignment.scrapper.user.model.ERole;
import ir.snapppay.assignment.scrapper.user.model.Role;
import ir.snapppay.assignment.scrapper.user.model.UserDomain;
import ir.snapppay.assignment.scrapper.user.model.dto.ResponseDTO;
import ir.snapppay.assignment.scrapper.user.model.dto.SignupDTO;
import ir.snapppay.assignment.scrapper.user.repository.RoleRepository;
import ir.snapppay.assignment.scrapper.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public ResponseDTO signup(SignupDTO dto) {
        //Check if user already exists?
        if (userRepository.existsUserDomainByEmail(dto.getEmail())) {
            //TODO error handling
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
        //Save created user
        userRepository.save(user);
        return new ResponseDTO("User registered successfully!");
    }
}
