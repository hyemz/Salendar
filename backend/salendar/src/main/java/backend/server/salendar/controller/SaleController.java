package backend.server.salendar.controller;

import backend.server.salendar.repository.SaleRepository;
import backend.server.salendar.service.SaleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"2. Sale"})
@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:8081")
@RequestMapping("/api/sale")
public class SaleController {
    @Autowired
    SaleService saleService;
    private final SaleRepository saleRepository;

    //    세일 DB 업데이트
    @ApiOperation(value = "세일 DB 업데이트", notes = "관리자")
    @PostMapping(value = "/updateDB")
    public ResponseEntity<String> updateSaleDB() {
        try {
            saleService.crawlAll();
        } catch (Exception e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
//    세일 전체 리스트 (썸네일)


//    월별 캘린더 (제목, 기간)


//    찜한 매장 캘린더


//    각 세일 정보
}
