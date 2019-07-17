package com.assets;


public class Main {

	public static void main(String arg[]) throws Exception {
		ScaffoldGen sg = new ScaffoldGen("trade","WechatWxBillCompareStatement", "biz_wechat_wx_bill_compare_statement","对账清单");
		sg.execute();
       }
}