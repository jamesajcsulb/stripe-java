package com.stripe.model;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.net.APIResource;

import org.junit.Test;

public class LegalEntityTest extends BaseStripeMockTest {
  @Test
  public void testDeserialize() throws Exception {
    String json = getResourceAsString("/api_fixtures/legal_entity.json");
    LegalEntity le = APIResource.GSON.fromJson(json, LegalEntity.class);
    assertNotNull(le);
    // TODO: Figure out how to test various versions of legal_entity
    // such as with additional_owners, with expanded verification doc, etc.
  }
}
