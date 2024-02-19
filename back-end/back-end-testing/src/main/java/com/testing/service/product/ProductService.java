package com.testing.service.product;

import com.testing.model.Product;
import com.testing.model.request.ProductRequest;

import java.util.List;

public interface ProductService {
  Product saveProduct(ProductRequest productRequest);
  List<Product> findAll();
  boolean deleteProduct(String name);
}
