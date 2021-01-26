package backend.server.salendar.repository;

import backend.server.salendar.domain.Sale;
import backend.server.salendar.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findSalesByStore(Store store);

    Sale findBySaleNo(Long saleNo);
}
