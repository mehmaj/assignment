package ir.snapppay.assignment.scrapper.user.repository;

import ir.snapppay.assignment.scrapper.user.model.UserDomain;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserDomain, String> {
    UserDomain findUserDomainById(String userId);
}
