package issdetector.com.erdkse.apps.issdetector;

import java.util.ArrayList;

import org.json.JSONObject;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import issdetector.com.erdkse.apps.issdetector.adapters.MyAdapter;
import issdetector.com.erdkse.apps.issdetector.objects.ResponseObject;
import issdetector.com.erdkse.apps.issdetector.objects.ReturnObject;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class PassesListActivity extends Activity {

	private MyAdapter myAdapter;
	private ProgressDialog barProgressDialog;

	private AQuery aq;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.passes_list_activity);

		url = "http://api.open-notify.org/iss-pass.json?lat="
				+ MainActivity.latitude + "&lon=" + MainActivity.longitude
				+ "&alt=" + "500"+ "&n=20";
		
		Toast.makeText(
				getApplicationContext(),
				url, Toast.LENGTH_LONG).show();

		aq = new AQuery(PassesListActivity.this);

		async_getPasses();
	}

	public void async_getPasses() {
		// TODO Auto-generated method stub

		barProgressDialog = new ProgressDialog(PassesListActivity.this);
		barProgressDialog.setTitle("Liste y�kleniyor...");

		AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject json, AjaxStatus status) {

				try {
					Log.i("response", json.toString());

					Gson gson = new Gson();
					ReturnObject returnObject = gson.fromJson(json.toString(),
							ReturnObject.class);

					ArrayList<ResponseObject> mData = new ArrayList<ResponseObject>();

					for (int i = 0; i < returnObject.getResponse().length; i++) {
						mData.add(returnObject.getResponse()[i]);
					}

					myAdapter = new MyAdapter(PassesListActivity.this, mData);

					aq.id(R.id.passes_listview).adapter(myAdapter);

				} catch (Exception e) {

				}
			}
		};

		// BasicHandle handle = new BasicHandle(AUTH_USERNAME, AUTH_PASSWORD);
		// cb.header("X-API-KEY", XAPIKEY);

		cb.url(url).type(JSONObject.class);
		aq.ajax(cb).progress(barProgressDialog);

	}

}
