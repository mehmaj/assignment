package ir.snapppay.assignment.scrapper.user.repository;


import ir.snapppay.assignment.scrapper.user.model.ERole;
import ir.snapppay.assignment.scrapper.user.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
    boolean existsRoleByName(ERole name);
}
