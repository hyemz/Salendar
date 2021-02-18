package backend.server.salendar.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 매장 정보
@Entity(name = "store")
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
    private Long storeNo;

    // 매장 이름
    @JsonProperty("store_name")
    private String storeName;

    // 세일
    @JsonProperty("store_sales")
    @OneToMany(mappedBy = "store")
    @JsonManagedReference
    private List<Sale> storeSales = new ArrayList<>();

    // 로고
    @JsonProperty("store_logo")
    private String storeLogo;

    //    팔로워
    @ManyToMany(mappedBy = "usrFollowing")
    //@JsonManagedReference(value = "user-following")
    private List<User> users = new ArrayList<>();
}