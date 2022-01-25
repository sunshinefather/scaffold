package com.assets;


public class Main {

	public static void main(String arg[]) throws Exception {
		ScaffoldGen sg = new ScaffoldGen("newinsurance","TransferRecord", "insu_transfer_record","转院备案");
		sg.execute();
       }
}