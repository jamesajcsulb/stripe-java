package com.stripe.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.Stripe;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import com.stripe.model.BankAccount;
import com.stripe.model.Card;
import com.stripe.model.Customer;
import com.stripe.model.CustomerCollection;
import com.stripe.model.Subscription;
import com.stripe.net.APIResource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


public class CustomerTest extends BaseStripeMockTest {
  public static final String RESOURCE_ID = "cus_123";

  @Test
  public void testCreate() throws StripeException {
    Map<String, Object> params = new HashMap<String, Object>();

    Customer resource = Customer.create(params);

    assertNotNull(resource);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/customers"),
        params
    );
  }

  @Test
  public void testRetrieve() throws StripeException {
    Customer resource = Customer.retrieve(RESOURCE_ID);

    assertNotNull(resource);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/customers/%s", RESOURCE_ID)
    );
  }

  @Test
  public void testUpdate() throws StripeException {
    Customer resource = Customer.retrieve(RESOURCE_ID);

    Map<String, String> metadataParams = new HashMap<String, String>();
    metadataParams.put("key", "value");
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("metadata", metadataParams);

    resource.update(params);

    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/customers/%s", resource.getId()),
        params
    );
  }

  @Test
  public void testList() throws StripeException {
    Map<String, Object> listParams = new HashMap<String, Object>();
    listParams.put("limit", 1);

    CustomerCollection resources = Customer.list(listParams);

    assertNotNull(resources);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/customers")
    );
  }

  @Test
  public void testDelete() throws StripeException {
    Customer resource = Customer.retrieve(RESOURCE_ID);

    resource.delete();

    verifyRequest(
        APIResource.RequestMethod.DELETE,
        String.format("/v1/customers/%s", resource.getId())
    );
  }

  @Test
  public void testCreateCard() throws IOException, StripeException {
    Customer resource = Customer.retrieve(RESOURCE_ID);

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("source", "tok_123");    

    // stripe-mock returns a BankAccount instance instead of a Card so we stub the request
    stubRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/customers/%s/cards", resource.getId()),
        params,
        Card.class,
        getResourceAsString("/api_fixtures/card.json")
    );

    Card card = resource.createCard(params);

    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/customers/%s/cards", resource.getId()),
        params
    );
  }

  @Test
  public void testCreateBankAccount() throws IOException, StripeException {
    Customer resource = Customer.retrieve(RESOURCE_ID);

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("source", "btok_123");    

    BankAccount ba = resource.createBankAccount(params);

    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/customers/%s/bank_accounts", resource.getId()),
        params
    );
  }

  @Test
  public void testCreateSubscription() throws StripeException {
    Customer resource = Customer.retrieve(RESOURCE_ID);

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("plan", "plan_1");

    Subscription subscription = resource.createSubscription(params);

    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/customers/%s/subscriptions", resource.getId()),
        params
    );
  }

  @Test
  public void testUpdateeSubscription() throws IOException, StripeException {
    Customer resource = Customer.retrieve(RESOURCE_ID);

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("plan", "plan_1");

    // stripe-mock does not support /v1/customers/%s/subscription endpoint, so we stub the request
    stubRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/customers/%s/subscription", resource.getId()),
        params,
        Subscription.class,
        getResourceAsString("/api_fixtures/subscription.json")
    );

    Subscription subscription = resource.updateSubscription(params);

    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/customers/%s/subscription", resource.getId()),
        params
    );
  }

  @Test
  public void testCancelSubscription() throws IOException, StripeException {
    Customer resource = Customer.retrieve(RESOURCE_ID);

    Map<String, Object> params = new HashMap<String, Object>();

    // stripe-mock does not support /v1/customers/%s/subscription endpoint, so we stub the request
    stubRequest(
        APIResource.RequestMethod.DELETE,
        String.format("/v1/customers/%s/subscription", resource.getId()),
        params,
        Subscription.class,
        getResourceAsString("/api_fixtures/subscription.json")
    );

    Subscription subscription = resource.cancelSubscription(params);

    verifyRequest(
        APIResource.RequestMethod.DELETE,
        String.format("/v1/customers/%s/subscription", resource.getId()),
        params
    );
  }

  /*
  // TODO: stubRequest should support no returned value
  @Test
  public void testDeleteDiscount() throws IOException, StripeException {
    Customer resource = Customer.retrieve(RESOURCE_ID);

    Map<String, Object> params = new HashMap<String, Object>();

    // stripe-mock does not support /v1/customers/%s/discount endpoint, so we stub the request
    stubRequest(
        APIResource.RequestMethod.DELETE,
        String.format("/v1/customers/%s/discount", resource.getId()),
        params,
        null,
        null
    );

    resource.deleteDiscount(params);

    verifyRequest(
        APIResource.RequestMethod.DELETE,
        String.format("/v1/customers/%s/discount", resource.getId()),
        params
    );
  }
  */
}
