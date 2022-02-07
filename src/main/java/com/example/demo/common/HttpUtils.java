package com.example.demo.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;

/**
 *  HTTPユーティリティクラス
 */
public class HttpUtils {

    /**
	 *  複数ファイルダウンロード
	 *  @version 1.0
	 *  @param HttpServletResponse レスポンス
	 *  @param List<File> ダウンロードファイルリスト
	 *  @return boolean 結果（true:成功、false:失敗）
	 */
    public static Boolean doMultiplueFileDownload (
        HttpServletResponse response, 
        List<File> files
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

        // ファイルをセット
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
        // ZIPにファイルを登録
            for (File file : files) {
                try (InputStream input = new FileInputStream(file.getName())) {
                    zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
                    StreamUtils.copy(input, zipOutputStream); // write per 4KB
                }
            }
            // 返り値に成功をセット
            retVal = true;
        } catch (Exception e) {
            // エラー処理
            retVal = false;
        }

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
