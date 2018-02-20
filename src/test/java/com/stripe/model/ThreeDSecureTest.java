package com.stripe.model;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.net.APIResource;

import org.junit.Test;

public class ThreeDSecureTest extends BaseStripeMockTest {
  @Test
  public void testDeserialize() throws Exception {
    String data = getFixture("/v1/3d_secure/tds_123");
    ThreeDSecure threeDSecure = APIResource.GSON.fromJson(data, ThreeDSecure.class);
    assertNotNull(threeDSecure);
    assertNotNull(threeDSecure.getId());
  }
}
