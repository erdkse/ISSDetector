package issdetector.com.erdkse.apps.issdetector;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import issdetector.com.erdkse.apps.issdetector.adapters.MyAdapter;
import issdetector.com.erdkse.apps.issdetector.objects.ResponseObject;
import issdetector.com.erdkse.apps.issdetector.objects.ReturnObject;
import com.google.gson.Gson;

public class MainActivity extends ActionBarActivity {

	private Button btn_show_location;
	private Button btn_show_iss;
	private GPSTracker gps;
	private AQuery aq;
	public static double latitude;
	public static double longitude;
	public static double altitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		gps = new GPSTracker(MainActivity.this);
		aq = new AQuery(MainActivity.this);
		btn_show_location = (Button) findViewById(R.id.btn_show_location);
		btn_show_iss = (Button) findViewById(R.id.btn_show_iss);

		btn_show_location.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// create class object

				// check if GPS enabled
				if (gps.canGetLocation()) {

					latitude = gps.getLatitude();
					longitude = gps.getLongitude();
					altitude = gps.getAltitude();

					// \n is for new line
					Toast.makeText(
							getApplicationContext(),
							"Lokasyonun - \nEnlem: " + latitude + "\nBoylam: "
									+ longitude + "\nYükseklik: " + altitude,
							Toast.LENGTH_LONG).show();
				} else {
					// can't get location
					// GPS or Network is not enabled
					// Ask user to enable GPS/network in settings
					gps.showSettingsAlert();
				}

			}
		});

		btn_show_iss.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (latitude == 0 && longitude == 0) {

					Toast.makeText(getApplicationContext(),
							"Lütfen ünce konumunuzu belirleyin.",
							Toast.LENGTH_SHORT).show();

				} else {
					Intent intent = new Intent(MainActivity.this,
							PassesListActivity.class);
					startActivity(intent);
				}

			}
		});

	}

}
