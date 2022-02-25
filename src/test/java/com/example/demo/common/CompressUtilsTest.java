package com.example.demo.common;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class CompressUtilsTest  {
  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();

  @Test
  public void testCompressionRootDirectory() throws Exception {
    boolean result = false;
    createTestFileInRootDirectory("test.txt");
    OutputStream stream = mock(OutputStream.class);
    result = CompressUtils.compressDirectory(stream, tempFolder.getRoot());
    assertTrue(result);
  }

  @Test
  public void testCompressionSubDirectory() throws Exception {
    boolean result = false;
    createTestFileInSubDirectory("test.txt");
    OutputStream stream = mock(OutputStream.class);
    result = CompressUtils.compressDirectory(stream, tempFolder.getRoot());
    assertTrue(result);
  }
  
  @Test
  public void testStreamParameterIsNull() throws Exception {
    boolean result = false;
    result = CompressUtils.compressDirectory(null, tempFolder.getRoot());
    assertFalse(result);
  }

  @Test
  public void testDirectoryParameterIsNull() throws Exception {
    boolean result = false;
    OutputStream stream = mock(OutputStream.class);
    result = CompressUtils.compressDirectory(stream, null);
    assertFalse(result);
  }

  @Test
  public void testDirectoryIsNothing() throws Exception {
    boolean result = false;
    File nonDir = new File(tempFolder.getRoot().getAbsolutePath() + "\\non");
    OutputStream stream = mock(OutputStream.class);
    result = CompressUtils.compressDirectory(stream, nonDir);
    assertFalse(result);
  }

  /*
   * テンポラリフォルダにファイル作成
   */
  private void createTestFileInRootDirectory(String fileName) throws Exception {
    File dir = tempFolder.getRoot();
    assertTrue(dir.exists());
    tempFolder.newFile(fileName);
    File file = new File(dir.getAbsolutePath() + "\\" + fileName);
    assertTrue(file.exists());
  }

    /*
   * テンポラリフォルダにファイル作成
   */
  private void createTestFileInSubDirectory(String fileName) throws Exception {
    File dir = tempFolder.getRoot();
    assertTrue(dir.exists());
    File subDir = new File(dir.getParentFile()+ "\\sub1\\sub2" );
    Path path = Files.createDirectories(subDir.toPath());
    File file = new File(path.toAbsolutePath() + "\\" + fileName);
    file.createNewFile();
    assertTrue(file.exists());
  }
}
