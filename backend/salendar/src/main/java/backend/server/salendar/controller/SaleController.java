package backend.server.salendar.controller;

import backend.server.salendar.domain.Sale;
import backend.server.salendar.domain.Store;
import backend.server.salendar.domain.User;
import backend.server.salendar.repository.SaleRepository;
import backend.server.salendar.repository.StoreRepository;
import backend.server.salendar.security.JwtTokenProvider;
import backend.server.salendar.service.SaleService;
import backend.server.salendar.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"2. Sale"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sale")
public class SaleController {
    @Autowired
    SaleService saleService;
    @Autowired
    UserService userService;
    private final SaleRepository saleRepository;
    private final StoreRepository storeRepository;

    //    세일 DB 업데이트
    @ApiOperation(value = "세일 DB 업데이트")
    @PutMapping(value = "/updateDB")
    public ResponseEntity<String> updateSaleDB() {
        try {
            saleService.crawlAll();
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

//    세일 업데이트 스케쥴러
    @Scheduled(cron = "0 30 6 * * *", zone = "Asia/Seoul")
    public void updateSaleDBAuto() {
        try {
            saleService.crawlAll();
        } catch (Exception ignored) {
        }
    }

    //    세일 전체 리스트
    @Transactional
    @ApiOperation(value = "세일 전체 리스트", notes = "카드 형태 페이지, 전체 반환")
    @GetMapping(value = "/list")
    public ResponseEntity<Map<String, List<Sale>>> saleList() {
        Map<String, List<Sale>> result = saleService.findSalesByStores();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    //    찜한 매장 리스트
    @Transactional
    @ApiOperation(value = "팔로우 중인 매장의 세일 리스트")
    @GetMapping(value = "/token/list/follow")
    public ResponseEntity<Map<String, List<Sale>>> saleFollowingList(HttpServletRequest request) {
        User user = userService.findByToken(JwtTokenProvider.resolveToken(request));
        Map<String, List<Sale>> result = saleService.findSalesByFollowingStores(user);
        if (result.isEmpty()) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    //    각 세일 정보
    @ApiOperation(value = "세일 상세 정보")
    @GetMapping(value = "/{saleNo}")
    public ResponseEntity<Sale> saleDetail(@PathVariable("saleNo") Long saleNo) {
        return new ResponseEntity<>(saleRepository.findBySaleNo(saleNo), HttpStatus.OK);
    }

    //    전체 매장 리스트
    @Transactional
    @ApiOperation(value = "전체 매장 리스트")
    @GetMapping(value = "/storelist")
    public List<Map<String, String>> storeList() {
        List<Map<String, String>> result = new ArrayList<>();
        List<Store> stores = storeRepository.findAll();
        for (Store store : stores) {
            Map <String, String> temp = new HashMap<>();
            temp.put("storeName", store.getStoreName());
            temp.put("storeLogo", store.getStoreLogo());
            result.add(temp);
        }
        return result;
    }
}
