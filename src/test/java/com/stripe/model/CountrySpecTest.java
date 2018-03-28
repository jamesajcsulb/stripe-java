package com.stripe.model;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.model.CountrySpec;
import com.stripe.net.APIResource;

import org.junit.Test;

public class CountrySpecTest extends BaseStripeMockTest {
  @Test
  public void testDeserialize() throws Exception {
    String data = getFixture("/v1/country_specs/us");
    CountrySpec resource = APIResource.GSON.fromJson(data, CountrySpec.class);
    assertNotNull(resource);
    assertNotNull(resource.getId());
  }
}
