package com.stripe.model;

import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.stripe.BaseStripeTest;
import com.stripe.exception.StripeException;
import com.stripe.model.SubscriptionItem;
import com.stripe.net.APIResource;
import com.stripe.net.LiveStripeResponseGetter;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SubscriptionItemTest extends BaseStripeTest {

  @Before
  public void mockStripeResponseGetter() {
    APIResource.setStripeResponseGetter(networkMock);
  }

  @After
  public void unmockStripeResponseGetter() {
    /* This needs to be done because tests aren't isolated in Java */
    APIResource.setStripeResponseGetter(new LiveStripeResponseGetter());
  }

  @Test
  public void testRetrieve() throws StripeException {
    SubscriptionItem.retrieve("test_item");

    verifyGet(SubscriptionItem.class, "https://api.stripe.com/v1/subscription_items/test_item");
    verifyNoMoreInteractions(networkMock);
  }

  @Test
  public void testList() throws StripeException {
    HashMap<String, Object> params = new HashMap<String, Object>();
    params.put("limit", 3);
    params.put("subscription", "test_sub");

    SubscriptionItem.list(params);

    verifyGet(SubscriptionItemCollection.class, "https://api.stripe.com/v1/subscription_items", params);
    verifyNoMoreInteractions(networkMock);
  }

  @Test
  public void testUpdate() throws StripeException {
    SubscriptionItem item = new SubscriptionItem();
    item.setId("test_item");

    HashMap<String, Object> params = new HashMap<String, Object>();
    params.put("plan", "gold");

    item.update(params);

    verifyPost(SubscriptionItem.class, "https://api.stripe.com/v1/subscription_items/test_item", params);
    verifyNoMoreInteractions(networkMock);
  }

  @Test
  public void testCreate() throws StripeException {
    HashMap<String, Object> params = new HashMap<String, Object>();
    params.put("subscription", "sub_8OgUootyH2faMz");
    params.put("plan", "gold");
    params.put("quantity", 2);

    SubscriptionItem.create(params);

    verifyPost(SubscriptionItem.class, "https://api.stripe.com/v1/subscription_items", params);
    verifyNoMoreInteractions(networkMock);
  }

  @Test
  public void testDelete() throws StripeException {
    SubscriptionItem item = new SubscriptionItem();
    item.setId("test_item");

    HashMap<String, Object> params = new HashMap<String, Object>();
    params.put("prorate", false);

    item.delete(params);

    verifyDelete(DeletedSubscriptionItem.class, "https://api.stripe.com/v1/subscription_items/test_item", params);
    verifyNoMoreInteractions(networkMock);
  }
}
