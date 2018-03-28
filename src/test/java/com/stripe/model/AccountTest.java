package com.stripe.model;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.model.Account;
import com.stripe.net.APIResource;

import org.junit.Test;

public class AccountTest extends BaseStripeMockTest {
  @Test
  public void testDeserialize() throws Exception {
    String data = getFixture("/v1/accounts/acct_123");
    Account account = APIResource.GSON.fromJson(data, Account.class);
    assertNotNull(account);
    assertNotNull(account.getId());
  }
}
