package com.assets;

import java.sql.SQLException;
public class Main {

	public static void main(String arg[]) throws SQLException {
		ScaffoldGen sg = new ScaffoldGen("delivery","StemOption", "biz_stem_option","题项");
		sg.execute();
       }
}