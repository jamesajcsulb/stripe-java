package com.stripe.functional;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.exception.StripeException;
import com.stripe.model.CountrySpec;
import com.stripe.model.CountrySpecCollection;
import com.stripe.net.APIResource;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class CountrySpecTest extends BaseStripeMockTest {
  public static final String RESOURCE_ID = "US";

  @Test
  public void testRetrieve() throws StripeException {
    CountrySpec resource = CountrySpec.retrieve(RESOURCE_ID);

    assertNotNull(resource);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/country_specs/%s", RESOURCE_ID)
    );
  }

  @Test
  public void testList() throws StripeException {
    Map<String, Object> listParams = new HashMap<String, Object>();
    listParams.put("limit", 1);

    CountrySpecCollection resources = CountrySpec.list(listParams);

    assertNotNull(resources);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/country_specs")
    );
  }
}
