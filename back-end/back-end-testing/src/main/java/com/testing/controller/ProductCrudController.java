package com.testing.controller;

import com.testing.controller.path.TestingPath;
import com.testing.model.Product;
import com.testing.model.request.ProductRequest;
import com.testing.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = TestingPath.PRODUCT)
public class ProductCrudController {

  private final ProductService productService;

  @PostMapping(TestingPath.CREATE)
  public Product saveProducts(@RequestBody ProductRequest productRequest) throws Exception {
    return productService.saveProduct(productRequest);
  }

  @GetMapping(TestingPath.FIND_ALL)
  public List<Product> getProduct() throws Exception {
    return productService.findAll();
  }

  @DeleteMapping(TestingPath.DELETE)
  public boolean deleteProducts(String id) throws Exception {
    return productService.deleteProduct(id);
  }
}
