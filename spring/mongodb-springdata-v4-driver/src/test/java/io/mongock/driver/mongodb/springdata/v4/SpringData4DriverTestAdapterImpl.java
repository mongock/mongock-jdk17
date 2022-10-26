package io.mongock.driver.mongodb.springdata.v4;

import com.mongodb.client.*;
import com.mongodb.client.model.*;
import io.mongock.driver.mongodb.test.template.util.*;
import org.bson.*;

public class SpringData4DriverTestAdapterImpl implements MongoDBDriverTestAdapter {

  private final MongoCollection<Document> collection;

  public SpringData4DriverTestAdapterImpl(MongoCollection<Document> collection) {
    this.collection = collection;
  }

  @Override
  public void insertOne(Document document) {
    collection.insertOne(document);
  }

  @Override
  public long countDocuments(Document document) {
    return collection.countDocuments(document);
  }

  @Override
  public void createIndex(Document document, IndexOptions options) {
    collection.createIndex(document, options);
  }
}
