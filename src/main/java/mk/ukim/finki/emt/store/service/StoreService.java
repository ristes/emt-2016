package mk.ukim.finki.emt.store.service;

import mk.ukim.finki.emt.store.model.Category;
import mk.ukim.finki.emt.store.model.Product;

import java.util.List;

public interface StoreService {

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findAllProducts();

    List<Product> searchProducts(String query);

    Product createProduct(String name, String description, String categoryId);

    Category createCategoryByName(String categoryName);

    Category deleteCategory(Long id);

    List<Category> findAllCategories();

}
