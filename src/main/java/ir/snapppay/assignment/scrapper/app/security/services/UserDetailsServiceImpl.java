package ir.snapppay.assignment.scrapper.app.security.services;


import ir.snapppay.assignment.scrapper.user.model.UserDomain;
import ir.snapppay.assignment.scrapper.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    final
    UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserDomain user = userRepository.findUserDomainById(userId);
        if (user == null)
            throw new UsernameNotFoundException("User Not Found!" );

        return UserDetailsImpl.build(user);
    }

}
