package backend.server.salendar.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.security.Timestamp;

// 세일 정보
@Entity(name="Sale")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale {

    // 세일 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("sale_no")
    private Integer saleNo;

    // 링크
    @JsonProperty("sale_link")
    private String saleLink;

    // 이미지
    @JsonProperty("sale_big_img")
    private String saleBigImg;

    // 썸네일
    @JsonProperty("sale_thumbnail")
    private String saleThumbnail;

    // 세일 이름
    @JsonProperty("sale_name")
    private String saleName;

    // 시작 시간
    @JsonProperty("sale_start_date")
    private Timestamp saleStartDate;

    // 종료 시간
    @JsonProperty("sale_end_date")
    private Timestamp saleEndDate;

    // 매장
    @JsonProperty("sale_store")
    @ManyToOne
    @JoinColumn(name="store_no")
    private Store store;

    // Sale 모델 복사
    public void CopyData(Sale param)
    {
        this.saleNo = param.getSaleNo();
        this.saleLink = param.getSaleLink();
        this.saleBigImg = param.getSaleBigImg();
        this.saleThumbnail = param.getSaleThumbnail();
        this.saleName = param.getSaleName();
        this.saleStartDate = param.getSaleStartDate();
        this.saleEndDate = param.getSaleEndDate();
    }
}