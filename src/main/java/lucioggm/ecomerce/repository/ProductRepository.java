package lucioggm.ecomerce.repository;

import lucioggm.ecomerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {


}
