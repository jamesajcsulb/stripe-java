package com.stripe.model;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.model.ApplePayDomain;
import com.stripe.net.APIResource;

import org.junit.Test;

public class ApplePayDomainTest extends BaseStripeMockTest {
  @Test
  public void testDeserialize() throws Exception {
    String data = getFixture("/v1/apple_pay/domains/apftw_123");
    ApplePayDomain resource = APIResource.GSON.fromJson(data, ApplePayDomain.class);
    assertNotNull(resource);
    assertNotNull(resource.getId());
  }
}
