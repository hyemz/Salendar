package backend.server.salendar.service;

import backend.server.salendar.domain.Sale;
import backend.server.salendar.domain.Store;
import backend.server.salendar.domain.User;
import backend.server.salendar.repository.SaleRepository;
import backend.server.salendar.repository.StoreRepository;
import backend.server.salendar.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Scheduled;

import javax.transaction.Transactional;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
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
//    @Scheduled(cron = "0 30 6 * * *", zone = "Asia/Seoul")
    public void crawlAll() {
        List<Sale> cursales = saleRepository.findAll();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul")).getTime();
        for (Sale sale : cursales) {
            if (Math.abs((sale.getSaleEndDate().getTime() - today.getTime()) / (24 * 60 * 60 * 1000)) > 365) {
                saleRepository.delete(sale);
            }
        }

        Class[] noParam = {};
        Class cls = Class.forName("backend.server.salendar.util.Crawler");
        Object obj = cls.newInstance();

        Stream<Store> stores = storeRepository.findAll().stream();

        Pattern pattern = Pattern.compile("(?m)^(\\[(.*?)\\])");

        stores.forEach(store -> {
            try {
                Method method = cls.getDeclaredMethod("crawl" + store.getStoreName(), noParam);
                List<Sale> sales = (List<Sale>) method.invoke(obj, null);
                System.out.println("Store: " + store.getStoreName() + ", size: " + sales.size());
                sales.stream()
                        .forEach(sale -> {
                            saleRepository.findBySaleTitle(sale.getSaleTitle()).orElseGet(() -> {
                                sale.setStore(store);
                                if (pattern.matcher(sale.getSaleTitle()).find()) {
                                    String newTitle = pattern.matcher(sale.getSaleTitle()).group();
                                    sale.setSaleTitle(newTitle);
                                }
                                if (pattern.matcher(sale.getSaleDsc()).find()) {
                                    String newDsc = pattern.matcher(sale.getSaleDsc()).group();
                                    sale.setSaleDsc(newDsc);
                                }
                                if (sale.getSaleBigImg().strip().length() < 5) {
                                    sale.setSaleBigImg(sale.getSaleThumbnail());
                                }
                                saleRepository.save(sale);
                                System.out.println(sale);
                                return sale;
                            });
                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Transactional
    public Map<String, List<Sale>> findSalesByFollowingStores(User user) {
        Map<String, List<Sale>> result = new HashMap<>();
        user.getUsrFollowing()
                .stream()
                .forEach(store -> {
                    if (!saleRepository.findSalesByStore(store).isEmpty()) {
                        result.put(store.getStoreName(), new ArrayList<>());
                        saleRepository.findSalesByStore(store)
                                .forEach(res -> result.get(store.getStoreName()).add(res));
                    }
                });
        return result;
    }

    @Transactional
    public Map<String, List<Sale>> findSalesByStores() {
        Map<String, List<Sale>> result = new HashMap<>();
        storeRepository.findAll()
                .stream()
                .forEach(store -> {
                    if (!saleRepository.findSalesByStore(store).isEmpty()) {
                        result.put(store.getStoreName(), new ArrayList<>());
                        saleRepository.findSalesByStore(store)
                                .forEach(res -> result.get(store.getStoreName()).add(res));
                    }
                });
        return result;
    }
}
