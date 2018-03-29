package com.stripe.functional;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.exception.StripeException;
import com.stripe.model.Coupon;
import com.stripe.model.CouponCollection;
import com.stripe.net.APIResource;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class CouponTest extends BaseStripeMockTest {
  public static final String RESOURCE_ID = "COUPON_ID";

  @Test
  public void testCreate() throws StripeException {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("percent_off", 25);
    params.put("duration", "forever");

    Coupon resource = Coupon.create(params);

    assertNotNull(resource);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/coupons"),
        params
    );
  }

  @Test
  public void testRetrieve() throws StripeException {
    Coupon resource = Coupon.retrieve(RESOURCE_ID);

    assertNotNull(resource);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/coupons/%s", RESOURCE_ID)
    );
  }

  @Test
  public void testUpdate() throws StripeException {
    Coupon resource = Coupon.retrieve(RESOURCE_ID);

    Map<String, String> metadataParams = new HashMap<String, String>();
    metadataParams.put("key", "value");
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("metadata", metadataParams);

    resource.update(params);

    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/coupons/%s", resource.getId()),
        params
    );
  }

  @Test
  public void testList() throws StripeException {
    Map<String, Object> listParams = new HashMap<String, Object>();
    listParams.put("limit", 1);

    CouponCollection resources = Coupon.list(listParams);

    assertNotNull(resources);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/coupons")
    );
  }

  @Test
  public void testDelete() throws StripeException {
    Coupon resource = Coupon.retrieve(RESOURCE_ID);

    resource.delete();

    verifyRequest(
        APIResource.RequestMethod.DELETE,
        String.format("/v1/coupons/%s", resource.getId())
    );
  }
}
