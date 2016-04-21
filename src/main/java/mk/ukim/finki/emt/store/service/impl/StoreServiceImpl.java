package mk.ukim.finki.emt.store.service.impl;

import mk.ukim.finki.emt.store.model.Category;
import mk.ukim.finki.emt.store.model.Product;
import mk.ukim.finki.emt.store.repository.CategoryRepository;
import mk.ukim.finki.emt.store.repository.ProductRepository;
import mk.ukim.finki.emt.store.repository.SearchRepository;
import mk.ukim.finki.emt.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  ProductRepository productRepository;

  @Autowired
  SearchRepository searchRepository;

  @Override
  public List<Product> findByCategoryId(Long categoryId) {
    return productRepository.findByCategoryId(categoryId);
  }

  @Override
  public List<Product> findAllProducts() {
    return productRepository.findAll();
  }

  @Override
  public List<Product> searchProducts(String query) {
    return searchRepository.searchPhrase(
      Product.class,
      query,
      "name",
      "description",
      "category.name");
  }

  @Override
  public Category createCategoryByName(String categoryName) {
    Category category = categoryRepository.findByName(categoryName);
    if (category == null) {
      category = new Category();
      category.name = categoryName;
      categoryRepository.save(category);
    }
    return category;
  }

  @Override
  public Category deleteCategory(Long id) {
    Category category = null;
    try {
      category = categoryRepository.findOne(id);
      categoryRepository.delete(category);
      return category;
    } catch (DataIntegrityViolationException e) {
      e.printStackTrace();
      throw new IllegalStateException("Can't delete category with products. Delete the products first!");
    } catch (Exception e) {
      e.printStackTrace();
      return category;
    }
  }

  @Override
  public Product createProduct(String name, String description, String categoryName, MultipartFile picture) throws IOException, SQLException {
    Product product = productRepository.findByName(name);
    if (product == null) {
      Blob pictureBlob = null;
      if (picture != null && "image/png".equals(picture.getContentType())) {
        pictureBlob = new SerialBlob(picture.getBytes());
      }
      product = new Product();
      product.name = name;
      product.description = description;
      Category category = categoryRepository.findByName(categoryName);
      product.category = category;
      product.picture = pictureBlob;
      productRepository.save(product);
    }
    return product;
  }

  @Override
  public List<Category> findAllCategories() {
    return categoryRepository.findAll();
  }

  @Override
  public Product findProductById(Long productId) {
    return productRepository.findOne(productId);
  }

}
