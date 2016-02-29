package mk.ukim.finki.emt.store.repository;

import mk.ukim.finki.emt.store.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryRepository extends JpaRepository<Category, Long>,
  JpaSpecificationExecutor<Category> {

  Category findByName(String categoryName);
}
