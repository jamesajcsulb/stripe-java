package com.stripe.functional;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.exception.StripeException;
import com.stripe.model.Token;
import com.stripe.net.APIResource;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TokenTest extends BaseStripeMockTest {
  public static final String RESOURCE_ID = "tok_123";

  @Test
  public void testCreate() throws StripeException {
    Calendar now = Calendar.getInstance();
    Map<String, Object> card = new HashMap<String, Object>();
    card.put("number", "4242424242424242");
    card.put("exp_month", now.get(Calendar.MONTH));
    card.put("exp_year", now.get(Calendar.YEAR) + 1);
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("card", card);

    Token token = Token.create(params);

    assertNotNull(token);
    verifyRequest(
        APIResource.RequestMethod.POST,
        "/v1/tokens",
        params
    );
  }

  @Test
  public void testRetrieve() throws StripeException {
    Token token = Token.retrieve(RESOURCE_ID);

    assertNotNull(token);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/tokens/%s", RESOURCE_ID)
    );
  }
}
