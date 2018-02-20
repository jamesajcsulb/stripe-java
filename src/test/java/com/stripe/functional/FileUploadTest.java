package com.stripe.functional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.FileUpload;
import com.stripe.model.FileUploadCollection;
import com.stripe.net.APIResource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class FileUploadTest extends BaseStripeMockTest {
  public static final String RESOURCE_ID = "file_123";

  @Test
  public void testCreate() throws IOException, StripeException {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("purpose", "dispute_evidence");
    params.put("file", new File(getClass().getResource("/test.png").getFile()));

    // stripe-mock does not support the /v1/files endpoint, so we stub the request
    stubRequest(
        APIResource.RequestMethod.POST,
        String.format("%s/v1/files", Stripe.UPLOAD_API_BASE),
        params,
        FileUpload.class,
        getResourceAsString("/api_fixtures/file_upload.json")
    );
    FileUpload fileUpload = FileUpload.create(params);

    assertNotNull(fileUpload);
    verifyRequest(
      APIResource.RequestMethod.POST,
      String.format("%s/v1/files", Stripe.UPLOAD_API_BASE),
      params,
      APIResource.RequestType.MULTIPART,
      null
    );
  }

  @Test
  public void testRetrieve() throws IOException, StripeException {
    // stripe-mock does not support the /v1/files endpoint, so we stub the request
    stubRequest(
        APIResource.RequestMethod.GET,
        String.format("%s/v1/files/%s", Stripe.UPLOAD_API_BASE, RESOURCE_ID),
        FileUpload.class,
        getResourceAsString("/api_fixtures/file_upload.json")
    );
    FileUpload fileUpload = FileUpload.retrieve(RESOURCE_ID);

    assertNotNull(fileUpload);
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("%s/v1/files/%s", Stripe.UPLOAD_API_BASE, RESOURCE_ID)
    );
  }

  @Test
  public void testList() throws IOException, StripeException {
    // stripe-mock does not support the /v1/files endpoint, so we stub the request
    stubRequest(
        APIResource.RequestMethod.GET,
        String.format("%s/v1/files", Stripe.UPLOAD_API_BASE),
        FileUploadCollection.class,
        getResourceAsString("/api_fixtures/file_upload_list.json")
    );
    FileUploadCollection fileUploads = FileUpload.list(null);

    assertNotNull(fileUploads);
    assertEquals(1, fileUploads.getData().size());
    verifyRequest(
        APIResource.RequestMethod.GET,
        String.format("%s/v1/files", Stripe.UPLOAD_API_BASE)
    );
  }
}
