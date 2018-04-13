package com.stripe.functional;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.exception.StripeException;
import com.stripe.model.ApplicationFee;
import com.stripe.model.ApplicationFeeCollection;
import com.stripe.net.APIResource;
import com.stripe.net.RequestOptions;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ApplicationFeeTest extends BaseStripeMockTest {
  public static final String RESOURCE_ID = "fee_123";

  @Test
  public void testRetrieve() throws StripeException {
    ApplicationFee resource = ApplicationFee.retrieve(RESOURCE_ID);

    assertNotNull(resource);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/application_fees/%s", RESOURCE_ID)
    );
  }

  @Test
  public void testList() throws StripeException {
    Map<String, Object> listParams = new HashMap<String, Object>();
    listParams.put("limit", 1);

    ApplicationFeeCollection resources = ApplicationFee.list(listParams);

    assertNotNull(resources);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/application_fees")
    );
  }

  @Test
  public void testRefund() throws StripeException {
    ApplicationFee resource = ApplicationFee.retrieve(RESOURCE_ID);

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("amount", 100);

    // TODO: This is an old endpoint that should be removed.
    resource.refund(params, (RequestOptions) null);

    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/application_fees/%s/refund", resource.getId()),
        params
    );
  }
}
