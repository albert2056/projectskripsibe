package com.testing.impl.product;

import com.testing.model.Product;
import com.testing.model.request.ProductRequest;
import com.testing.repository.ProductRepository;
import com.testing.service.product.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

  @Autowired
  private ProductRepository productRepository;

  @Override
  public Product saveProduct(ProductRequest productRequest) {
    Product product = new Product();
    product.setName(productRequest.getName());
    product.setPrice(productRequest.getPrice());
    productRepository.save(product);
    return product;
  }

  @Override
  public List<Product> findAll() {
    return productRepository.findAll();
  }

  @Override
  public boolean deleteProduct(String id) {
    Product product = productRepository.findById(id).orElse(null);
    if (Objects.isNull(product)) {
      return false;
    }
    productRepository.delete(product);
    return true;
  }
}
