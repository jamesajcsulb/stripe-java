package com.stripe.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.net.APIResource;

import org.junit.Test;

public class SKUTest extends BaseStripeMockTest {
  @Test
  public void testDeserialize() throws Exception {
    String data = getFixture("/v1/skus/sku_123");
    SKU sku = APIResource.GSON.fromJson(data, SKU.class);
    assertNotNull(sku);
    assertNotNull(sku.getId());
    assertEquals("sku", sku.getObject());
    assertNull(sku.getProductObject());
  }

  @Test
  public void testDeserializeWithExpansions() throws Exception {
    String[] expansions = { "product" };
    String data = getFixture("/v1/skus/sku_123", expansions);
    SKU sku = APIResource.GSON.fromJson(data, SKU.class);
    assertNotNull(sku);
    Product product = sku.getProductObject();
    assertNotNull(product);
    assertNotNull(product.getName());
  }
}
