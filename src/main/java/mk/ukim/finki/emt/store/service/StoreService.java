package mk.ukim.finki.emt.store.service;

import mk.ukim.finki.emt.store.model.Category;
import mk.ukim.finki.emt.store.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface StoreService {

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findAllProducts();

    List<Product> searchProducts(String query);

    Product createProduct(String name, String description, String categoryId, MultipartFile picture) throws IOException, SQLException ;

    Category createCategoryByName(String categoryName);

    Category deleteCategory(Long id);

    List<Category> findAllCategories();

    Product findProductById(Long productId);
}
