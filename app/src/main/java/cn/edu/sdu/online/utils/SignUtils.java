package cn.edu.sdu.online.utils;

import cn.edu.sdu.online.app.Main;
public class SignUtils {
	DateUtil data;

	public SignUtils() {
		data = new DateUtil();
	}

	boolean ifGetRank = false;
	public static String rank;

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

//	public void getsignRank(String id, String name, final DataListener listener) {
//		RequestParams params = new RequestParams();
//		params.addQueryStringParameter("Type", "getRank");
//		params.addBodyParameter("id", id);
//		params.addBodyParameter("name", name);
//		System.out.println("pai mimg zhi ximg");
//		HttpUtils http = new HttpUtils();
//		http.send(HttpRequest.HttpMethod.POST, DefineUtil.PATH, params,
//				new RequestCallBack<String>() {
//
//					@Override
//					public void onFailure(HttpException arg0, String arg1) {
//						// TODO Auto-generated method stub
//						ifGetRank = false;
//						System.out.println(arg0.getMessage() + "yi chang shi");
//					}
//
//					@Override
//					public void onSuccess(ResponseInfo<String> arg0) {
//						// TODO Auto-generated method stub
//						System.out.println("chnegggon");
//						rank = arg0.result;
//						ifGetRank = true;
//						listener.onFinished();
//					}
//				});
//	}

	public boolean continuSign() {

		// TODO Auto-generated method stub
		int Signday = Main.getApp().getDataStore().getInt("signday", 0);
		if (Signday == data.getDay()) {
			return true;
		} else {
			return false;
		}
	}

	public static interface DataListener {
		public void onFinished();
	}

}
