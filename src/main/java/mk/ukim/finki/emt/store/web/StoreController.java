package mk.ukim.finki.emt.store.web;

import mk.ukim.finki.emt.store.model.Category;
import mk.ukim.finki.emt.store.model.Product;
import mk.ukim.finki.emt.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by slobodanka on 2/17/2016.
 */
@Controller
public class StoreController {

  @Autowired
  StoreService service;

  @RequestMapping(value = { "/user", "/me" },produces = "application/json")
  @ResponseBody
  public Map<String, String> user(Principal principal) {
    Map<String, String> map = new LinkedHashMap();
    map.put("name", principal.getName());
    return map;
  }

  @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
  public String login(Model model, HttpSession session) {
    if (session.getAttribute("user") != null) {
      return "redirect:/";
    }
    model.addAttribute("pageFragment", "login");
    return "index";
  }

  @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
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

  @RequestMapping(value = {"/search"}, method = RequestMethod.GET)
  public String search(@RequestParam String query,
                       Model model) {
    List<Product> products;
    if (query.trim().isEmpty()) {
      products = service.findAllProducts();
    } else {
      products = service.searchProducts(query);
    }
    model.addAttribute("products", products);
    return "index";
  }

  @RequestMapping(value = {"/admin/category"}, method = RequestMethod.GET)
  public String addCategory(Model model) {
    model.addAttribute("pageFragment", "addCategory");
    return "index";
  }

  @RequestMapping(value = {"/admin/product"}, method = RequestMethod.GET)
  public String addProduct(Model model) {
    model.addAttribute("pageFragment", "addProduct");
    return "index";
  }

  @RequestMapping(value = {"/admin/product"}, method = RequestMethod.POST)
  public String createProduct(HttpServletRequest request,
                              HttpServletResponse resp,
                              Model model,
                              @RequestParam String name,
                              @RequestParam String description,
                              @RequestParam String categoryName) {
    Product product = service.createProduct(name, description, categoryName);
    model.addAttribute("product", product);
    return "index";
  }

  @RequestMapping(value = {"/admin/category"}, method = RequestMethod.POST)
  public String createCategory(HttpServletRequest request,
                               HttpServletResponse resp,
                               Model model,
                               @RequestParam String categoryName) {
    Category category = service.createCategoryByName(categoryName);
    List<Product> products;

    products = service.findByCategoryId(category.id);

    List<Category> categories = service.findAllCategories();
    model.addAttribute("category", category);
    model.addAttribute("products", products);
    // because there is a new category, so we have to override the category
    // request param from the filter
    model.addAttribute("categories", categories);
    return "index";
  }

  @RequestMapping(value = {"/admin/category/{id}/delete"}, method = RequestMethod.GET)
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
