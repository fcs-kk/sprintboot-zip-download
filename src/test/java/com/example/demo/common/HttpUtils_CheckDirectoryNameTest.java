package com.example.demo.common;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

import java.io.OutputStream;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class HttpUtils_CheckDirectoryNameTest {
  @TempDir 
  Path tempFolder;
  OutputStream stream = mock(OutputStream.class);
  
  @Test
  void testNormal() throws Exception {
    boolean result = false;
    result = HttpUtils.checkDirectoryName(tempFolder.toFile().getAbsolutePath());
    assertTrue(result);
  }

  @Test
  void testDirectoryTraversalPattern1() throws Exception {
    boolean result = false;
    String path = tempFolder.toFile().getAbsolutePath() + "..\\test.txt";
    result = HttpUtils.checkDirectoryName(path);
    assertFalse(result);
  }

  @Test
  void testDirectoryTraversalPattern2() throws Exception {
    boolean result = false;
    String path = tempFolder.toFile().getAbsolutePath() + "../test.txt";
    result = HttpUtils.checkDirectoryName(path);
    assertFalse(result);
  }

  @Test
  void testDirectoryTraversalPattern3() throws Exception {
    boolean result = false;
    String path = tempFolder.toFile().getAbsolutePath() + "/test.txt";
    result = HttpUtils.checkDirectoryName(path);
    assertFalse(result);
  }
}
