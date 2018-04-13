package com.stripe.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.google.common.collect.ImmutableMap;

import com.stripe.BaseStripeMockTest;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.ChargeCollection;
import com.stripe.net.APIResource;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ChargeTest extends BaseStripeMockTest {
  public static final String RESOURCE_ID = "ch_123";

  @Test
  public void testCreate() throws StripeException {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("amount", 100);
    params.put("currency", "usd");
    params.put("source", "src_123");

    final Charge charge = Charge.create(params);

    assertNotNull(charge);
    verifyRequest(
        APIResource.RequestMethod.POST,
        "/v1/charges",
        params
    );
  }

  @Test
  public void testRetrieve() throws StripeException {
    final Charge charge = Charge.retrieve(RESOURCE_ID);

    assertNotNull(charge);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/charges/%s", RESOURCE_ID)
    );
  }

  @Test
  public void testUpdate() throws StripeException {
    final Charge charge = Charge.retrieve(RESOURCE_ID);
    resetNetworkSpy();

    Map<String, Object> metadata = new HashMap<String, Object>();
    metadata.put("foo", "bar");
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("metadata", metadata);

    Charge updatedCharge = charge.update(params);

    assertNotNull(updatedCharge);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/charges/%s", charge.getId()),
        params
    );
  }

  @Test
  public void testList() throws StripeException {
    ChargeCollection charges = Charge.list(null);

    assertNotNull(charges);
    assertEquals(1, charges.getData().size());
    verifyRequest(
        APIResource.RequestMethod.GET,
        "/v1/charges"
    );
  }

  @Test
  public void testMarkFaudulent() throws StripeException {
    final Charge charge = Charge.retrieve(RESOURCE_ID);
    resetNetworkSpy();

    Charge fraudulentCharge = charge.markFraudulent(null);

    assertNotNull(fraudulentCharge);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/charges/%s", charge.getId()),
        ImmutableMap.of("fraud_details",
          (Object)ImmutableMap.of("user_report", (Object)"fraudulent"))
    );
  }

  @Test
  public void testMarkSafe() throws StripeException {
    final Charge charge = Charge.retrieve(RESOURCE_ID);
    resetNetworkSpy();

    Charge fraudulentCharge = charge.markSafe(null);

    assertNotNull(fraudulentCharge);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/charges/%s", charge.getId()),
        ImmutableMap.of("fraud_details",
          (Object)ImmutableMap.of("user_report", (Object)"safe"))
    );
  }
}
