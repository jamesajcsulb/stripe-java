package com.stripe.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.net.APIResource;

import org.junit.Test;

public class OrderTest extends BaseStripeMockTest {
  @Test
  public void testDeserialize() throws Exception {
    String data = getFixture("/v1/orders/or_123");
    Order order = APIResource.GSON.fromJson(data, Order.class);
    assertNotNull(order);
    assertNotNull(order.getId());
    assertEquals("order", order.getObject());
  }
}
