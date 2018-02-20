package com.stripe.functional;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.exception.StripeException;
import com.stripe.model.ThreeDSecure;
import com.stripe.net.APIResource;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ThreeDSecureTest extends BaseStripeMockTest {
  public static final String RESOURCE_ID = "tds_123";

  @Test
  public void testCreate() throws StripeException {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("amount", 1000);
    params.put("currency", "usd");
    params.put("customer", "cus_123");
    params.put("card", "card_123");
    params.put("return_url", "https://example.com");

    ThreeDSecure threeDSecure = ThreeDSecure.create(params);

    assertNotNull(threeDSecure);
    verifyRequest(
        APIResource.RequestMethod.POST,
        "/v1/3d_secure",
        params
    );
  }

  @Test
  public void testRetrieve() throws StripeException {
    ThreeDSecure threeDSecure = ThreeDSecure.retrieve(RESOURCE_ID);

    assertNotNull(threeDSecure);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/3d_secure/%s", RESOURCE_ID)
    );
  }
}
