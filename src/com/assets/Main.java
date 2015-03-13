package com.assets;

import java.sql.SQLException;

public class Main {

	public static void main(String arg[]) throws SQLException {
		ScaffoldGen sg = new ScaffoldGen("notice","NoticeDetial", "notice_detial","通知详细");
		sg.execute();
       }
}