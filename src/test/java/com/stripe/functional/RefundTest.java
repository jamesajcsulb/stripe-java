package com.stripe.functional;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import com.stripe.model.RefundCollection;
import com.stripe.net.APIResource;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


public class RefundTest extends BaseStripeMockTest {
  public static final String RESOURCE_ID = "re_123";

  @Test
  public void testCreate() throws StripeException {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("charge", "ch_123");

    Refund resource = Refund.create(params);

    assertNotNull(resource);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/refunds"),
        params
    );
  }

  @Test
  public void testRetrieve() throws StripeException {
    Refund resource = Refund.retrieve(RESOURCE_ID);

    assertNotNull(resource);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/refunds/%s", RESOURCE_ID)
    );
  }

  @Test
  public void testUpdate() throws StripeException {
    Refund resource = Refund.retrieve(RESOURCE_ID);

    Map<String, String> metadataParams = new HashMap<String, String>();
    metadataParams.put("key", "value");
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("metadata", metadataParams);

    resource.update(params);

    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/refunds/%s", resource.getId()),
        params
    );
  }

  @Test
  public void testList() throws StripeException {
    Map<String, Object> listParams = new HashMap<String, Object>();
    listParams.put("limit", 1);

    RefundCollection resources = Refund.list(listParams);

    assertNotNull(resources);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/refunds")
    );
  }
}
