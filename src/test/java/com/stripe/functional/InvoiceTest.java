package com.stripe.functional;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.exception.StripeException;
import com.stripe.model.Invoice;
import com.stripe.model.InvoiceCollection;
import com.stripe.net.APIResource;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


public class InvoiceTest extends BaseStripeMockTest {
  public static final String RESOURCE_ID = "in_123";

  @Test
  public void testCreate() throws StripeException {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("customer", "cus_123");

    Invoice resource = Invoice.create(params);

    assertNotNull(resource);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/invoices"),
        params
    );
  }

  @Test
  public void testRetrieve() throws StripeException {
    Invoice resource = Invoice.retrieve(RESOURCE_ID);

    assertNotNull(resource);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/invoices/%s", RESOURCE_ID)
    );
  }

  @Test
  public void testUpdate() throws StripeException {
    Invoice resource = Invoice.retrieve(RESOURCE_ID);

    Map<String, String> metadataParams = new HashMap<String, String>();
    metadataParams.put("key", "value");
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("metadata", metadataParams);

    resource.update(params);

    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/invoices/%s", resource.getId()),
        params
    );
  }

  @Test
  public void testList() throws StripeException {
    Map<String, Object> listParams = new HashMap<String, Object>();
    listParams.put("limit", 1);

    InvoiceCollection resources = Invoice.list(listParams);

    assertNotNull(resources);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/invoices")
    );
  }

  @Test
  public void testUpcoming() throws StripeException {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("customer", "cus_123");

    Invoice resource = Invoice.upcoming(params);

    assertNotNull(resource);
    verifyRequest(
        APIResource.RequestMethod.GET,
        "/v1/invoices/upcoming",
        params
    );
  }

  @Test
  public void testPay() throws StripeException {
    Invoice resource = Invoice.retrieve(RESOURCE_ID);

    resource.pay();

    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/invoices/%s/pay", resource.getId())
    );
  }
}
