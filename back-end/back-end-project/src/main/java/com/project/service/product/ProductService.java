package com.project.service.product;

import com.project.model.Product;
import com.project.model.request.ProductRequest;

import java.util.List;

public interface ProductService {
  Product saveProduct(ProductRequest productRequest);
  List<Product> findAll();
  boolean deleteProduct(String name);
}
