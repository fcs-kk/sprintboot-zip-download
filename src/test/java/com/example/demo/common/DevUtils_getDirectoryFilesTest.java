package com.example.demo.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class DevUtils_getDirectoryFilesTest {
  @Rule
  public TemporaryFolder tempFolder = new TemporaryFolder();
  
  @Test
  public void testNormalFindAll() throws Exception {
    File file1 = new File(tempFolder.getRoot().getAbsolutePath() + "\\test1.csv");
    file1.createNewFile();
    File file2 = new File(tempFolder.getRoot().getAbsolutePath() + "\\test2.txt");
    file2.createNewFile();
    ArrayList<File> files = new ArrayList<File>();
    files.add(file1);
    files.add(file2);
    List<String> result = DevUtils.getDirectoryFiles(tempFolder.getRoot().getAbsolutePath(), "");
    assertEquals(result.size(), 2);
  }

  @Test
  public void testNormalFindExtension() throws Exception {
    File file1 = new File(tempFolder.getRoot().getAbsolutePath() + "\\test1.txt");
    file1.createNewFile();
    File file2 = new File(tempFolder.getRoot().getAbsolutePath() + "\\test2.txt");
    file2.createNewFile();
    File subDir = new File(tempFolder.getRoot().getAbsolutePath()+ "\\sub1\\sub2" );
    Path path = Files.createDirectories(subDir.toPath());

    ArrayList<File> files = new ArrayList<File>();
    files.add(file1);
    files.add(file2);
    files.add(path.toFile());
    List<String> result = DevUtils.getDirectoryFiles(tempFolder.getRoot().getAbsolutePath(), "txt");
    assertEquals(result.size(), 2);
  }

  @Test
  public void testNormalNotFond() throws Exception {
    File file1 = new File(tempFolder.getRoot().getAbsolutePath() + "\\test1.txt");
    file1.createNewFile();
    File file2 = new File(tempFolder.getRoot().getAbsolutePath() + "\\test2.txt");
    file2.createNewFile();
    ArrayList<File> files = new ArrayList<File>();
    files.add(file1);
    files.add(file2);
    List<String> result = DevUtils.getDirectoryFiles(tempFolder.getRoot().getAbsolutePath(), "csv");
    assertEquals(result.size(), 0);
  }

  @Test
  public void testPathParameterIsNull() throws Exception {
    List<String> result = DevUtils.getDirectoryFiles(null, "");
    assertEquals(result.size(), 0);
  }

  @Test
  public void testExtensionParameterIsNull() throws Exception {
    File file1 = new File(tempFolder.getRoot().getAbsolutePath() + "\\test1.csv");
    file1.createNewFile();
    File file2 = new File(tempFolder.getRoot().getAbsolutePath() + "\\test2.txt");
    file2.createNewFile();
    ArrayList<File> files = new ArrayList<File>();
    files.add(file1);
    files.add(file2);
    List<String> result = DevUtils.getDirectoryFiles(tempFolder.getRoot().getAbsolutePath(), null);
    assertEquals(result.size(), 0);
  }
}
