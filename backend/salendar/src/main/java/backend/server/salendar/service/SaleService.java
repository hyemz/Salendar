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
                        .forEach(sale -> {
                            saleRepository.findBySaleTitle(sale.getSaleTitle()).orElseGet(() -> {
                                sale.setStore(store);
                                if (sale.getSaleBigImg().strip().length() < 5) {
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

    @Transactional
    public Map<String, List<Sale>> findSalesByStores() {
        Map<String, List<Sale>> result = new HashMap<>();
        storeRepository.findAll()
                .stream()
                .forEach(store -> {
                    result.put(store.getStoreName(), new ArrayList<>());
                    saleRepository.findSalesByStore(store)
                            .forEach(res -> result.get(store.getStoreName()).add(res));
                });
        return result;
    }
}
