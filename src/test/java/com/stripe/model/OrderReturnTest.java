package com.stripe.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.net.APIResource;

import org.junit.Test;

public class OrderReturnTest extends BaseStripeMockTest {
  @Test
  public void testDeserialize() throws Exception {
    String data = getFixture("/v1/order_returns/orret_123");
    OrderReturn orderReturn = APIResource.GSON.fromJson(data, OrderReturn.class);
    assertNotNull(orderReturn);
    assertNotNull(orderReturn.getId());
    assertEquals("order_return", orderReturn.getObject());
  }
}
