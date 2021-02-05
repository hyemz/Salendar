package backend.server.salendar.service;

import backend.server.salendar.domain.Sale;
import backend.server.salendar.domain.User;
import backend.server.salendar.repository.UserRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class EmailService {

    private final UserRepository userRepository;
    private SaleService saleService;
    private JavaMailSender javaMailSender;
    private TemplateEngine templateEngine;

    public EmailService(UserRepository userRepository, SaleService saleService, JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.userRepository = userRepository;
        this.saleService = saleService;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendMail(User user) {
//        Map<String, List<Sale>> sales = saleService.findSalesByFollowingStores(user);
        Map<String, List<Sale>> salesMap = saleService.findSalesByStores();
        MimeMessagePreparator message = mimeMessage -> {
            String content = buildSales(salesMap);

            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(user.getUsrEmail());
            Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
            String month = String.valueOf(cal.get(Calendar.MONTH));
            messageHelper.setSubject(month + "월의 세일입니다.");
            messageHelper.setText(content, true);
        };
        javaMailSender.send(message);
    }

    private String buildSales(Map<String, List<Sale>> salesMap) {
        Context context = new Context();
        Map<String, String> restSales = new HashMap<>();
        Map<String, Sale> resultSales = new HashMap<>();
        for (String storeName : salesMap.keySet()) {
            String salesDsc = "";
            for (Sale sale : salesMap.get(storeName)) {
                salesDsc += sale.getSaleTitle() + (salesMap.get(storeName).indexOf(sale) != salesMap.get(storeName).size() ? ", " : "");
            }
            restSales.put(storeName, salesDsc);

            resultSales.put("storeName", salesMap.get(storeName).get(0));
        }
        context.setVariable("salesMap", resultSales);
        context.setVariable("salesDsc", restSales);
        return templateEngine.process("page/mail", context);
    }

//    /*
//     * 알림 메일 전송
//     */
//    @Transactional
//    public void mailAlertSale() {
//        List<User> users = userRepository.findAll();
//        users.stream()
//                .forEach(user -> {
//
//                });
//    }
}
