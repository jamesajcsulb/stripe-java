package com.stripe.model;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.model.Coupon;
import com.stripe.net.APIResource;

import org.junit.Test;

public class CouponTest extends BaseStripeMockTest {
  @Test
  public void testDeserialize() throws Exception {
    String data = getFixture("/v1/coupons/COUPON_ID");
    Coupon resource = APIResource.GSON.fromJson(data, Coupon.class);
    assertNotNull(resource);
    assertNotNull(resource.getId());
  }
}
