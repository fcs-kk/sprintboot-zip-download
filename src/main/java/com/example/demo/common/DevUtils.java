package com.example.demo.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *  デバイスユーティリティークラス
 */
public class DevUtils {

	/**
	 *  ファイル一覧を取得する
	 *  @version 1.0
	 *  @param string 対象ディレクトリ
	 *  @param string 拡張子
	 *  @return List<string> ファイルリスト
	 */
	public static List<String> getDirectoryFiles(
									String targetPath, 
									String extensionFilter)
	{
		List<String> retVal = new ArrayList<String>();	// 返り値

		File dir = new File(targetPath);
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];

			// ファイル以外はスキップ
			if (file.isFile() == false) {
				continue;
			}

			// ファイル名を取得
			String filename = file.getName();

			// 拡張子を取得
			String extension = filename.substring(filename.lastIndexOf("."));

			// 拡張子が指定されている場合、拡張子をチェック
			if (0 < extensionFilter.length()) {
				// CSVファイル以外はスキップ
				if (extension.equalsIgnoreCase("." + extensionFilter) == false) {
					continue;
				}
			}

			// 返り値リストに登録
			retVal.add(file.getName());
		}

		// ファイル一覧を返す
		return retVal;
	}
}
