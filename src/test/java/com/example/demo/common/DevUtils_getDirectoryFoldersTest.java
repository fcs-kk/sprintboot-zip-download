package com.example.demo.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class DevUtils_getDirectoryFoldersTest {
  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();
  
  @Test
  public void testNormalFind() throws Exception {
    File file1 = new File(tempFolder.getRoot().getAbsolutePath() + "\\test1.txt");
    file1.createNewFile();
    File file2 = new File(tempFolder.getRoot().getAbsolutePath() + "\\test2.txt");
    file2.createNewFile();
    File subDir = new File(tempFolder.getRoot().getAbsolutePath()+ "\\sub1\\sub2" );
    Files.createDirectories(subDir.toPath());
    List<String> result = DevUtils.getDirectoryFolders(tempFolder.getRoot().getAbsolutePath());
    assertEquals(result.size(), 1);
  }

  @Test
  public void testNormalNotFind() throws Exception {
    File file1 = new File(tempFolder.getRoot().getAbsolutePath() + "\\test1.csv");
    file1.createNewFile();
    File file2 = new File(tempFolder.getRoot().getAbsolutePath() + "\\test2.txt");
    file2.createNewFile();
    List<String> result = DevUtils.getDirectoryFolders(tempFolder.getRoot().getAbsolutePath());
    assertEquals(result.size(), 0);
  }

  @Test
  public void testPathParameterIsNull() throws Exception {
    List<String> result = DevUtils.getDirectoryFolders(null);
    assertEquals(result.size(), 0);
  }

  @Test
  public void testPathParameterIsFile() throws Exception {
    File file1 = new File(tempFolder.getRoot().getAbsolutePath() + "\\test1.csv");
    file1.createNewFile();
    List<String> result = DevUtils.getDirectoryFolders(file1.getPath());
    assertEquals(result.size(), 0);
  }
}
