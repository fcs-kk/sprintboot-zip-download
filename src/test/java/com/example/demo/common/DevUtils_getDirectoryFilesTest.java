package com.example.demo.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class DevUtils_getDirectoryFilesTest {
  @TempDir 
  Path tempFolder;
  
  @Test
  public void testNormalFindAll() throws Exception {
    File file1 = new File(tempFolder.toFile().getAbsolutePath() + "\\test1.csv");
    file1.createNewFile();
    File file2 = new File(tempFolder.toFile().getAbsolutePath() + "\\test2.txt");
    file2.createNewFile();
    ArrayList<File> files = new ArrayList<File>();
    files.add(file1);
    files.add(file2);
    List<String> result = DevUtils.getDirectoryFiles(tempFolder.toFile().getAbsolutePath(), "");
    assertEquals(result.size(), 2);
  }

  @Test
  public void testNormalFindExtension() throws Exception {
    File file1 = new File(tempFolder.toFile().getAbsolutePath() + "\\test1.txt");
    file1.createNewFile();
    File file2 = new File(tempFolder.toFile().getAbsolutePath() + "\\test2.txt");
    file2.createNewFile();
    File subDir = new File(tempFolder.toFile().getAbsolutePath()+ "\\sub1\\sub2" );
    Path path = Files.createDirectories(subDir.toPath());

    ArrayList<File> files = new ArrayList<File>();
    files.add(file1);
    files.add(file2);
    files.add(path.toFile());
    List<String> result = DevUtils.getDirectoryFiles(tempFolder.toFile().getAbsolutePath(), "txt");
    assertEquals(result.size(), 2);
  }

  @Test
  public void testNormalNotFond() throws Exception {
    File file1 = new File(tempFolder.toFile().getAbsolutePath() + "\\test1.txt");
    file1.createNewFile();
    File file2 = new File(tempFolder.toFile().getAbsolutePath() + "\\test2.txt");
    file2.createNewFile();
    ArrayList<File> files = new ArrayList<File>();
    files.add(file1);
    files.add(file2);
    List<String> result = DevUtils.getDirectoryFiles(tempFolder.toFile().getAbsolutePath(), "csv");
    assertEquals(result.size(), 0);
  }

  @Test
  public void testPathParameterIsNull() throws Exception {
    List<String> result = DevUtils.getDirectoryFiles(null, "");
    assertEquals(result.size(), 0);
  }

  @Test
  public void testExtensionParameterIsNull() throws Exception {
    File file1 = new File(tempFolder.toFile().getAbsolutePath() + "\\test1.csv");
    file1.createNewFile();
    File file2 = new File(tempFolder.toFile().getAbsolutePath() + "\\test2.txt");
    file2.createNewFile();
    ArrayList<File> files = new ArrayList<File>();
    files.add(file1);
    files.add(file2);
    List<String> result = DevUtils.getDirectoryFiles(tempFolder.toFile().getAbsolutePath(), null);
    assertEquals(result.size(), 0);
  }
}
