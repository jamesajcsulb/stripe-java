package com.stripe.functional;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.exception.StripeException;
import com.stripe.model.Balance;
import com.stripe.net.APIResource;

import org.junit.Test;

public class BalanceTest extends BaseStripeMockTest {
  @Test
  public void testRetrieve() throws StripeException {
    Balance resource = Balance.retrieve();

    assertNotNull(resource);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/balance")
    );
  }
}
