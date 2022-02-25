package com.example.demo.common;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.OutputStream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class HttpUtils_CheckDirectoryNameTest {
  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();
  public OutputStream stream = mock(OutputStream.class);
  
  @Test
  public void testNormal() throws Exception {
    boolean result = false;
    result = HttpUtils.checkDirectoryName(tempFolder.getRoot().getAbsolutePath());
    assertTrue(result);
  }

  @Test
  public void testDirectoryTraversalPattern1() throws Exception {
    boolean result = false;
    String path = tempFolder.getRoot().getAbsolutePath() + "..\\test.txt";
    result = HttpUtils.checkDirectoryName(path);
    assertFalse(result);
  }

  @Test
  public void testDirectoryTraversalPattern2() throws Exception {
    boolean result = false;
    String path = tempFolder.getRoot().getAbsolutePath() + "../test.txt";
    result = HttpUtils.checkDirectoryName(path);
    assertFalse(result);
  }

  @Test
  public void testDirectoryTraversalPattern3() throws Exception {
    boolean result = false;
    String path = tempFolder.getRoot().getAbsolutePath() + "/test.txt";
    result = HttpUtils.checkDirectoryName(path);
    assertFalse(result);
  }
}
