package com.stripe.model;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.model.Card;
import com.stripe.net.APIResource;

import org.junit.Test;

public class CardTest extends BaseStripeMockTest {
  @Test
  public void testDeserialize() throws Exception {
    String data = getFixture("/v1/customers/cus_123/cards/card_123");
    Card resource = APIResource.GSON.fromJson(data, Card.class);
    assertNotNull(resource);
    assertNotNull(resource.getId());
  }
}
