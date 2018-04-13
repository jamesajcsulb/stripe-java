package com.stripe.model;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.net.APIResource;

import org.junit.Ignore;
import org.junit.Test;

// stripe-mock doesn't return an outcome, disable the test for now
@Ignore
public class ChargeOutcomeTest extends BaseStripeMockTest {
  @Test
  public void testDeserialize() throws Exception {
    String chargeData = getFixture("/v1/charges/ch_123");
    String data = getDataAt(chargeData, "outcome");
    ChargeOutcome object = APIResource.GSON.fromJson(data, ChargeOutcome.class);
    assertNotNull(object);
    assertNotNull(object.getNetworkStatus());
  }
}
