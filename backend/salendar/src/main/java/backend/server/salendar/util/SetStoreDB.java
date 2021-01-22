package backend.server.salendar.util;

import backend.server.salendar.domain.Store;

import javax.persistence.EntityManager;
import java.util.Map;

/*
* 매장 정보 갱신 class
* 매장 정보를 갱신하고자 할때 클래스 개별 동작 util
*/
public class SetStoreDB {
    public void setStoreInfo(Map<String, String> storeInfo, EntityManager em){
        Store store = new Store();
        store.setStoreLogo(storeInfo.get("storeLogo"));
        store.setStoreName(storeInfo.get("storeName"));
        em.persist(store);
    }
    public static void main(String[] args) {

    }
}
