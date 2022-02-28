package com.example.demo.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class DevUtils_getDirectoryFoldersTest {
  @TempDir 
  Path tempFolder;
  
  @Test
  void testNormalFind() throws Exception {
    File file1 = new File(tempFolder.toFile().getAbsolutePath() + "\\test1.txt");
    file1.createNewFile();
    File file2 = new File(tempFolder.toFile().getAbsolutePath() + "\\test2.txt");
    file2.createNewFile();
    File subDir = new File(tempFolder.toFile().getAbsolutePath()+ "\\sub1\\sub2" );
    Files.createDirectories(subDir.toPath());
    List<String> result = DevUtils.getDirectoryFolders(tempFolder.toFile().getAbsolutePath());
    assertEquals(result.size(), 1);
  }

  @Test
  void testNormalNotFind() throws Exception {
    File file1 = new File(tempFolder.toFile().getAbsolutePath() + "\\test1.csv");
    file1.createNewFile();
    File file2 = new File(tempFolder.toFile().getAbsolutePath() + "\\test2.txt");
    file2.createNewFile();
    List<String> result = DevUtils.getDirectoryFolders(tempFolder.toFile().getAbsolutePath());
    assertEquals(result.size(), 0);
  }

  @Test
  void testPathParameterIsNull() throws Exception {
    List<String> result = DevUtils.getDirectoryFolders(null);
    assertEquals(result.size(), 0);
  }

  @Test
  void testPathParameterIsFile() throws Exception {
    File file1 = new File(tempFolder.toFile().getAbsolutePath() + "\\test1.csv");
    file1.createNewFile();
    List<String> result = DevUtils.getDirectoryFolders(file1.getPath());
    assertEquals(result.size(), 0);
  }
}
