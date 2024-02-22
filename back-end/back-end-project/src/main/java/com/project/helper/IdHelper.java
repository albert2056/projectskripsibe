package com.project.helper;

import com.project.model.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Component
public class IdHelper {

  @Autowired
  private MongoTemplate mongoTemplate;

  public Integer getNextSequenceId(String sequenceName) {
    Sequence sequence = mongoTemplate.findAndModify(
        query(where("_id").is(sequenceName)),
        new Update().inc("sequenceValue", 1),
        options().returnNew(true).upsert(true),
        Sequence.class);
    return sequence != null ? sequence.getSequenceValue() : 1;
  }
}
