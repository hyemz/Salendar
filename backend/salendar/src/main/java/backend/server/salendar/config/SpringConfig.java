package backend.server.salendar.config;

import backend.server.salendar.repository.BoardRepository;
import backend.server.salendar.repository.SaleRepository;
import backend.server.salendar.repository.StoreRepository;
import backend.server.salendar.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import backend.server.salendar.repository.UserRepository;
import backend.server.salendar.service.UserService;

@Configuration
public class SpringConfig {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final SaleRepository saleRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public SpringConfig(UserRepository userRepository, StoreRepository storeRepository, SaleRepository saleRepository, BoardRepository boardRepository) {
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
        this.saleRepository = saleRepository;
        this.boardRepository = boardRepository;
    }

    @Bean
    public UserService userService() {
        return new UserService(userRepository, storeRepository, boardRepository);
    }

    @Bean
    public SaleService saleService() {
        return new SaleService(storeRepository, saleRepository);
    }
}
