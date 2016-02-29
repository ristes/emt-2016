package mk.ukim.finki.emt.store.web;

import mk.ukim.finki.emt.store.model.Category;
import mk.ukim.finki.emt.store.model.Product;
import mk.ukim.finki.emt.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by slobodanka on 2/17/2016.
 */
@Controller
public class StoreController {

  @Autowired
  StoreService service;

  @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
  public String index(HttpServletRequest request,
                      HttpServletResponse resp,
                      Model model,
                      @RequestParam(required = false) Long categoryId) {
    List<Product> products;
    if (categoryId != null) {
      products = service.findByCategoryId(categoryId);
    } else {
      products = service.findAllProducts();
    }
    model.addAttribute("products", products);
    return "index";
  }

  @RequestMapping(value = {"/category"}, method = RequestMethod.POST)
  public String createCategory(HttpServletRequest request,
                               HttpServletResponse resp,
                               Model model,
                               @RequestParam String categoryName) {
    Category category = service.createCategoryByName(categoryName);
    List<Product> products;

    products = service.findByCategoryId(category.id);

    model.addAttribute("category", category);
    model.addAttribute("products", products);
    return "index";
  }

  @RequestMapping(value = {"/category/{id}/delete"}, method = RequestMethod.GET)
  public String deleteCategory(Model model,
                               @PathVariable Long id) {
    try {
      Category category = service.deleteCategory(id);
      List<Product> products = null;

      if (category != null) {
        products = service.findByCategoryId(category.id);
      }

      model.addAttribute("category", category);
      model.addAttribute("products", products);
    } catch (IllegalStateException e) {
      model.addAttribute("exception", e.getMessage());
    }


    return "index";
  }

}
