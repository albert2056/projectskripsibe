package com.project.service.packages;

import com.project.model.Package;

public interface PackageService {
  Package savePackage(String name, Integer price);
  Package updatePackage(Integer id, String name, Integer price);
  boolean deletePackage(Integer id);
  Package findById(Integer id);
}
