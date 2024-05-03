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
  public Package savePackage(String name) {
    Package pack = new Package();
    pack.setId(idHelper.getNextSequenceId(Package.COLLECTION_NAME));
    pack.setName(name);
    pack.setIsDeleted(0);
    return this.packageRepository.save(pack);
  }

  @Override
  public Package updatePackage(Integer id, String name) {
    Query query = new Query(where("_id").is(id));
    Update update = new Update().set("name", name);
    this.mongoTemplate.updateMulti(query, update, Package.class);
    return this.packageRepository.findByIdAndIsDeleted(id, 0);
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

  private void deletePackageById(Integer id){
    Query query = new Query(
        where("_id").is(id));
    Update update = new Update().set("isDeleted", 1);

    this.mongoTemplate.updateMulti(query, update, Package.class);
  }
}
