package com.stripe.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.exception.StripeException;
import com.stripe.model.DeletedSubscriptionItem;
import com.stripe.model.SubscriptionItem;
import com.stripe.model.SubscriptionItemCollection;
import com.stripe.net.APIResource;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SubscriptionItemTest extends BaseStripeMockTest {
  public static final String RESOURCE_ID = "si_123";

  @Test
  public void testCreate() throws StripeException {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("plan", "plan_123");
    params.put("subscription", "cus_123");
    params.put("quantity", 99);

    SubscriptionItem subscriptionItem = SubscriptionItem.create(params);

    assertNotNull(subscriptionItem);
    verifyRequest(
        APIResource.RequestMethod.POST,
        "/v1/subscription_items",
        params
    );
  }

  @Test
  public void testRetrieve() throws StripeException {
    SubscriptionItem subscriptionItem = SubscriptionItem.retrieve(RESOURCE_ID);

    assertNotNull(subscriptionItem);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/subscription_items/%s", RESOURCE_ID)
    );
  }

  @Test
  public void testUpdate() throws StripeException {
    SubscriptionItem subscriptionItem = SubscriptionItem.retrieve(RESOURCE_ID);

    Map<String, Object> metadata = new HashMap<String, Object>();
    metadata.put("key", "value");
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("metadata", metadata);

    SubscriptionItem updatedSubscriptionItem = subscriptionItem.update(params);

    assertNotNull(updatedSubscriptionItem);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/subscription_items/%s", subscriptionItem.getId()),
        params
    );
  }

  @Test
  public void testDelete() throws StripeException {
    SubscriptionItem subscriptionItem = SubscriptionItem.retrieve(RESOURCE_ID);

    DeletedSubscriptionItem deletedSubscriptionItem = subscriptionItem.delete();

    assertNotNull(deletedSubscriptionItem);
    verifyRequest(
        APIResource.RequestMethod.DELETE,
        String.format("/v1/subscription_items/%s", subscriptionItem.getId())
    );
  }

  @Test
  public void testList() throws StripeException {
    SubscriptionItemCollection subscriptionItems = SubscriptionItem.list(null);

    assertNotNull(subscriptionItems);
    assertEquals(1, subscriptionItems.getData().size());
    verifyRequest(
        APIResource.RequestMethod.GET,
        "/v1/subscription_items"
    );
  }
}
