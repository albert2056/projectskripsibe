package com.project.service.packages;

import com.project.model.Package;

public interface PackageService {
  Package savePackage(String name);
  Package updatePackage(Integer id, String name);
  boolean deletePackage(Integer id);
}
