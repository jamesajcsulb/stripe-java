package com.stripe.functional;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.exception.StripeException;
import com.stripe.model.Dispute;
import com.stripe.model.DisputeCollection;
import com.stripe.net.APIResource;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class DisputeTest extends BaseStripeMockTest {
  public static final String RESOURCE_ID = "dp_123";

  @Test
  public void testRetrieve() throws StripeException {
    Dispute resource = Dispute.retrieve(RESOURCE_ID);

    assertNotNull(resource);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/disputes/%s", RESOURCE_ID)
    );
  }

  @Test
  public void testUpdate() throws StripeException {
    Dispute resource = Dispute.retrieve(RESOURCE_ID);

    Map<String, String> metadataParams = new HashMap<String, String>();
    metadataParams.put("key", "value");
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("metadata", metadataParams);

    resource.update(params);

    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/disputes/%s", resource.getId()),
        params
    );
  }

  @Test
  public void testList() throws StripeException {
    Map<String, Object> listParams = new HashMap<String, Object>();
    listParams.put("limit", 1);

    DisputeCollection resources = Dispute.list(listParams);

    assertNotNull(resources);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/disputes")
    );
  }

  @Test
  public void testClose() throws StripeException {
    Dispute resource = Dispute.retrieve(RESOURCE_ID);

    Dispute disputeClosed = resource.close();

    assertNotNull(disputeClosed);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/disputes/%s/close", resource.getId())
    );
  }
}
