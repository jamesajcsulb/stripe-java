package com.stripe.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.exception.StripeException;
import com.stripe.model.DeletedProduct;
import com.stripe.model.Product;
import com.stripe.model.ProductCollection;
import com.stripe.net.APIResource;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class ProductTest extends BaseStripeMockTest {
  public static final String RESOURCE_ID = "prod_123";

  @Test
  public void testCreate() throws StripeException {
    List<String> attributes = new LinkedList<String>();
    attributes.add("attr1");
    attributes.add("attr2");
    Map<String, Object> packageDimensions = new HashMap<String, Object>();
    packageDimensions.put("height", 2.234);
    packageDimensions.put("length", 5.10);
    packageDimensions.put("width", 6.50);
    packageDimensions.put("weight", 10);
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("active", true);
    params.put("name", "Test Name");
    params.put("description", "This is a description");
    params.put("caption", "This is a caption");
    params.put("attributes", attributes);
    params.put("url", "http://example.com");
    params.put("shippable", true);
    params.put("package_dimensions", packageDimensions);
    params.put("type", "good");

    final Product product = Product.create(params);

    assertNotNull(product);
    verifyRequest(
        APIResource.RequestMethod.POST,
        "/v1/products",
        params
    );
  }

  @Test
  public void testRetrieve() throws StripeException {
    final Product product = Product.retrieve(RESOURCE_ID);

    assertNotNull(product);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/products/%s", RESOURCE_ID)
    );
  }

  @Test
  public void testUpdate() throws StripeException {
    final Product product = Product.retrieve(RESOURCE_ID);

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("name", "Updated Name");

    final Product updatedProduct = product.update(params);

    assertNotNull(updatedProduct);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/products/%s", product.getId()),
        params
    );
  }

  @Test
  public void testDelete() throws StripeException {
    final Product product = Product.retrieve(RESOURCE_ID);

    final DeletedProduct deletedProduct = product.delete();

    assertNotNull(deletedProduct);
    verifyRequest(
        APIResource.RequestMethod.DELETE,
        String.format("/v1/products/%s", product.getId())
    );
  }

  @Test
  public void testList() throws StripeException {
    final ProductCollection products = Product.list(null);

    assertNotNull(products);
    assertEquals(1, products.getData().size());
    verifyRequest(
        APIResource.RequestMethod.GET,
        "/v1/products"
    );
  }
}
