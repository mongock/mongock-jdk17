/*
 * Copyright © Ericsson AB 2021.
 *
 * All Rights Reserved.
 *
 * Reproduction in whole or in part is prohibited without the written consent of the copyright owner.
 *
 */

package io.mongock.driver.mongodb.springdata.v4.config;

import io.mongock.api.config.*;
import io.mongock.driver.api.driver.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.data.mongo.*;
import org.springframework.boot.autoconfigure.mongo.*;
import org.springframework.boot.test.context.runner.*;
import org.springframework.cloud.sleuth.autoconfig.brave.*;
import org.springframework.cloud.sleuth.autoconfig.instrument.config.*;
import org.springframework.cloud.sleuth.autoconfig.instrument.mongodb.*;
import org.springframework.cloud.sleuth.autoconfig.instrument.tx.*;
import org.springframework.cloud.sleuth.instrument.tx.*;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.*;
import org.springframework.data.mongodb.core.*;

import static org.assertj.core.api.Assertions.*;

public class SpringDataMongoV4ContextTest {
  
  @Test
  @Disabled("Sleuth is not used in mongock but it's failing and we want to know why before removing it.")
  public void withoutSleuth() {
    ApplicationContextRunner applicationContextRunner = new ApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(
            MongockConfiguration.class,
            MongoTxConfiguration.class,
            SpringDataMongoV4Context.class,
            MongoAutoConfiguration.class,
            MongoDataAutoConfiguration.class));
    applicationContextRunner.run((context) -> assertThat(context)
        .hasSingleBean(MongoTransactionManager.class)
        .hasSingleBean(MongoTemplate.class)
        .hasSingleBean(ConnectionDriver.class));
  }

  @Test
  @Disabled("Sleuth is not used in mongock but it's failing and we want to know why before removing it.")
  public void withSleuth() {
    ApplicationContextRunner applicationContextRunner = new ApplicationContextRunner()
        .withConfiguration(AutoConfigurations.of(
            MongockConfiguration.class,
            MongoTxConfiguration.class,
            SpringDataMongoV4Context.class,
            MongoAutoConfiguration.class,
            MongoDataAutoConfiguration.class,
            TraceMongoDbAutoConfiguration.class,
            TraceSpringCloudConfigAutoConfiguration.class,
            BraveAutoConfiguration.class,
            TraceTxAutoConfiguration.class));
    applicationContextRunner.run((context) -> assertThat(context)
        .hasSingleBean(TracePlatformTransactionManager.class)
        .hasSingleBean(MongoTemplate.class)
        .hasSingleBean(ConnectionDriver.class));
  }

  @Configuration(proxyBeanMethods = false)
  static class MongoTxConfiguration {
    @Bean
    public MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
      return new MongoTransactionManager(dbFactory);
    }
  }
}
