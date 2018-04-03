package com.stripe.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.exception.StripeException;
import com.stripe.model.Order;
import com.stripe.model.OrderCollection;
import com.stripe.model.OrderReturn;
import com.stripe.net.APIResource;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class OrderTest extends BaseStripeMockTest {
  public static final String RESOURCE_ID = "or_123";

  @Test
  public void testCreate() throws StripeException {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("currency", "usd");

    final Order order = Order.create(params);

    assertNotNull(order);
    verifyRequest(
        APIResource.RequestMethod.POST,
        "/v1/orders",
        params
    );
  }

  @Test
  public void testRetrieve() throws StripeException {
    final Order order = Order.retrieve(RESOURCE_ID);

    assertNotNull(order);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/orders/%s", RESOURCE_ID)
    );
  }

  @Test
  public void testUpdate() throws StripeException {
    final Order order = Order.retrieve(RESOURCE_ID);

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("status", "fulfilled");

    final Order updatedOrder = order.update(params);

    assertNotNull(updatedOrder);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/orders/%s", order.getId()),
        params
    );
  }

  @Test
  public void testList() throws StripeException {
    final OrderCollection orders = Order.list(null);

    assertNotNull(orders);
    assertEquals(1, orders.getData().size());
    verifyRequest(
        APIResource.RequestMethod.GET,
        "/v1/orders"
    );
  }

  @Test
  public void testPay() throws StripeException {
    final Order order = Order.retrieve(RESOURCE_ID);

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("customer", "cus_123");

    final Order paidOrder = order.pay(params);

    assertNotNull(paidOrder);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/orders/%s/pay", order.getId()),
        params
    );
  }

  @Test
  public void testReturn() throws StripeException {
    final Order order = Order.retrieve(RESOURCE_ID);

    final OrderReturn orderReturn = order.returnOrder(null);

    assertNotNull(orderReturn);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/orders/%s/returns", order.getId()),
        null
    );
  }
}
