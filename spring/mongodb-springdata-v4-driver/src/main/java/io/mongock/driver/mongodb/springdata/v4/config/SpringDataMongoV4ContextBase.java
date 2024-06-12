package io.mongock.driver.mongodb.springdata.v4.config;

import com.mongodb.*;
import io.mongock.api.config.*;
import io.mongock.driver.api.driver.*;
import io.mongock.driver.mongodb.springdata.v4.*;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.transaction.*;

import java.util.*;

public abstract class SpringDataMongoV4ContextBase<CONFIG extends MongockConfiguration, DRIVER extends SpringDataMongoV4DriverBase> {

  @Bean
  public ConnectionDriver connectionDriver(MongoTemplate mongoTemplate,
                                           CONFIG config,
                                           MongoDBConfiguration mongoDbConfig,
                                           Optional<PlatformTransactionManager> txManagerOpt) {
    DRIVER driver = buildDriver(mongoTemplate, config, mongoDbConfig, txManagerOpt);
    setGenericDriverConfig(config, txManagerOpt, driver);
    setMongoDBConfig(mongoDbConfig, driver);
    driver.initialize();
    return driver;
  }

  protected abstract DRIVER buildDriver(MongoTemplate mongoTemplate,
                                        CONFIG config,
                                        MongoDBConfiguration mongoDbConfig,
                                        Optional<PlatformTransactionManager> txManagerOpt);

  private void setGenericDriverConfig(CONFIG config,
                                      Optional<PlatformTransactionManager> txManagerOpt,
                                      DRIVER driver) {
    txManagerOpt.filter(tx -> config.getTransactionEnabled().orElse(true)).ifPresent(tx -> driver.enableTransaction());
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
