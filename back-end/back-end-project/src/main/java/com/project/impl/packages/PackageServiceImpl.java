package com.project.impl.packages;

import com.project.helper.IdHelper;
import com.project.model.Package;
import com.project.repository.PackageRepository;
import com.project.service.packages.PackageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
@Slf4j
public class PackageServiceImpl implements PackageService {

  @Autowired
  private PackageRepository packageRepository;

  @Autowired
  private IdHelper idHelper;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public Package savePackage(String name, Integer price, String description) {
    Package pack = new Package();
    pack.setId(idHelper.getNextSequenceId(Package.COLLECTION_NAME));
    pack.setName(name);
    pack.setPrice(price);
    pack.setDescription(this.generateDescription(description));
    pack.setIsDeleted(0);
    return this.packageRepository.save(pack);
  }

  @Override
  public Package updatePackage(Integer id, String name, Integer price, String description) {
    return this.updatePackage(id, name, price, this.generateDescription(description));
  }

  @Override
  public boolean deletePackage(Integer id) {
    Package pack = this.packageRepository.findByIdAndIsDeleted(id, 0);
    if (Objects.isNull(pack)) {
      return false;
    }
    this.deletePackageById(id);
    return true;
  }

  @Override
  public Package findById(Integer id) {
    return this.packageRepository.findByIdAndIsDeleted(id, 0);
  }

  @Override
  public List<Package> findAll() {
    return this.packageRepository.findByIsDeleted(0);
  }

  private void deletePackageById(Integer id){
    Query query = new Query(
        where("_id").is(id));
    Update update = new Update().set("isDeleted", 1);

    this.mongoTemplate.updateMulti(query, update, Package.class);
  }

  private List<String> generateDescription(String description) {
    List<String> descriptions = new ArrayList<>();
    String[] splits = description.split(";");
    for (String split : splits) {
      descriptions.add(split);
    }
    return descriptions;
  }

  private Package updatePackage(Integer id, String name, Integer price, List<String> description) {
    Query query = new Query(where("_id").is(id));
    Update update = new Update().set("name", name).set("price", price).set("description", description);
    this.mongoTemplate.updateMulti(query, update, Package.class);
    return this.packageRepository.findByIdAndIsDeleted(id, 0);
  }
}
