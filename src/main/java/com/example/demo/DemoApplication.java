package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *  アプリケーションクラス
 */
@SpringBootApplication
public class DemoApplication {

	/**
	 *  メイン処理
	 *  @version 1.0
	 *  @param String[] 起動パラメータ
	 *  @return なし
	 */
	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);
	}

}


