package backend.server.salendar.service;

import backend.server.salendar.domain.Sale;
import backend.server.salendar.domain.User;
import backend.server.salendar.repository.UserRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class EmailService {

    private final UserRepository userRepository;
    private final SaleService saleService;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public EmailService(UserRepository userRepository, SaleService saleService, JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.userRepository = userRepository;
        this.saleService = saleService;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendMail(User user) {
        Map<String, List<Sale>> salesMap = saleService.findSalesByFollowingStores(user);
        if (!salesMap.isEmpty()) {
            MimeMessagePreparator message = mimeMessage -> {
                String content = buildSales(salesMap);
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                messageHelper.setTo(user.getUsrEmail());
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
                String month = String.valueOf(cal.get(Calendar.MONTH));
                messageHelper.setSubject(month + "월의 세일 소식을 전해드립니다!");
                messageHelper.setText(content, true);
            };
            javaMailSender.send(message);
        }
    }

    private String buildSales(Map<String, List<Sale>> salesMap) {
        Context context = new Context();
        List<String> storeNames = new ArrayList<>();
        List<Sale> sales = new ArrayList<>();
        List<String> saleDscs = new ArrayList<>();

        for (String storeName : salesMap.keySet()) {
            storeNames.add(storeName);
            StringBuilder salesDsc = new StringBuilder();
            for (Sale sale : salesMap.get(storeName)) {
                salesDsc.append(sale.getSaleTitle()).append(salesMap.get(storeName).indexOf(sale) != salesMap.get(storeName).size() - 1 ? ", " : "");
            }
            saleDscs.add(salesDsc.toString());

            sales.add(salesMap.get(storeName).get(0));
        }
        context.setVariable("storeNames", storeNames);
        context.setVariable("sales", sales);
        context.setVariable("saleDscs", saleDscs);
        return templateEngine.process("page/mail", context);
    }

    /*
     * 알림 메일 전송
     */
    @Transactional
    @Scheduled(cron = "0 0 9 1 * *", zone = "Asia/Seoul")
    public void mailAlertSale() {
        List<User> users = userRepository.findAll();
        users
                .forEach(user -> {
                    if (user.getUsrAlarm()) {
                        sendMail(user);
                    }
                });
    }
}
