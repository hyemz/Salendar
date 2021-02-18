package backend.server.salendar.service;

import backend.server.salendar.domain.Sale;
import backend.server.salendar.domain.Store;
import backend.server.salendar.domain.User;
import backend.server.salendar.repository.SaleRepository;
import backend.server.salendar.repository.StoreRepository;
import lombok.SneakyThrows;

import javax.transaction.Transactional;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

public class SaleService {
    private final StoreRepository storeRepository;
    private final SaleRepository saleRepository;

    public SaleService(StoreRepository storeRepository, SaleRepository saleRepository) {
        this.storeRepository = storeRepository;
        this.saleRepository = saleRepository;
    }

    @SneakyThrows
    public void crawlAll() {
        List<Sale> cursales = saleRepository.findAll();
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

        stores
                .filter(store -> !"Innisfree".equals(store.getStoreName()))
                .forEach(store -> {
                    try {
                        System.out.println(store.getStoreName());
                        Method method = cls.getDeclaredMethod("crawl" + store.getStoreName(), noParam);
                        List<Sale> sales = (List<Sale>) method.invoke(obj, null);
                        System.out.println("Store: " + store.getStoreName() + ", size: " + sales.size());
                        sales
                                .forEach(sale -> {
                                    saleRepository.findBySaleTitle(sale.getSaleTitle()).orElseGet(() -> {
                                        sale.setStore(store);
                                        if (sale.getSaleTitle().contains("]")) {
                                            sale.setSaleTitle(sale.getSaleTitle().substring(sale.getSaleTitle().indexOf("]") + 1).strip());
                                        }
                                        if (sale.getSaleDsc().contains("]")) {
                                            sale.setSaleDsc(sale.getSaleDsc().substring(sale.getSaleDsc().indexOf("]") + 1).strip());
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
