package io.mongock.driver.mongodb.springdata.v4.config;

import io.mongock.api.config.*;
import io.mongock.driver.mongodb.springdata.v4.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.context.properties.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.transaction.*;

import java.util.*;

@AutoConfiguration
@ConditionalOnExpression("${mongock.enabled:true}")
@ConditionalOnBean(MongockConfiguration.class)
@EnableConfigurationProperties(MongoDBConfiguration.class)
public class SpringDataMongoV4Context extends SpringDataMongoV4ContextBase<MongockConfiguration, SpringDataMongoV4Driver> {

  @Override
  protected SpringDataMongoV4Driver buildDriver(MongoTemplate mongoTemplate,
                                                MongockConfiguration config,
                                                MongoDBConfiguration mongoDbConfig,
                                                Optional<PlatformTransactionManager> txManagerOpt) {
    return SpringDataMongoV4Driver.withLockStrategy(
        mongoTemplate,
        config.getLockAcquiredForMillis(),
        config.getLockQuitTryingAfterMillis(),
        config.getLockTryFrequencyMillis());
  }

}

