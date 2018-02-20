package com.stripe.model;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.net.APIResource;

import org.junit.Test;

public class SubscriptionItemTest extends BaseStripeMockTest {
  @Test
  public void testDeserialize() throws Exception {
    String data = getFixture("/v1/subscription_items/si_123");
    SubscriptionItem subscriptionItem = APIResource.GSON.fromJson(data, SubscriptionItem.class);
    assertNotNull(subscriptionItem);
    assertNotNull(subscriptionItem.getId());
  }
}
