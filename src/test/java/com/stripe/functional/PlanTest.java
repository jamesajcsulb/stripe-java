package com.stripe.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.exception.StripeException;
import com.stripe.model.DeletedPlan;
import com.stripe.model.Plan;
import com.stripe.model.PlanCollection;
import com.stripe.net.APIResource;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class PlanTest extends BaseStripeMockTest {
  public static final String RESOURCE_ID = "gold";

  @Test
  public void testCreate() throws StripeException {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("amount", 1);
    params.put("currency", "usd");
    params.put("id", "sapphire-elite");
    params.put("interval", "month");
    params.put("name", "Sapphire Elite");

    Plan plan = Plan.create(params);

    assertNotNull(plan);
    verifyRequest(
        APIResource.RequestMethod.POST,
        "/v1/plans",
        params
    );
  }

  @Test
  public void testRetrieve() throws StripeException {
    Plan plan = Plan.retrieve(RESOURCE_ID);

    assertNotNull(plan);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("/v1/plans/%s", RESOURCE_ID)
    );
  }

  @Test
  public void testUpdate() throws StripeException {
    Plan plan = Plan.retrieve(RESOURCE_ID);

    Map<String, Object> params = new HashMap<String, Object>();
    params.put("name", "Updated Name");

    Plan updatedPlan = plan.update(params);

    assertNotNull(updatedPlan);
    verifyRequest(
        APIResource.RequestMethod.POST,
        String.format("/v1/plans/%s", plan.getId()),
        params
    );
  }

  @Test
  public void testDelete() throws StripeException {
    Plan plan = Plan.retrieve(RESOURCE_ID);

    DeletedPlan deletedPlan = plan.delete();

    assertNotNull(deletedPlan);
    verifyRequest(
        APIResource.RequestMethod.DELETE,
        String.format("/v1/plans/%s", plan.getId())
    );
  }

  @Test
  public void testList() throws StripeException {
    PlanCollection plans = Plan.list(null);

    assertNotNull(plans);
    assertEquals(1, plans.getData().size());
    verifyRequest(
        APIResource.RequestMethod.GET,
        "/v1/plans"
    );
  }
}
