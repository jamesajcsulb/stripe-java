package com.stripe.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.net.APIResource;

import org.junit.Test;

public class OrderItemTest extends BaseStripeMockTest {
  @Test
  public void testDeserialize() throws Exception {
    String orderData = getFixture("/v1/orders/or_123");
    String itemsData = getDataAt(orderData, "items");
    String itemData = getDataAt(itemsData, 0);
    OrderItem orderItem = APIResource.GSON.fromJson(itemData, OrderItem.class);
    assertNotNull(orderItem);
    assertEquals("order_item", orderItem.getObject());
  }
}
