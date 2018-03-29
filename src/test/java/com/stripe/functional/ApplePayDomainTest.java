package com.stripe.functional;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.exception.StripeException;
import com.stripe.model.ApplePayDomain;
import com.stripe.model.ApplePayDomainCollection;
import com.stripe.net.APIResource;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ApplePayDomainTest extends BaseStripeMockTest {
  public static final String RESOURCE_ID = "apftw_123";

  @Test
  public void testCreate() throws StripeException {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("domain_name", "stripe.com");

    ApplePayDomain resource = ApplePayDomain.create(params);

    assertNotNull(resource);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/apple_pay/domains"),
        params
    );
  }

  @Test
  public void testRetrieve() throws StripeException {
    ApplePayDomain resource = ApplePayDomain.retrieve(RESOURCE_ID);

    assertNotNull(resource);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/apple_pay/domains/%s", RESOURCE_ID)
    );
  }

  @Test
  public void testList() throws StripeException {
    Map<String, Object> listParams = new HashMap<String, Object>();
    listParams.put("limit", 1);

    ApplePayDomainCollection resources = ApplePayDomain.list(listParams);

    assertNotNull(resources);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/apple_pay/domains")
    );
  }

  @Test
  public void testDelete() throws StripeException {
    ApplePayDomain resource = ApplePayDomain.retrieve(RESOURCE_ID);

    resource.delete();

    verifyRequest(
        APIResource.RequestMethod.DELETE,
        String.format("/v1/apple_pay/domains/%s", resource.getId())
    );
  }
}
