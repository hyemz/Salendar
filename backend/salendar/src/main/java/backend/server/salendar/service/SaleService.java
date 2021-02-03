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
import java.util.regex.Matcher;
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
                System.out.println("Store: " + store.getStoreName() + ", size: " + sales.size());
                sales.stream()
                        .filter(sale -> saleFilter(sale))
                        .forEach(sale -> {
                            saleRepository.findBySaleTitle(sale.getSaleTitle()).orElseGet(() -> {
                                sale.setStore(store);
                                if (sale.getSaleBigImg() == null) {
                                    sale.setSaleBigImg(sale.getSaleThumbnail());
                                }
                                if (sale.getSaleEndDate() == null) {
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(sale.getSaleStartDate());
                                    cal.add(Calendar.YEAR, 1);
                                    sale.setSaleEndDate(cal.getTime());
                                }
                                saleRepository.save(sale);
                                return sale;
                            });
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @SneakyThrows
    public Boolean saleFilter(Sale sale) {


//      기간 필터링: 기간이 10일 이하
        if (sale.getSaleEndDate() != null) {
            long eventPeriod = sale.getSaleEndDate().getTime() - sale.getSaleStartDate().getTime();
            long eventPeriodInDays = eventPeriod / (24 * 60 * 60 * 1000);

            if (eventPeriodInDays > 15) {
                return false;
            }
        }


//      불필요 단어 + 과도한 % 필터링
        List<String> donIncludeWords = Arrays.asList("카드", "출석", "출첵", "LIVE", "쿠폰", "사은품", "증정");
        List<Pattern> patterns = new ArrayList<>();
        for (String word : donIncludeWords) {
            patterns.add(Pattern.compile("(?m)" + word));
        }
        for (Pattern pattern : patterns) {
            if (pattern.matcher(sale.getSaleTitle()).find()
                    || pattern.matcher(sale.getSaleDsc()).find()) {
                return false;
            }
        }

        List<Pattern> patterns2 = Arrays.asList(Pattern.compile("(?m)\\d*%"), Pattern.compile("(?m)\\d*.\\d*%"));
        for (Pattern pattern : patterns2) {
            Optional<Double> per = Optional.empty();
            Matcher matcher = pattern.matcher(sale.getSaleTitle());
            while (matcher.find()) {
                if (per.isPresent() && Double.parseDouble(matcher.group()) < per.get()) {
                    per = Optional.of(Double.parseDouble(matcher.group()));
                }
            }
            Matcher matcher2 = pattern.matcher(sale.getSaleDsc());
            while (matcher2.find()) {
                if (per.isPresent() && Double.parseDouble(matcher2.group()) < per.get()) {
                    per = Optional.of(Double.parseDouble(matcher2.group()));
                }
            }
        }
        return true;
    }

    @Transactional
    public Map<String, List<Sale>> findSalesByFollowingStores(User user) {
        Map<String, List<Sale>> result = new HashMap<>();
        user.getUsrFollowing()
                .stream()
                .forEach(store -> {
                    result.put(store.getStoreName(), new ArrayList<>());
                    saleRepository.findSalesByStore(store)
                            .forEach(res -> result.get(store.getStoreName()).add(res));
                });
        return result;
    }
}
