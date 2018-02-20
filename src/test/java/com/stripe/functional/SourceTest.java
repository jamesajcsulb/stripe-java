package com.stripe.functional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import com.stripe.model.Source;
import com.stripe.net.APIResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class SourceTest extends BaseStripeMockTest {
  public static final String RESOURCE_ID = "src_123";

  @Test
  public void testCreate() throws StripeException {
    Map<String, Object> owner = new HashMap<String, Object>();
    owner.put("email", "jenny.rosen@example.com");
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("type", "ach_credit_transfer");
    params.put("currency", "usd");
    params.put("owner", owner);

    Source source = Source.create(params);

    assertNotNull(source);
    verifyRequest(
        APIResource.RequestMethod.POST,
        "/v1/sources",
        params
    );
  }

  @Test
  public void testRetrieve() throws StripeException {
    Source source = Source.retrieve(RESOURCE_ID);

    assertNotNull(source);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/sources/%s", RESOURCE_ID)
    );
  }

  @Test
  public void testUpdate() throws StripeException {
    Source source = Source.retrieve(RESOURCE_ID);

    Map<String, Object> metadata = new HashMap<String, Object>();
    metadata.put("key", "value");
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("metadata", metadata);

    Source updatedSource = source.update(params);

    assertNotNull(updatedSource);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/sources/%s", source.getId()),
        params
    );
  }

  @Test
  public void testVerify() throws StripeException {
    Source source = Source.retrieve(RESOURCE_ID);

    List<Integer> values = new LinkedList<Integer>();
    values.add(32);
    values.add(45);
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("values", values);

    Source verifiedSource = source.verify(params);

    assertNotNull(verifiedSource);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/sources/%s/verify", source.getId()),
        params
    );
  }

  @Test
  public void testDetachAttachedSource() throws IOException, StripeException {
    Source source = Source.retrieve(RESOURCE_ID);
    source.setCustomer("cus_123");

    // stripe-mock returns a bank_account instead of a source, which causes a deserialization
    // error. Stub the request for now.
    stubRequest(
        APIResource.RequestMethod.DELETE,
        String.format("/v1/customers/%s/sources/%s", source.getCustomer(), source.getId()),
        new HashMap<String, Object>(),
        Source.class,
        getResourceAsString("/api_fixtures/source_detached.json")
    );
    Source detachedSource = source.detach();

    assertNotNull(detachedSource);
    assertNull(detachedSource.getCustomer());
    verifyRequest(
        APIResource.RequestMethod.DELETE,
        String.format("/v1/customers/%s/sources/%s", source.getCustomer(), source.getId())
    );
  }

  @Test(expected = InvalidRequestException.class)
  public void testDetachUnattachedSource() throws StripeException {
    Source source = Source.retrieve(RESOURCE_ID);
    source.setCustomer(null);

    source.detach();
  }
}
