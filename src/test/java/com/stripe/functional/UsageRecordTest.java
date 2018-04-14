package com.stripe.functional;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.exception.StripeException;
import com.stripe.model.UsageRecord;
import com.stripe.net.APIResource;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class UsageRecordTest extends BaseStripeMockTest {

  @Test
  public void testCreate() throws StripeException {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("quantity", 10);
    params.put("subscription_item", "si_123");
    params.put("timestamp", System.currentTimeMillis() / 1000L);

    UsageRecord resource = UsageRecord.create(params, null);

    // TODO: We likely should pass the item as a parameter instead
    // The library removes this parameter to pass it in the URL
    params.remove("subscription_item");

    assertNotNull(resource);
    verifyRequest(
        APIResource.RequestMethod.POST,
        "/v1/subscription_items/si_123/usage_records",
        params
    );
  }
}
