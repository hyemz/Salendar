package backend.server.salendar.service;


import backend.server.salendar.domain.User;
import backend.server.salendar.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
class EmailServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;

    @Test
    void sendMailTest() {
        Optional<User> user = userRepository.findByUsrEmail("dudrlf1859@naver.com");
        emailService.sendMail(user.get());
    }
}