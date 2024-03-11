package com.project.impl.roles;

import com.project.helper.IdHelper;
import com.project.model.Roles;
import com.project.repository.RolesRepository;
import com.project.service.roles.RolesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RolesServiceImpl implements RolesService {

  @Autowired
  private RolesRepository rolesRepository;

  @Autowired
  private IdHelper idHelper;

  @Override
  public Roles saveRole(String name) {
    Roles role = new Roles();
    role.setId(idHelper.getNextSequenceId(Roles.COLLECTION_NAME));
    role.setName(name);
    return rolesRepository.save(role);
  }
}
