package com.stripe.model;

import static org.junit.Assert.assertNotNull;

import com.stripe.BaseStripeMockTest;
import com.stripe.net.APIResource;

import org.junit.Test;

public class FileUploadTest extends BaseStripeMockTest {
  @Test
  public void testDeserialize() throws Exception {
    String data = getResourceAsString("/api_fixtures/file_upload.json");
    FileUpload fileUpload = APIResource.GSON.fromJson(data, FileUpload.class);
    assertNotNull(fileUpload);
    assertNotNull(fileUpload.getId());
  }
}
