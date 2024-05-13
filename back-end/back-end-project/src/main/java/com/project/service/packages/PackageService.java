package com.project.service.packages;

import com.project.model.Package;

import java.util.List;

public interface PackageService {
  Package savePackage(String name, Integer price, String description);
  Package updatePackage(Integer id, String name, Integer price, String description);
  boolean deletePackage(Integer id);
  Package findById(Integer id);
  List<Package> findAll();
}
