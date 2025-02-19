package lucioggm.ecomerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import lucioggm.ecomerce.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Boolean existsByName(String name);

}
