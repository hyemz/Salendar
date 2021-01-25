package backend.server.salendar.config;

import backend.server.salendar.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import backend.server.salendar.repository.UserRepository;
import backend.server.salendar.service.UserService;

@Configuration
public class SpringConfig {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    @Autowired
    public SpringConfig(UserRepository userRepository, StoreRepository storeRepository) {
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository, storeRepository);
    }
}
