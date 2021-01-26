package backend.server.salendar.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// 매장 정보
@Entity(name = "store")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Proxy(lazy = false)
public class Store implements Serializable {

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
    @JsonManagedReference
    @OneToMany(mappedBy = "store", fetch = FetchType.EAGER)
    private List<Sale> storeSales = new ArrayList<Sale>();

    // 로고
    @JsonProperty("store_logo")
    private String storeLogo;

    //    팔로워
    @JsonManagedReference
    @ManyToMany(mappedBy = "usrFollowing", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<User>();

    // Store 모델 복사
    public void CopyData(Store param) {
        this.storeNo = param.getStoreNo();
        this.storeName = param.getStoreName();
        this.storeSales = param.getStoreSales();
        this.storeLogo = param.getStoreLogo();
    }
}