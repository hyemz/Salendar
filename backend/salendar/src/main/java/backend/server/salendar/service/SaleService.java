package backend.server.salendar.service;

import backend.server.salendar.domain.Sale;
import backend.server.salendar.domain.Store;
import backend.server.salendar.domain.User;
import backend.server.salendar.repository.SaleRepository;
import backend.server.salendar.repository.StoreRepository;
import backend.server.salendar.repository.UserRepository;
import lombok.SneakyThrows;

import javax.transaction.Transactional;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class SaleService {
    private final StoreRepository storeRepository;
    private final SaleRepository saleRepository;
    private final UserRepository userRepository;

    public SaleService(StoreRepository storeRepository, SaleRepository saleRepository, UserRepository userRepository) {
        this.storeRepository = storeRepository;
        this.saleRepository = saleRepository;
        this.userRepository = userRepository;
    }

    @SneakyThrows
    public void crawlAll() {
        Class[] noParam = {};
        Class cls = Class.forName("backend.server.salendar.util.Crawler");
        Object obj = cls.newInstance();

        Stream<Store> stores = storeRepository.findAll().stream();

        stores.forEach(store -> {
            try {
                Method method = cls.getDeclaredMethod("crawl" + store.getStoreName(), noParam);
                List<Sale> sales = (List<Sale>) method.invoke(obj, null);
                sales.stream()
                        .filter(sale -> saleFilter(sale))
                        .forEach(sale -> {
                            sale.setStore(store);
                            saleRepository.save(sale);
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @SneakyThrows
    public Boolean saleFilter(Sale sale) {
//      기간 필터링: 기간이 10일 이하
        var forPeriod = true;

        if (sale.getSaleEndDate() != null) {
            long eventPeriod = sale.getSaleEndDate().getTime() - sale.getSaleStartDate().getTime();
            long eventPeriodInDays = eventPeriod / (24 * 60 * 60 * 1000);

            if (eventPeriodInDays > 15) {
                forPeriod = false;
            }
        }

//      내용 필터링
        var forContent = true;
//      1. % 확인
        String saleDsc = sale.getSaleDsc();
        int index = saleDsc.indexOf("%");
        if (index != -1) {
            String percentage = saleDsc.substring(index - 2, index);
            int per = Integer.parseInt(percentage);
            if (per == 00 || per > 70) {
                forContent = false;
            }
        }

//       2. 불필요 단어 찾기
        if (forContent) {
            List<String> donIncludeWords = Arrays.asList("카드", "출석", "출첵", "LIVE", "쿠폰");
            List<Pattern> patterns = new ArrayList<>();
            for (String word : donIncludeWords) {
                patterns.add(Pattern.compile("(?m)" + word));
            }
            for (Pattern pattern : patterns) {
                System.out.println(pattern.toString() + " " + sale.getSaleTitle());
                if (pattern.matcher(sale.getSaleTitle()).find()
                        || pattern.matcher(sale.getSaleDsc()).find()) {
                    forContent = false;
                    break;
                }
            }
        }
        return forPeriod && forContent;
    }

    @Transactional
    public Map<String, List<Sale>> findSalesByFollowingStores(User user) {
        Map<String, List<Sale>> result = new HashMap<>();
        user.getUsrFollowing()
                .stream()
                .forEach(store -> {
                    System.out.println(store);
                    result.put(store.getStoreName(), new ArrayList<>());
                    saleRepository.findSalesByStore(store)
                            .forEach(res -> result.get(store.getStoreName()).add(res));
                });
        return result;
    }
}
