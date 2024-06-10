package io.mongock.driver.mongodb.springdata.v4.config;

import com.mongodb.*;
import io.mongock.api.config.*;
import io.mongock.driver.api.driver.*;
import io.mongock.driver.mongodb.springdata.v4.*;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.core.*;

public abstract class SpringDataMongoV4ContextBase<CONFIG extends MongockConfiguration, DRIVER extends SpringDataMongoV4DriverBase> {

  @Bean
  public ConnectionDriver connectionDriver(MongoTemplate mongoTemplate,
                                           CONFIG config,
                                           MongoDBConfiguration mongoDbConfig) {
    DRIVER driver = buildDriver(mongoTemplate, config, mongoDbConfig);
    setGenericDriverConfig(config, driver);
    setMongoDBConfig(mongoDbConfig, driver);
    driver.initialize();
    return driver;
  }

  protected abstract DRIVER buildDriver(MongoTemplate mongoTemplate,
                                        CONFIG config,
                                        MongoDBConfiguration mongoDbConfig);

  private void setGenericDriverConfig(CONFIG config,
                                      DRIVER driver) {
    if (config.getTransactionEnabled().orElse(true)) {
      driver.enableTransaction();
    }
    driver.setMigrationRepositoryName(config.getMigrationRepositoryName());
    driver.setLockRepositoryName(config.getLockRepositoryName());
    driver.setIndexCreation(config.isIndexCreation());
  }

  private void setMongoDBConfig(MongoDBConfiguration mongoDbConfig, DRIVER driver) {
    driver.setWriteConcern(mongoDbConfig.getBuiltMongoDBWriteConcern());
    driver.setReadConcern(new ReadConcern(mongoDbConfig.getReadConcern()));
    driver.setReadPreference(mongoDbConfig.getReadPreference().getValue());
  }


}
