package com.stripe.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.exception.StripeException;
import com.stripe.model.Subscription;
import com.stripe.model.SubscriptionCollection;
import com.stripe.net.APIResource;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SubscriptionTest extends BaseStripeMockTest {
  public static final String RESOURCE_ID = "sub_123";

  @Test
  public void testCreate() throws StripeException {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("customer", "cus_123");
    params.put("plan", "plan_123");

    Subscription subscription = Subscription.create(params);

    assertNotNull(subscription);
    verifyRequest(
        APIResource.RequestMethod.POST,
        "/v1/subscriptions",
        params
    );
  }

  @Test
  public void testRetrieve() throws StripeException {
    Subscription subscription = Subscription.retrieve(RESOURCE_ID);

    assertNotNull(subscription);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/subscriptions/%s", RESOURCE_ID)
    );
  }

  @Test
  public void testUpdate() throws StripeException {
    Subscription subscription = Subscription.retrieve(RESOURCE_ID);

    Map<String, Object> metadata = new HashMap<String, Object>();
    metadata.put("key", "value");
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("metadata", metadata);

    Subscription updatedSubscription = subscription.update(params);

    assertNotNull(updatedSubscription);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/subscriptions/%s", subscription.getId()),
        params
    );
  }

  @Test
  public void testList() throws StripeException {
    SubscriptionCollection subscriptions = Subscription.list(null);

    assertNotNull(subscriptions);
    assertEquals(1, subscriptions.getData().size());
    verifyRequest(
        APIResource.RequestMethod.GET,
        "/v1/subscriptions"
    );
  }

  @Test
  public void testCancel() throws StripeException {
    Subscription subscription = Subscription.retrieve(RESOURCE_ID);

    Subscription canceledSubscription = subscription.cancel(null);

    assertNotNull(canceledSubscription);
    verifyRequest(
        APIResource.RequestMethod.DELETE,
        String.format("/v1/subscriptions/%s", subscription.getId())
    );
  }

  @Test
  public void testDeleteDiscount() throws StripeException {
    Subscription subscription = Subscription.retrieve(RESOURCE_ID);

    subscription.deleteDiscount();

    verifyRequest(
        APIResource.RequestMethod.DELETE,
        String.format("/v1/subscriptions/%s/discount", subscription.getId())
    );
  }
}
