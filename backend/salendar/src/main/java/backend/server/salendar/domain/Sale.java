package backend.server.salendar.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Date;


/*
세일정보
Json
{
    "saleNo": 1,
    "saleTitle": String,
    "saleStore": Integer,
    "saleLink": String,
    "saleDsc": String,
    "saleBigImg": String,
    "saleThumbnail": String,
    "saleStartDate": Date,
    "saleEndDate": Date
}
*/
@Entity(name = "Sale")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Sale {

    // 세일 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("sale_no")
    private Long saleNo;

    // 링크
    @JsonProperty("sale_link")
    private String saleLink;

    // 이미지
    @JsonProperty("sale_big_img")
    private String saleBigImg;

    // 썸네일
    @JsonProperty("sale_thumbnail")
    private String saleThumbnail;

    // 세일 제목
    @JsonProperty("sale_title")
    private String saleTitle;

    // 세일 설명
    @JsonProperty("sale_dsc")
    private String saleDsc;

    // 시작 시간
    @JsonProperty("sale_start_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date saleStartDate;

    // 종료 시간
    @JsonProperty("sale_end_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date saleEndDate;

    // 매장
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "store_no")
    private Store store;
}