package io.mongock.driver.mongodb.springdata.v4;


import com.mongodb.*;
import com.mongodb.client.*;
import io.mongock.api.config.*;
import io.mongock.driver.mongodb.springdata.v4.config.*;
import io.mongock.driver.mongodb.sync.v4.repository.*;
import io.mongock.driver.mongodb.sync.v4.repository.util.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.data.mongodb.core.*;

public class SpringDataMongoV4ContextTest {


  /**
   * public ConnectionDriver connectionDriver(MongoTemplate mongoTemplate,
   *                                            SpringMongoDBConfiguration config)
   */



  @Test
  @Disabled("Find the way to mock it  ")
  public void shouldCreateDefaultReadWriteConcerns_whenCreating_ifNoParams() {
    MongoTemplate mongoTemplate = Mockito.mock(MongoTemplate.class);
    Mockito.when(mongoTemplate.getDb()).thenReturn(Mockito.mock(MongoDatabase.class));

    MongockConfiguration config = Mockito.spy(new MongockConfiguration());
    MongoDBConfiguration mongoDbConfig = Mockito.spy(new MongoDBConfiguration());
//    Mockito.when(config.getMongoDb()).thenReturn(new SpringMongoDBConfiguration.MongoDBConfiguration())
//    SpringMongoDBConfiguration.MongoDBConfiguration mongoDBConfig = new SpringMongoDBConfiguration.MongoDBConfiguration();

    SpringDataMongoV4Driver driver = (SpringDataMongoV4Driver)new SpringDataMongoV4Context().connectionDriver(
        mongoTemplate, config, mongoDbConfig
    );
    WriteConcern expectedWriteConcern = WriteConcern.MAJORITY;
    ReadConcern expectedReadConcern = ReadConcern.MAJORITY;
    ReadPreference expectedReadPreference = ReadPreference.primary();

    MongoCollection changeEntryCollection = RepositoryAccessorHelper.getCollection((MongoSync4RepositoryBase) driver.getChangeEntryService());
    Assertions.assertEquals(expectedWriteConcern, changeEntryCollection.getWriteConcern());
    Assertions.assertEquals(expectedReadConcern, changeEntryCollection.getReadConcern());
    Assertions.assertEquals(expectedReadPreference, changeEntryCollection.getReadPreference());
  }

}
