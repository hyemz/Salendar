package backend.server.salendar.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 매장 정보
@Entity(name="store")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {

    // 매장 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("store_no")
    private Integer storeNo;

    // 매장 이름
    @JsonProperty("store_name")
    private String storeName;

    // 세일
    @JsonProperty("store_sales")
    @OneToMany(mappedBy = "store")
    private List<Sale> storeSales = new ArrayList<Sale>();

    // 로고
    @JsonProperty("store_logo")
    private String storeLogo;

    // Store 모델 복사
    public void CopyData(Store param)
    {
        this.storeNo = param.getStoreNo();
        this.storeName = param.getStoreName();
        this.storeSales = param.getStoreSales();
        this.storeLogo = param.getStoreLogo();
    }
}