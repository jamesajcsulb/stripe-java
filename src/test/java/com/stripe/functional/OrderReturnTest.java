package com.stripe.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.exception.StripeException;
import com.stripe.model.OrderReturn;
import com.stripe.model.OrderReturnCollection;
import com.stripe.net.APIResource;

import org.junit.Test;

public class OrderReturnTest extends BaseStripeMockTest {
  public static final String RESOURCE_ID = "orret_123";

  @Test
  public void testRetrieve() throws StripeException {
    final OrderReturn orderReturn = OrderReturn.retrieve(RESOURCE_ID);

    assertNotNull(orderReturn);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/order_returns/%s", RESOURCE_ID)
    );
  }

  @Test
  public void testList() throws StripeException {
    final OrderReturnCollection orderReturns = OrderReturn.list(null);

    assertNotNull(orderReturns);
    assertEquals(1, orderReturns.getData().size());
    verifyRequest(
        APIResource.RequestMethod.GET,
        "/v1/order_returns"
    );
  }
}
