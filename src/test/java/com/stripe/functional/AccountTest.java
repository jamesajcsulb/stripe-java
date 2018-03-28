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

    Account resource = Account.create(params);

    assertNotNull(resource);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/accounts"),
        params
    );
  }

  @Test
  public void testList() throws StripeException {
    AccountCollection resources = Account.list(null);

    assertNotNull(resources);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/accounts")
    );
  }

  @Test
  public void testRetrieve() throws StripeException {
    Account resource = Account.retrieve();

    assertNotNull(resource);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/account")
    );
  }

  @Test
  public void testRetrieveWithId() throws StripeException {
    Account resource = Account.retrieve(RESOURCE_ID, null);

    assertNotNull(resource);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/accounts/%s", RESOURCE_ID)
    );
  }

  @Test
  public void testUpdate() throws StripeException {
    Account resource = Account.retrieve(RESOURCE_ID, null);

    Map<String, Object> params = new HashMap<String, Object>();
    Map<String, Object> legalEntity = new HashMap<String, Object>();
    legalEntity.put("type", "individual");
    params.put("legal_entity", legalEntity);

    resource.update(params);

    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/accounts/%s", resource.getId()),
        params
    );
  }

  @Test
  public void testDelete() throws StripeException {
    Account resource = Account.retrieve(RESOURCE_ID, null);

    resource.delete();

    verifyRequest(
        APIResource.RequestMethod.DELETE,
        String.format("/v1/accounts/%s", resource.getId())
    );
  }

  @Test
  public void testReject() throws StripeException {
    Account resource = Account.retrieve(RESOURCE_ID, null);

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("reason", "fraud");

    resource.reject(params);

    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/accounts/%s/reject", resource.getId()),
        params
    );
  }

  // TODO: Decide if sub-resources tests should live here.
  @Test
  public void testAccountCreateExternalAccount() throws StripeException {
    Account resource = Account.retrieve(RESOURCE_ID, null);

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("external_account", "tok_1234");
    ExternalAccount ea = resource.getExternalAccounts().create(params);

    assertNotNull(ea);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/accounts/%s/external_accounts", resource.getId()),
        params
    );
  }

  @Test
  public void testAccountCreateLoginLink() throws StripeException, IOException {
    String json = getResourceAsString("/api_fixtures/account_express.json");
    Account resource = APIResource.GSON.fromJson(json, Account.class);

    LoginLink link = resource.getLoginLinks().create();
    assertNotNull(link);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/accounts/%s/login_links", resource.getId())
    );
  }
}
