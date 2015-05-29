package cn.edu.sdu.online.network;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import cn.edu.sdu.online.app.Main;
import cn.edu.sdu.online.modal.Course;
import cn.edu.sdu.online.modal.Lesson;
import cn.edu.sdu.online.modal.MyHomework;
import cn.edu.sdu.online.modal.MyInfo;
import cn.edu.sdu.online.modal.Person;

public class NetWork {

	private final static String LIB_URL = "http://202.194.14.195:8080/curriculumlib/lib";
	public final static String LOGIN_URL = "http://202.194.14.195:8080/CurriculumServer/login";
	private HttpPost httpRequest;
	private int loginTimes = 0;
	private String loginMessage = null;
	private Context context;
    private final static String TAG = "FromNetWork";

	public NetWork(Context context) {
		this.context = context;
	}

	public JSONArray GetResultFromNet(List<NameValuePair> params)
			throws ClientProtocolException, IOException {
		httpRequest = new HttpPost(LIB_URL);
		httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		HttpResponse httpResponse = new DefaultHttpClient()
				.execute(httpRequest);
		String jsonData = "";
		Log.i("GetResultFromNet", ""
				+ httpResponse.getStatusLine().getStatusCode());
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			InputStream is = httpResponse.getEntity().getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = "";
			while ((line = br.readLine()) != null) {
				jsonData += line + "\r\n";
			}
		} else {
			Log.e("Reason",httpResponse.getStatusLine().getStatusCode()+"");
			Log.e("Network", "访问失败~");
		}
		jsonData = jsonData.trim();
		Log.i("GetResultFromNet", jsonData);
		return AnalysisJsonArray(jsonData);
	}

	public JSONArray NetWorkParams(JSONObject object) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Iterator<?> keys = object.keys();
		while (keys.hasNext()) {
			String key = keys.next().toString();
			try {
				params.add(new BasicNameValuePair(key, object.getString(key)));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			return GetResultFromNet(params);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public int LoginNetWork(JSONObject object)
			throws ClientProtocolException, IOException {
		int LOGIN_SUCCESS = 0;
		int WRONG_UN_PWD = 1;
		int ERROR = 2;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Iterator<?> keys = object.keys();

		while (keys.hasNext()) {
			String key = keys.next().toString();
			try {
				params.add(new BasicNameValuePair(key, object.getString(key)));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		httpRequest = new HttpPost(LOGIN_URL);

		httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		HttpResponse httpResponse = new DefaultHttpClient()
				.execute(httpRequest);
		/*httpResponse.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
		httpResponse.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				15000);*/

		String jsonData = "";
		int result  = httpResponse.getStatusLine().getStatusCode();
		if (result == 200) {
			InputStream is = httpResponse.getEntity().getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = "";
			while ((line = br.readLine()) != null) {
				jsonData += line + "\r\n";
			}
			jsonData = jsonData.trim();
			Log.i("登录测试","登录数据："+jsonData);
			char codechar = jsonData.charAt(0);
			String code = String.valueOf(codechar);
			if (code.equals("0")) {
				AnalysisLoginMessage(jsonData);
				return LOGIN_SUCCESS;
			} else {
				return WRONG_UN_PWD;
			}
		} else {
			Log.i("Network", "访问失败~State:"+result);
			return ERROR;
		}

	}

	private void AnalysisLoginMessage(String content) {
		TreeMap<Integer, List<Course>> semList = new TreeMap<Integer, List<Course>>();
		String[] contents = content.split("学生在线课程格子");
		Gson gson = new Gson();
		for (int i = 1; i < contents.length; i++) {
			switch (i) {
			case 1:
				Person person = new Person();// 读取用户个人信息
				person = gson.fromJson(contents[1], Person.class);
				Main.getApp().setPerson(person);

				break;
			case 2:
				List<Lesson> lessonList = new ArrayList<Lesson>(); // 读取课程表对象
				lessonList = gson.fromJson(contents[2],
						new TypeToken<List<Lesson>>() {
						}.getType());
                Log.i(TAG, "课表信息："+lessonList);
				Main.getApp().setCurriculumArray(lessonList);

				break;
			case 3:
				List<Course> semesterlist1 = new ArrayList<Course>(); // 读取第一学期成绩的列表对象
				semesterlist1 = gson.fromJson(contents[3],
						new TypeToken<List<Course>>() {
						}.getType());
				// System.out.println(semesterlist1);
				semList.put(1, semesterlist1);

				break;
			case 4:
				List<Course> semesterlist2 = new ArrayList<Course>(); // 读取第二学期课成绩的列表
				semesterlist2 = gson.fromJson(contents[4],
						new TypeToken<List<Course>>() {
						}.getType());
				semList.put(2, semesterlist2);

				break;
			case 5:
				List<Course> semesterlist3 = new ArrayList<Course>(); // 读取第三学期课成绩的列表
				semesterlist3 = gson.fromJson(contents[5],
						new TypeToken<List<Course>>() {
						}.getType());
				semList.put(3, semesterlist3);
				break;
			case 6:
				List<Course> semesterlist4 = new ArrayList<Course>(); // 读取第四学期课成绩的列表
				semesterlist4 = gson.fromJson(contents[6],
						new TypeToken<List<Course>>() {
						}.getType());
				semList.put(4, semesterlist4);
				break;
			case 7:
				List<Course> semesterlist5 = new ArrayList<Course>(); // 读取第五学期课成绩的列表
				semesterlist5 = gson.fromJson(contents[7],
						new TypeToken<List<Course>>() {
						}.getType());
				semList.put(5, semesterlist5);
				System.out.println(semesterlist5);
				break;
			case 8:
				List<Course> semesterlist6 = new ArrayList<Course>(); // 读取第六学期课成绩的列表
				semesterlist6 = gson.fromJson(contents[8],
						new TypeToken<List<Course>>() {
						}.getType());
				semList.put(6, semesterlist6);
				System.out.println(semesterlist6);
				break;
			case 9:
				List<Course> semesterlist7 = new ArrayList<Course>(); // 读取第七学期课成绩的列表
				semesterlist7 = gson.fromJson(contents[9],
						new TypeToken<List<Course>>() {
						}.getType());
				semList.put(7, semesterlist7);
				System.out.println(semesterlist7);
				break;
			case 10:
				List<Course> semesterlist8 = new ArrayList<Course>(); // 读取第八学期课成绩的列表
				semesterlist8 = gson.fromJson(contents[10],
						new TypeToken<List<Course>>() {
						}.getType());
				semList.put(8, semesterlist8);
				System.out.println(semesterlist8);
				break;
			case 11:
				MyInfo info = new MyInfo();
				info = gson.fromJson(contents[11], MyInfo.class);
				if (info != null) {
					if (info.getName() != null) {
						Main.getApp().getDataStore().edit()
								.putString("Info_name", info.getName())
								.commit();
					}
					if (info.getAim() != null) {
						Main.getApp().getDataStore().edit()
								.putString("Info_aim", info.getAim()).commit();
					}
					if (info.getSex() != null) {
						Main.getApp().getDataStore().edit()
								.putString("Info_sex", info.getSex()).commit();
					}
					if (info.getTalktome() != null) {
						Main.getApp().getDataStore().edit()
								.putString("Info_talktome", info.getTalktome())
								.commit();
					}
					if (info.getImagepath() != null) {
						Main.getApp()
								.getDataStore()
								.edit()
								.putString("faceimagepath", info.getImagepath())
								.commit();
					}
					if (info.getPassword() != null) {
						Main.getApp().getDataStore().edit()
								.putString("lock", info.getPassword()).commit();
						Main.getApp().getDataStore().edit()
								.putBoolean("iflock", true).commit();
					}
					if (info.getIfsign() != null) {
						Main.getApp().getDataStore().edit()
								.putString("signif", info.getIfsign()).commit();
					}
					Main.getApp().getDataStore().edit()
							.putInt("signrank", info.getSignrank()).commit();
					Main.getApp().getDataStore().edit()
							.putInt("signcontinue", info.getSigncontinue())
							.commit();
					Main.getApp().getDataStore().edit()
							.putInt("signall", info.getSignall()).commit();
					Main.getApp().getDataStore().edit()
							.putInt("signday", info.getDay()).commit();
				}
				break;
			case 12:
				ArrayList<MyHomework> home = new ArrayList<MyHomework>();
				home = gson.fromJson(contents[12],
						new TypeToken<List<MyHomework>>() {
						}.getType());
				Main.getApp().setHomework("HOMEWORK", home);
				break;
			default:
				break;
			}
		}
		Main.getApp().setCompulsoryList(semList);
	}

	private JSONArray AnalysisJsonArray(String content) {
		JSONArray message = null;
		try {
			if (content.equals("用户名或密码错误[]")) {
				JSONObject objcet = new JSONObject();
				objcet.put("state", "用户名或密码错误");
				message = new JSONArray();
				message.put(objcet);
				return message;
			}
			message = new JSONArray(content);
		} catch (JSONException e) {

			e.printStackTrace();
			Log.i("NetWOrk_analysis", content);
		}
		return message;
	}
}
