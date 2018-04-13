package com.stripe.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Recipient;
import com.stripe.model.RecipientCollection;
import com.stripe.net.APIResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;


public class RecipientTest extends BaseStripeMockTest {
  public static final String RESOURCE_ID = "rp_123";

  @Test
  public void testCreate() throws StripeException {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("name", "John Doe");
    params.put("type", "Individual");

    Recipient resource = Recipient.create(params);

    assertNotNull(resource);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/recipients"),
        params
    );
  }

  @Test
  public void testRetrieve() throws StripeException {
    Recipient resource = Recipient.retrieve(RESOURCE_ID);

    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/recipients/%s", RESOURCE_ID)
    );
  }

  @Test
  public void testUpdate() throws StripeException {
    Recipient resource = Recipient.retrieve(RESOURCE_ID);

    Map<String, String> metadataParams = new HashMap<String, String>();
    metadataParams.put("key", "value");
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("metadata", metadataParams);

    resource.update(params);

    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/recipients/%s", resource.getId()),
        params
    );
  }

  @Test
  public void testList() throws StripeException {
    Map<String, Object> listParams = new HashMap<String, Object>();
    listParams.put("limit", 1);

    RecipientCollection resources = Recipient.list(listParams);
    
    assertNotNull(resources);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/recipients")
    );
  }

  @Test
  public void testDelete() throws StripeException {
    Recipient resource = Recipient.retrieve(RESOURCE_ID);

    resource.delete();

    verifyRequest(
        APIResource.RequestMethod.DELETE,
        String.format("/v1/recipients/%s", resource.getId())
    );
  }

  @Test
  public void testCreateCard() throws IOException, StripeException {
    Recipient resource = Recipient.retrieve(RESOURCE_ID);

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("card", "tok_123");    

    // stripe-mock does not support POST /v1/recipients, so we stub the request
    stubRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/recipients/%s/cards", resource.getId()),
        params,
        Card.class,
        getResourceAsString("/api_fixtures/card.json")
    );

    Card card = resource.createCard(params);

    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/recipients/%s/cards", resource.getId()),
        params
    );
  }
}
