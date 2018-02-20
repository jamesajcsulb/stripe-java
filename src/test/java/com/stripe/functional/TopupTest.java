package com.stripe.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.exception.StripeException;
import com.stripe.model.Topup;
import com.stripe.model.TopupCollection;
import com.stripe.net.APIResource;

import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

// stripe-mock returns an invalid `source` attribute in the topup fixture that causes a crash
// during deserialization. Disable the tests while waiting for a fix.
@Ignore
public class TopupTest extends BaseStripeMockTest {
  public static final String RESOURCE_ID = "tu_123";

  @Test
  public void testCreate() throws StripeException {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("amount", 100);
    params.put("currency", "usd");
    params.put("source", "src_123");
    params.put("description", "creating a topup");
    params.put("statement_descriptor", "topup");

    Topup topup = Topup.create(params);

    assertNotNull(topup);
    verifyRequest(
        APIResource.RequestMethod.POST,
        "/v1/topups",
        params
    );
  }

  @Test
  public void testRetrieve() throws StripeException {
    Topup topup = Topup.retrieve(RESOURCE_ID);

    assertNotNull(topup);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/topups/%s", RESOURCE_ID)
    );
  }

  @Test
  public void testUpdate() throws StripeException {
    Topup topup = Topup.retrieve(RESOURCE_ID);

    Map<String, Object> metadata = new HashMap<String, Object>();
    metadata.put("key", "value");
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("metadata", metadata);

    Topup updatedTopup = topup.update(params);

    assertNotNull(updatedTopup);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/transfers/%s", topup.getId()),
        params
    );
  }

  @Test
  public void testList() throws StripeException {
    TopupCollection topups = Topup.list(null);

    assertNotNull(topups);
    assertEquals(1, topups.getData().size());
    verifyRequest(
        APIResource.RequestMethod.GET,
        "/v1/topups"
    );
  }
}
