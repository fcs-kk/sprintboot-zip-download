package com.example.demo.common;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockHttpServletResponse;

public class HttpUtils_doMultiplueFileDownloadTest {
  @TempDir 
  Path tempFolder;
  MockHttpServletResponse response = new MockHttpServletResponse();
  
  @Test
  void testNormal() throws Exception {
    boolean result = false;
    File file1 = new File(tempFolder.toFile().getAbsolutePath() + "\\test1.txt");
    file1.createNewFile();
    File file2 = new File(tempFolder.toFile().getAbsolutePath() + "\\test2.txt");
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
  void testResponseParameterIsNull() throws Exception {
    boolean result = false;
    File file1 = new File(tempFolder.toFile().getAbsolutePath() + "\\test1.txt");
    file1.createNewFile();
    File file2 = new File(tempFolder.toFile().getAbsolutePath() + "\\test2.txt");
    file2.createNewFile();
    ArrayList<File> files = new ArrayList<File>();
    files.add(file1);
    files.add(file2);
    result = HttpUtils.doMultiplueFileDownload(null, files);
    assertFalse(result);
  }

  @Test
  void testFilesParameterIsNull() throws Exception {
    boolean result = false;
    result = HttpUtils.doMultiplueFileDownload(response, null);
    assertFalse(result);
  }

  @Test
  void testFilesParameterIsZero() throws Exception {
    boolean result = false;
    ArrayList<File> files = new ArrayList<File>();
    result = HttpUtils.doMultiplueFileDownload(response, files);
    assertFalse(result);
  }

  @Test
  void testFileNotExists() throws Exception {
    boolean result = false;
    File file1 = new File(tempFolder.toFile().getAbsolutePath() + "\\test1.txt");
    file1.createNewFile();
    File file2 = new File(tempFolder.toFile().getAbsolutePath() + "\\none.txt");
    ArrayList<File> files = new ArrayList<File>();
    files.add(file1);
    files.add(file2);
    result = HttpUtils.doMultiplueFileDownload(response, files);
    assertFalse(result);
  }
}
