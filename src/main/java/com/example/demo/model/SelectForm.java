package com.example.demo.model;

import java.io.Serializable;

/**
 *  ファイルダウンロード画面用データモデルクラス
 */
public class SelectForm implements Serializable 
{
	private String selectedItem;
	public String getSelectedItem() {
		return selectedItem;
	}
	public void setSelectedItem(String selectedItem) {
		this.selectedItem = selectedItem;
	}

	private String[] selectedItems;
	public String[] getSelectedItems() {
		return selectedItems;
	}
	public void setSelectedItems(String[] selectedItems) {
		this.selectedItems = selectedItems;
	}
}
