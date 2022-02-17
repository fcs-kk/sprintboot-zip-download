package com.example.demo.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *  圧縮・解凍ユーティリティークラス
 */
public class CompressUtils {
/**
   * ディレクトリを ZIP アーカイブし、Streamに出力する
   * 日本語ファイル名対応
   *
   * @param outStream OutputStream
   * @param rootDirectory 圧縮するディレクトリ ( 例; C:/sample )
   * @return 処理結果 true:圧縮成功 false:圧縮失敗
   */
  public static boolean compressDirectory( OutputStream outStream, File rootDirectory ) 
  {
    boolean retVal = false; // 返り値

    ZipOutputStream outZip = null;
    try {

      // ZIPファイル出力オブジェクト作成
      outZip = new ZipOutputStream(outStream);
      archive(outZip, rootDirectory, rootDirectory);
      // 返り値に成功をセット
      retVal = true;

    } catch ( Exception e ) {

      // 返り値に失敗をセット
      retVal = false;

    } finally {
      // ZIPエントリクローズ
      if ( outZip != null ) {
        try { outZip.closeEntry(); } catch (Exception e) {}
        try { outZip.flush(); } catch (Exception e) {}
        try { outZip.close(); } catch (Exception e) {}
      }
    }

    // 結果を返す
    return retVal;
  }
 
    /**
   * ファイルを ZIP アーカイブし、Streamに出力する
   * 日本語ファイル名対応
   *
   * @param outStream OutputStream
   * @param fileList 圧縮するファイルリスト  ( 例; {C:/sample1.txt, C:/sample2.txt} )
   * @return 処理結果 true:圧縮成功 false:圧縮失敗
   */
  public static boolean compressFileList( OutputStream outStream, ArrayList<File> files ) 
  {
    boolean retVal = false; // 返り値

    ZipOutputStream outZip = null;
    try {

      // ZIPファイル出力オブジェクト作成
      outZip = new ZipOutputStream(outStream);
      // 圧縮ファイルリストのファイルを連続圧縮
      for (File file : files) {
        // ファイルオブジェクト作成
        archive(outZip, file, file.getName());
      }
      // 返り値に成功をセット
      retVal = true;

    } catch ( Exception e ) {

      // 返り値に失敗をセット
      retVal = false;

    } finally {
      // ZIPエントリクローズ
      if ( outZip != null ) {
        try { outZip.closeEntry(); } catch (Exception e) {}
        try { outZip.flush(); } catch (Exception e) {}
        try { outZip.close(); } catch (Exception e) {}
      }
    }

    // 結果を返す
    return retVal;
  }
 
  /**
   * ディレクトリ圧縮のための再帰処理
   *
   * @param outZip ZipOutputStream
   * @param rootDir 圧縮ルートディレクトリ
   * @param targetDir 圧縮対象ディレクトリ
   */
  private static void archive(ZipOutputStream outZip, File rootDir, File targetDir) {
    try {
      if ( targetDir.isDirectory() ) {
        File[] files = targetDir.listFiles();
        for (File target : files) {
          String entryName = getEntryName(rootDir.getAbsolutePath(), target.getAbsolutePath());
          if ( target.isDirectory() ) {
            // ディレクトリを登録
            ZipEntry ze = new ZipEntry(entryName + "/");
            outZip.putNextEntry(ze);
            outZip.closeEntry();

            // 再帰呼び出し
            archive(outZip, rootDir, target);
          } else {
            // 圧縮処理
            archive(outZip, target, entryName);
          }
        }
      }
    } catch (Exception ex) {

    }
  }
 
  /**
   * 圧縮処理
   *
   * @param outZip ZipOutputStream
   * @param targetFile 圧縮対象ファイル
   * @parma entryName 登録用ファイル名
   */
  private static boolean archive(ZipOutputStream outZip, File targetFile, String entryName) {
    // 圧縮レベル設定
    outZip.setLevel(5);
 
    try {
 
      // ZIPエントリ作成
      outZip.putNextEntry(new ZipEntry(entryName));

      // 圧縮ファイル読み込みストリーム取得
      BufferedInputStream in = new BufferedInputStream(new FileInputStream(targetFile));

      // 圧縮ファイルをZIPファイルに出力
      int readSize = 0;
      byte buffer[] = new byte[1024]; // 読み込みバッファ
      while ((readSize = in.read(buffer, 0, buffer.length)) != -1) {
        outZip.write(buffer, 0, readSize);
      }
      // クローズ処理
      in.close();

      // ZIPエントリクローズ
      outZip.closeEntry();
    } catch ( Exception e ) {
      // ZIP圧縮失敗
      return false;
    }
    return true;
  }
  
  /**
   * ZipEntry登録用の名前を首都lkう
   * ※圧縮ディレクトリ配下のパスを返す
   *
   * @param rootPath ルートディレクトリ
   * @param filePath フォルダ名
   * @return ファイル名
   */
  private static String getEntryName(String rootPath, String filePath)
  {
    String retVal = "";
    retVal = filePath.substring(rootPath.length() + 1, filePath.length());
    return retVal;
  }
}
