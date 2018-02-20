package com.stripe.model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.net.APIResource;

import org.junit.Test;

public class PlanTest extends BaseStripeMockTest {
  @Test
  public void testDeserialize() throws Exception {
    String data = getFixture("/v1/plans/gold");
    Plan plan = APIResource.GSON.fromJson(data, Plan.class);
    assertNotNull(plan);
    assertNotNull(plan.getId());
    assertNull(plan.getProductObject());
  }

  @Test
  public void testDeserializeWithExpansions() throws Exception {
    String[] expansions = { "product" };
    String data = getFixture("/v1/plans/gold", expansions);
    Plan plan = APIResource.GSON.fromJson(data, Plan.class);
    assertNotNull(plan);
    Product product = plan.getProductObject();
    assertNotNull(product);
    assertNotNull(product.getName());
  }
}
