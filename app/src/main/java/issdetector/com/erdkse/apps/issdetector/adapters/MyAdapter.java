package issdetector.com.erdkse.apps.issdetector.adapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import issdetector.com.erdkse.apps.issdetector.MapActivity;
import issdetector.com.erdkse.apps.issdetector.R;
import issdetector.com.erdkse.apps.issdetector.objects.ResponseObject;

public class MyAdapter extends BaseAdapter {

	private ArrayList<ResponseObject> data;
	private AQuery aq;
	private Activity mActivity;
	private ViewHolder holder;
	private LayoutInflater inflater = null;

	public MyAdapter(Activity mActivity, ArrayList<ResponseObject> data) {
		// TODO Auto-generated constructor stub
		this.data = data;
		this.mActivity = mActivity;
		this.aq = new AQuery(mActivity);
		this.inflater = (LayoutInflater) mActivity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public ResponseObject getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		holder = null;

		if (convertView == null) {

			// inflate the layout
			holder = new ViewHolder();

			convertView = inflater.inflate(R.layout.iss_passes_row, null);

			holder.tv_iss_duration = (TextView) convertView
					.findViewById(R.id.tv_iss_duration);
			holder.tv_iss_risetime = (TextView) convertView
					.findViewById(R.id.tv_iss_risetime);
			holder.lv_row = (RelativeLayout) convertView
					.findViewById(R.id.lv_row);

			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final int item = position;
		ImageOptions img_options = new ImageOptions();

		img_options.round = 360;

		aq = new AQuery(convertView);

		int min = data.get(position).getDuration() / 60;
		int sec = data.get(position).getDuration() % 60;

		aq.id(holder.tv_iss_duration).text(min + " dakika " + sec + " saniye");

		Date date = new Date((long) data.get(position).getRisetime() * 1000);

		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss    MM/dd");

		aq.id(holder.tv_iss_risetime).text(format.format(date));

		aq.id(holder.lv_row).clicked(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Toast.makeText(mActivity, position + "'e bast�n",
//						Toast.LENGTH_SHORT).show();

				Intent intent = new Intent(mActivity, MapActivity.class);
				mActivity.startActivity(intent);
			}
		});

		return convertView;
	}

	class ViewHolder {
		private TextView tv_iss_duration, tv_iss_risetime;
		private RelativeLayout lv_row;
	}

}
