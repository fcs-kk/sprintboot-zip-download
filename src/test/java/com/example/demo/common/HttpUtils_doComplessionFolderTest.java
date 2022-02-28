package com.example.demo.common;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockHttpServletResponse;

public class HttpUtils_doComplessionFolderTest {
  @TempDir 
  Path tempFolder;
  MockHttpServletResponse response = new MockHttpServletResponse();
  
  @Test
  void testNormal() throws Exception {
    boolean result = false;
    File path = new File(tempFolder.toFile().getAbsolutePath());
    result = HttpUtils.doComplessionFolderDownload(response, path);
    assertEquals(response.getCharacterEncoding(), "windows-31j");
    assertEquals(response.getHeaderValue("Content-Type"), "application/octet-stream;charset=windows-31j");
    assertEquals(response.getHeaderValue("Content-Disposition"), "attachment; filename=download.zip");
    assertTrue(result);
  }

  @Test
  void testDirectoryIsFile() throws Exception {
    boolean result = false;
    File file = new File(tempFolder.toFile().getAbsolutePath() + "\\test.txt");
    file.createNewFile();
    result = HttpUtils.doComplessionFolderDownload(response, file);
    assertFalse(result);
  }

  @Test
  void testDirectoryNotExists() throws Exception {
    boolean result = false;
    File path = new File(tempFolder.toFile().getAbsolutePath() + "\\non");
    result = HttpUtils.doComplessionFolderDownload(response, path);
    assertFalse(result);
  }

  @Test
  void testDirectoryParameterIsNull() throws Exception {
    boolean result = false;
    result = HttpUtils.doComplessionFolderDownload(response, null);
    assertFalse(result);
  }

  @Test
  void testResponseParameterIsNull() throws Exception {
    boolean result = false;
    File path = new File(tempFolder.toFile().getAbsolutePath());
    result = HttpUtils.doComplessionFolderDownload(null, path);
    assertFalse(result);
  }
}
