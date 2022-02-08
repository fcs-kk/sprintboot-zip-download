package com.example.demo.common;

import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 *  HTTPユーティリティクラス
 */
public class HttpUtils {

        /**
	 *  ディレクトリダウンロード
	 *  @version 1.0
	 *  @param HttpServletResponse レスポンス
	 *  @param List<File> ダウンロードファイルリスト
	 *  @return boolean 結果（true:成功、false:失敗）
	 */
    public static Boolean doComplessionFolderDownload (
        HttpServletResponse response, 
        File dir
        ) throws Exception {

        Boolean retVal = false; // 返り値

        // パラメータチェック
        if (dir.isDirectory() == false) {
            return retVal;
        }

        // ダウンロード対象のコンテンツタイプを指定
        response.setContentType("application/zip");
        // 文字コードを指定
        response.setCharacterEncoding("windows-31j");
        // ヘッダ情報をセットする。
        response.setHeader(
                    HttpHeaders.CONTENT_TYPE,
                    MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=" + URLEncoder.encode("download.zip", "windows-31j"));

        // ZIP圧縮
        retVal = CompressUtils.compressDirectory(response.getOutputStream(), dir);

        // 結果を返す
        return retVal;
    }
    
    /**
	 *  複数ファイルダウンロード
	 *  @version 1.0
	 *  @param HttpServletResponse レスポンス
	 *  @param List<File> ダウンロードファイルリスト
	 *  @return boolean 結果（true:成功、false:失敗）
	 */
    public static Boolean doMultiplueFileDownload (
        HttpServletResponse response, 
        ArrayList<File> files
        ) throws Exception {

        Boolean retVal = false; // 返り値

        // パラメータチェック
        if (files == null || files.size() <= 0) {
            return retVal;
        }

        // ダウンロード対象のコンテンツタイプを指定
        response.setContentType("application/zip");
        // 文字コードを指定
        response.setCharacterEncoding("windows-31j");
        // ヘッダ情報をセットする。
        response.setHeader(
                    HttpHeaders.CONTENT_TYPE,
                    MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=" + URLEncoder.encode("download.zip", "windows-31j"));

        // ZIP圧縮
        retVal = CompressUtils.compressFileList(response.getOutputStream(), files);

        // 結果を返す
        return retVal;
    }

    /**
	 *  単一ファイルダウンロード
	 *  @version 1.0
	 *  @param HttpServletResponse レスポンス
	 *  @param File ダウンロードファイル
	 *  @return boolean 結果（true:成功、false:失敗）
	 */
    public static Boolean doSingleFileDownload(
        HttpServletResponse response,
        File file
        ) throws Exception {

        Boolean retVal = false;

        // パラメータチェック
        if (file == null || file.exists() == false) {
            return retVal;
        }

        // ファイルをセット
        try(OutputStream outputStream = response.getOutputStream();) {

            // ファイル取得
            Path filePath = Paths.get(file.getPath());

            // コンテンツタイプを指定
            response.setContentType("application/octet-stream");
            // 文字コードを指定
            response.setCharacterEncoding("windows-31j");
            // ヘッダ情報をセット
            response.setHeader(HttpHeaders.CONTENT_TYPE,
                    MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=" + URLEncoder.encode(file.getName(), "UTF-8"));

            // データセット
            byte[] buff = Files.readAllBytes(filePath);
            response.setContentLength(buff.length);

            // 書き込み
            outputStream.write(buff);
            outputStream.flush();

            // 返り値に成功をセット
            retVal = true;

        } catch (Exception e) {
            // エラー処理
        }

        // 結果を返す
        return retVal;
    }	
}
