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
   * @param directory 圧縮するディレクトリ ( 例; C:/sample )
   * @return 処理結果 true:圧縮成功 false:圧縮失敗
   */
  public static boolean compressDirectory( OutputStream outStream, File directory ) 
  {
    boolean retVal = false; // 返り値

    ZipOutputStream outZip = null;
    try {

      // ZIPファイル出力オブジェクト作成
      outZip = new ZipOutputStream(outStream);
      archive(outZip, directory);
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
        archive(outZip, file, file.getName(), "Shift_JIS");
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
   * @param targetFile File 圧縮したいファイル
   */
  private static void archive(ZipOutputStream outZip, File targetFile) {
    if ( targetFile.isDirectory() ) {
      File[] files = targetFile.listFiles();
      for (File target : files) {
        if ( target.isDirectory() ) {
          archive(outZip, target);
        } else {
          // 圧縮処理
          archive(outZip, target, target.getName(), "Shift_JIS");
        }
      }
    }
  }
 
  /**
   * 圧縮処理
   *
   * @param outZip ZipOutputStream
   * @param targetFile File 圧縮したいファイル
   * @parma entryName 保存ファイル名
   * @param enc 文字コード
   */
  private static boolean archive(ZipOutputStream outZip, File targetFile, String entryName, String enc) {
    // 圧縮レベル設定
    outZip.setLevel(5);
 
    // // 文字コードを指定
    // outZip.setEncoding(enc);
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
}
