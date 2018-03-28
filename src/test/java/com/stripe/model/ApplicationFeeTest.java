package com.stripe.model;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.model.ApplicationFee;
import com.stripe.net.APIResource;

import org.junit.Test;

public class ApplicationFeeTest extends BaseStripeMockTest {
  @Test
  public void testDeserialize() throws Exception {
    String data = getFixture("/v1/application_fees/fee_123");
    ApplicationFee resource = APIResource.GSON.fromJson(data, ApplicationFee.class);
    assertNotNull(resource);
    assertNotNull(resource.getId());
  }
}
