package com.stripe.model;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.net.APIResource;

import org.junit.Test;

public class SubscriptionTest extends BaseStripeMockTest {
  @Test
  public void testDeserialize() throws Exception {
    String data = getFixture("/v1/subscriptions/sub_123");
    Subscription subscription = APIResource.GSON.fromJson(data, Subscription.class);
    assertNotNull(subscription);
    assertNotNull(subscription.getId());
  }
}
