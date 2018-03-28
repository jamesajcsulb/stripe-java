package com.stripe.functional;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountCollection;
import com.stripe.model.ExternalAccount;
import com.stripe.model.LoginLink;
import com.stripe.net.APIResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class AccountTest extends BaseStripeMockTest {
  public static final String RESOURCE_ID = "acct_123";

  @Test
  public void testCreate() throws StripeException {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("type", "custom");

    Account account = Account.create(params);

    assertNotNull(account);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/accounts"),
        params
    );
  }

  @Test
  public void testList() throws StripeException {
    AccountCollection accounts = Account.list(null);

    assertNotNull(accounts);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/accounts")
    );
  }

  @Test
  public void testRetrieve() throws StripeException {
    Account account = Account.retrieve();

    assertNotNull(account);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/account")
    );
  }

  @Test
  public void testRetrieveWithId() throws StripeException {
    Account account = Account.retrieve(RESOURCE_ID, null);

    assertNotNull(account);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/accounts/%s", RESOURCE_ID)
    );
  }

  @Test
  public void testUpdate() throws StripeException {
    Account account = Account.retrieve(RESOURCE_ID, null);

    Map<String, Object> params = new HashMap<String, Object>();
    Map<String, Object> legalEntity = new HashMap<String, Object>();
    legalEntity.put("type", "individual");
    params.put("legal_entity", legalEntity);
    account.update(params);

    assertNotNull(account);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/accounts/%s", account.getId()),
        params
    );
  }

  @Test
  public void testDelete() throws StripeException {
    Account account = Account.retrieve(RESOURCE_ID, null);
    assertNotNull(account);

    account.delete();

    verifyRequest(
        APIResource.RequestMethod.DELETE,
        String.format("/v1/accounts/%s", account.getId())
    );
  }

  @Test
  public void testReject() throws StripeException {
    Account account = Account.retrieve(RESOURCE_ID, null);

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("reason", "fraud");
    account.reject(params);

    assertNotNull(account);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/accounts/%s/reject", account.getId()),
        params
    );
  }

  // TODO: Decide if sub-resources tests should live here.
  @Test
  public void testAccountCreateExternalAccount() throws StripeException {
    Account account = Account.retrieve(RESOURCE_ID, null);

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("external_account", "tok_1234");
    ExternalAccount ea = account.getExternalAccounts().create(params);

    assertNotNull(account);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/accounts/%s/external_accounts", account.getId()),
        params
    );
  }

  @Test
  public void testAccountCreateLoginLink() throws StripeException, IOException {
    String json = getResourceAsString("/api_fixtures/account_express.json");
    Account account = APIResource.GSON.fromJson(json, Account.class);
    assertNotNull(account);

    LoginLink link = account.getLoginLinks().create();
    assertNotNull(link);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/accounts/%s/login_links", account.getId())
    );
  }
}
