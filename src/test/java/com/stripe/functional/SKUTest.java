package com.stripe.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.exception.StripeException;
import com.stripe.model.DeletedSKU;
import com.stripe.model.SKU;
import com.stripe.model.SKUCollection;
import com.stripe.net.APIResource;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SKUTest extends BaseStripeMockTest {
  public static final String RESOURCE_ID = "sku_123";

  @Test
  public void testCreate() throws StripeException {
    Map<String, Object> attributes = new HashMap<String, Object>();
    attributes.put("attr1", "val1");
    attributes.put("attr2", "val2");
    Map<String, Object> inventory = new HashMap<String, Object>();
    inventory.put("type", "bucket");
    inventory.put("value", "limited");
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("active", true);
    params.put("attributes", attributes);
    params.put("price", 499);
    params.put("currency", "usd");
    params.put("inventory", inventory);
    params.put("product", "prod_123");
    params.put("image", "http://example.com/foo.png");

    SKU sku = SKU.create(params);

    assertNotNull(sku);
    verifyRequest(
        APIResource.RequestMethod.POST,
        "/v1/skus",
        params
    );
  }

  @Test
  public void testRetrieve() throws StripeException {
    SKU sku = SKU.retrieve(RESOURCE_ID);

    assertNotNull(sku);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/skus/%s", RESOURCE_ID)
    );
  }

  @Test
  public void testUpdate() throws StripeException {
    SKU sku = SKU.retrieve(RESOURCE_ID);

    Map<String, Object> inventory = new HashMap<String, Object>();
    inventory.put("type", "bucket");
    inventory.put("value", "in_stock");
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("inventory", inventory);

    SKU updatedSku = sku.update(params);

    assertNotNull(updatedSku);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/skus/%s", sku.getId()),
        params
    );
  }

  @Test
  public void testDelete() throws StripeException {
    SKU sku = SKU.retrieve(RESOURCE_ID);

    DeletedSKU deletedSku = sku.delete();

    assertNotNull(deletedSku);
    verifyRequest(
        APIResource.RequestMethod.DELETE,
        String.format("/v1/skus/%s", sku.getId())
    );
  }

  @Test
  public void testList() throws StripeException {
    SKUCollection skus = SKU.list(null);

    assertNotNull(skus);
    assertEquals(1, skus.getData().size());
    verifyRequest(
        APIResource.RequestMethod.GET,
        "/v1/skus"
    );
  }
}
