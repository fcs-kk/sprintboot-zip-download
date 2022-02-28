package com.example.demo.common;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class CompressUtilsTest  {
  @TempDir 
  Path tempFolder;

  @Test
  void testCompressionRootDirectory() throws Exception {
    boolean result = false;
    createTestFileInRootDirectory("test.txt");
    OutputStream stream = mock(OutputStream.class);
    result = CompressUtils.compressDirectory(stream, tempFolder.toFile());
    assertTrue(result);
  }

  @Test
  void testCompressionSubDirectory() throws Exception {
    boolean result = false;
    createTestFileInSubDirectory("test.txt");
    OutputStream stream = mock(OutputStream.class);
    result = CompressUtils.compressDirectory(stream, tempFolder.toFile());
    assertTrue(result);
  }
  
  @Test
  void testStreamParameterIsNull() throws Exception {
    boolean result = false;
    result = CompressUtils.compressDirectory(null, tempFolder.toFile());
    assertFalse(result);
  }

  @Test
  void testDirectoryParameterIsNull() throws Exception {
    boolean result = false;
    OutputStream stream = mock(OutputStream.class);
    result = CompressUtils.compressDirectory(stream, null);
    assertFalse(result);
  }

  @Test
  void testDirectoryIsNothing() throws Exception {
    boolean result = false;
    File nonDir = new File(tempFolder.toFile().getAbsolutePath() + "\\non");
    OutputStream stream = mock(OutputStream.class);
    result = CompressUtils.compressDirectory(stream, nonDir);
    assertFalse(result);
  }

  /*
   * テンポラリフォルダにファイル作成
   */
  private void createTestFileInRootDirectory(String fileName) throws Exception {
    File dir = tempFolder.toFile();
    assertTrue(dir.exists());
    File file = new File(dir.getAbsolutePath() + "\\" + fileName);
    file.createNewFile();
    assertTrue(file.exists());
  }

    /*
   * テンポラリフォルダにファイル作成
   */
  private void createTestFileInSubDirectory(String fileName) throws Exception {
    File dir = tempFolder.toFile();
    assertTrue(dir.exists());
    File subDir = new File(dir.getParentFile()+ "\\sub1\\sub2" );
    Path path = Files.createDirectories(subDir.toPath());
    File file = new File(path.toAbsolutePath() + "\\" + fileName);
    file.createNewFile();
    assertTrue(file.exists());
  }
}
