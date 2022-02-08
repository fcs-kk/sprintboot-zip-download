package com.example.demo.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import com.example.demo.model.SelectForm;
import com.example.demo.common.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *  メインコントローラクラス
 */
@Controller
public class MainController 
{
	@Bean // ファイルアップロード機能を有効化する
	public MultipartResolver multipartResolver() {
	MultipartResolver retVal = new StandardServletMultipartResolver();
			return retVal;
	}

	/**
	 *  ダウンロード画面
	 *  @version 1.0
	 *  @param SelectForm ダウンロードデータモデル
	 *  @param Model 画面データモデル
	 *  @return string 画面
	 */
	@RequestMapping(value = "/selector", method = RequestMethod.GET)
	public String index(SelectForm form, Model model) 
	{
		// ファイルリストを表示モデルにセット
		model.addAttribute("fileItems", getDataFiles());

		// フォルダリストを表示モデルにセット
		model.addAttribute("folderItems", getDataFolders());

		// 選択画面を表示
		return "selector";
	}

		/**
	 *  過去データファイルのリストを取得
	 *  @version 1.0
	 *  @param なし
	 *  @return Map<String,String> 過去データファイル名リスト
	 */
	private Map<String,String> getDataFiles() {
		Map<String, String> retVal = new LinkedHashMap<String, String>();

		List<String>files = DevUtils.getDirectoryFiles("d:\\", "csv");
		for (String fileName : files) {
			retVal.put(fileName, fileName);
		}

		// 過去データファイルのリストを返す
		return retVal;
	} 

			/**
	 *  過去データフォルダのリストを取得
	 *  @version 1.0
	 *  @param なし
	 *  @return Map<String,String> 過去データフォルダ名リスト
	 */
	private Map<String,String> getDataFolders() {
		Map<String, String> retVal = new LinkedHashMap<String, String>();

		List<String>files = DevUtils.getDirectoryFolders("d:\\");
		for (String fileName : files) {
			retVal.put(fileName, fileName);
		}

		// 過去データフォルダのリストを返す
		return retVal;
	} 
}
