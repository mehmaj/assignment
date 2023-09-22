package ir.snapppay.assignment.scrapper.app;

import ir.snapppay.assignment.scrapper.user.model.ERole;
import ir.snapppay.assignment.scrapper.user.model.Role;
import ir.snapppay.assignment.scrapper.user.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
//Staff that app is relied on, to run
public class StartupRunner implements CommandLineRunner {
    private final RoleRepository roleRepository;

    public StartupRunner(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        //Check if all user roles are existing
        for (ERole role : ERole.values()) {
            if (!roleRepository.existsRoleByName(role))
                roleRepository.save(new Role(role));
        }
    }
}
