package com.example.demo.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.example.demo.common.HttpUtils;
import com.example.demo.model.SelectForm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *  ダウンロードコントローラクラス
 */
@Controller
public class DownloadController {

    /**
	 *  複数ダウンロード処理
	 *  @version 1.0
	 *  @param SelectForm ダウンロードデータモデル
	 *  @param BindingResult バインド結果
	 *  @param Model 画面データモデル
     *  @param HttpServletResponse レスポンス
	 *  @return string 画面
	 */
	@RequestMapping(value="/downloads", params="zip", method = RequestMethod.POST)
	public Boolean doZipDownload(
                        @Validated @ModelAttribute SelectForm form, 
                        BindingResult result, 
                        Model model, 
                        HttpServletResponse response)  {

        Boolean retVal = false; // 返り値
        List<File> files = new ArrayList<>();
        
        // パラメータチェック
        if (form.getSelectedItems() == null)
        {
            return retVal;
        }
        else {
            for (String filename : form.getSelectedItems()) {
                String filePath = "D:\\"+filename;
                File file = new File(filePath);
                if (file.isFile() == true) {
                    files.add(file);
                }
            }
        }

        // ファイル数チェック
        if (files.size() <= 0) {
            return retVal;
        }

        try {
            // ダウンロード処理
            HttpUtils.doMultiplueFileDownload(response, files);
        }
        catch (Exception e) {
            // エラー処理
            retVal = false;
        }

        // 結果を返す
        return retVal;
	}

    /**
	 *  単一ダウンロード処理
	 *  @version 1.0
	 *  @param SelectForm ダウンロードデータモデル
	 *  @param BindingResult バインド結果
	 *  @param Model 画面データモデル
     *  @param HttpServletResponse レスポンス
	 *  @return string 画面
	 */
	@RequestMapping(value="/downloads", params="file", method = RequestMethod.POST)
	public Boolean doFileDownload(
                    @Validated @ModelAttribute SelectForm form, 
                        BindingResult result, 
                        Model model, 
                        HttpServletResponse response) throws Exception {

        Boolean retVal = false; // 返り値
        String fileName = "";

        // パラメータチェック
        if (form.getSelectedItems() != null)
        {
            fileName = form.getSelectedItems()[0];
        }
        else if (form.getSelectedItem() != null) {
            fileName = form.getSelectedItem();
        }

        // ファイルチェック
        File file = new File("d:\\" + fileName);
        if (file.isFile() == false) {
            return retVal;
        }

        try {
            // ダウンロード処理
            retVal = HttpUtils.doSingleFileDownload(response, file);
        }
        catch (Exception ex) {
            // エラー処理
            retVal = false;
        }

        // 結果を返す
        return retVal;
	}	
}
