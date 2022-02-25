package com.example.demo.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.springframework.mock.web.MockHttpServletResponse;

public class HttpUtils_doMultiplueFileDownloadTest {
  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();
  MockHttpServletResponse response = new MockHttpServletResponse();
  
  @Test
  public void testNormal() throws Exception {
    boolean result = false;
    File file1 = new File(tempFolder.getRoot().getAbsolutePath() + "\\test1.txt");
    file1.createNewFile();
    File file2 = new File(tempFolder.getRoot().getAbsolutePath() + "\\test2.txt");
    file2.createNewFile();
    ArrayList<File> files = new ArrayList<File>();
    files.add(file1);
    files.add(file2);
    result = HttpUtils.doMultiplueFileDownload(response, files);
    assertEquals(response.getCharacterEncoding(), "windows-31j");
    assertEquals(response.getHeaderValue("Content-Type"), "application/octet-stream;charset=windows-31j");
    assertEquals(response.getHeaderValue("Content-Disposition"), "attachment; filename=download.zip");
    assertTrue(result);
  }

  @Test
  public void testResponseParameterIsNull() throws Exception {
    boolean result = false;
    File file1 = new File(tempFolder.getRoot().getAbsolutePath() + "\\test1.txt");
    file1.createNewFile();
    File file2 = new File(tempFolder.getRoot().getAbsolutePath() + "\\test2.txt");
    file2.createNewFile();
    ArrayList<File> files = new ArrayList<File>();
    files.add(file1);
    files.add(file2);
    result = HttpUtils.doMultiplueFileDownload(null, files);
    assertFalse(result);
  }

  @Test
  public void testFilesParameterIsNull() throws Exception {
    boolean result = false;
    result = HttpUtils.doMultiplueFileDownload(response, null);
    assertFalse(result);
  }

  @Test
  public void testFilesParameterIsZero() throws Exception {
    boolean result = false;
    ArrayList<File> files = new ArrayList<File>();
    result = HttpUtils.doMultiplueFileDownload(response, files);
    assertFalse(result);
  }

  @Test
  public void testFileNotExists() throws Exception {
    boolean result = false;
    File file1 = new File(tempFolder.getRoot().getAbsolutePath() + "\\test1.txt");
    file1.createNewFile();
    File file2 = new File(tempFolder.getRoot().getAbsolutePath() + "\\none.txt");
    ArrayList<File> files = new ArrayList<File>();
    files.add(file1);
    files.add(file2);
    result = HttpUtils.doMultiplueFileDownload(response, files);
    assertFalse(result);
  }
}
