package backend.server.salendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import backend.server.salendar.repository.UserRepository;
import backend.server.salendar.service.UserService;

@Configuration
public class SpringConfig {

    @Autowired
    private final UserRepository userRepository;

    public SpringConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository);
    }
}
