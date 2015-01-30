package issdetector.com.erdkse.apps.issdetector;

import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import issdetector.com.erdkse.apps.issdetector.objects.ISSObject;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MapActivity extends Activity {

	// Google Map
	private GoogleMap googleMap;
	private double latitude = 17.385044;
	private double longitude = 78.486671;
	private AQuery aq;
	private String url = "http://api.open-notify.org/iss-now.json";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_activity);
		aq = new AQuery(getParent());
		async_getISSPosition();

	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	public void async_getISSPosition() {
		// TODO Auto-generated method stub

		AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject json, AjaxStatus status) {

				try {
					Log.i("response", json.toString());

					Gson gson = new Gson();
					ISSObject issObject = gson.fromJson(json.toString(),
							ISSObject.class);
					latitude = issObject.getIssPositionObject().getLatitude();
					longitude = issObject.getIssPositionObject().getLongitude();

					try {
						// Loading map
						initilizeMap();

					} catch (Exception e) {
						e.printStackTrace();
					}
					googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
					// latitude and longitude

					// create marker
					MarkerOptions marker = new MarkerOptions().position(
							new LatLng(latitude, longitude)).title("ISS");

					// Changing marker icon
					marker.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

					// adding marker
					googleMap.addMarker(marker);

					CameraPosition cameraPosition = new CameraPosition.Builder()
							.target(new LatLng(latitude, longitude)).build();

					googleMap.animateCamera(CameraUpdateFactory
							.newCameraPosition(cameraPosition));
					googleMap.setMyLocationEnabled(true);
					googleMap.getUiSettings().setRotateGesturesEnabled(false);

					Polyline line = googleMap.addPolyline(new PolylineOptions()
							.add(new LatLng(latitude, longitude),
									new LatLng(MainActivity.latitude,
											MainActivity.longitude)).width(25)
							.color(Color.BLUE).geodesic(true));

				} catch (Exception e) {

				}
			}
		};

		cb.url(url).type(JSONObject.class);
		aq.ajax(cb);

	}

}
