package backend.server.salendar.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"2. Sale"})
@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:8081")
@RequestMapping("/api/sale")
public class SaleController {

//    세일 전체 리스트 (썸네일)


//    월별 캘린더 (제목, 기간)


//    찜한 매장 캘린더


//    각 세일 정보
}
