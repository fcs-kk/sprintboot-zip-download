package com.example.demo.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.mock.web.MockHttpServletResponse;

public class HttpUtils_doComplessionFolderTest {
  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();
  MockHttpServletResponse response = new MockHttpServletResponse();
  
  @Test
  public void testNormal() throws Exception {
    boolean result = false;
    File path = new File(tempFolder.getRoot().getAbsolutePath());
    result = HttpUtils.doComplessionFolderDownload(response, path);
    assertEquals(response.getCharacterEncoding(), "windows-31j");
    assertEquals(response.getHeaderValue("Content-Type"), "application/octet-stream;charset=windows-31j");
    assertEquals(response.getHeaderValue("Content-Disposition"), "attachment; filename=download.zip");
    assertTrue(result);
  }

  @Test
  public void testDirectoryIsFile() throws Exception {
    boolean result = false;
    File file = new File(tempFolder.getRoot().getAbsolutePath() + "\\test.txt");
    file.createNewFile();
    result = HttpUtils.doComplessionFolderDownload(response, file);
    assertFalse(result);
  }

  @Test
  public void testDirectoryNotExists() throws Exception {
    boolean result = false;
    File path = new File(tempFolder.getRoot().getAbsolutePath() + "\\non");
    result = HttpUtils.doComplessionFolderDownload(response, path);
    assertFalse(result);
  }

  @Test
  public void testDirectoryParameterIsNull() throws Exception {
    boolean result = false;
    result = HttpUtils.doComplessionFolderDownload(response, null);
    assertFalse(result);
  }

  @Test
  public void testResponseParameterIsNull() throws Exception {
    boolean result = false;
    File path = new File(tempFolder.getRoot().getAbsolutePath());
    result = HttpUtils.doComplessionFolderDownload(null, path);
    assertFalse(result);
  }
}
