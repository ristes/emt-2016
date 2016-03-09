package mk.ukim.finki.emt.store.repository;

import mk.ukim.finki.emt.store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>,
        JpaSpecificationExecutor<Product> {

    List<Product> findByCategoryId(Long categoryId);

    Product findByName(String name);
}
