package com.stripe.model;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.net.APIResource;

import org.junit.Test;

public class TokenTest extends BaseStripeMockTest {
  @Test
  public void testDeserialize() throws Exception {
    String data = getFixture("/v1/tokens/tok_123");
    Token token = APIResource.GSON.fromJson(data, Token.class);
    assertNotNull(token);
    assertNotNull(token.getId());
  }
}
