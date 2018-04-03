package com.stripe.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.net.APIResource;

import org.junit.Test;

public class ProductTest extends BaseStripeMockTest {
  @Test
  public void testDeserialize() throws Exception {
    String data = getFixture("/v1/products/prod_123");
    Product product = APIResource.GSON.fromJson(data, Product.class);
    assertNotNull(product);
    assertNotNull(product.getId());
    assertEquals("product", product.getObject());
  }
}
