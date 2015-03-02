package com.example.acc;

import java.util.List;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		private TextView mtextview1;
		private TextView mtextview2;
		private TextView mtextview3;
		private SeekBar mseekbar1;
		private ProgressBar mseekbar2;
		private ProgressBar mseekbar3;
		private TextView mcount;
		
		private SensorManager mSensorManager;
		int count = 0;
		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			mtextview1 = (TextView)rootView.findViewById(R.id.textview1);
			mtextview2 = (TextView)rootView.findViewById(R.id.textview2);
			mtextview3 = (TextView)rootView.findViewById(R.id.textview3);
			mseekbar1 = (SeekBar)rootView.findViewById(R.id.seekbar1);
			mseekbar1.setMax(20);
			mseekbar2 = (ProgressBar)rootView.findViewById(R.id.seekbar2);
			mseekbar3 = (ProgressBar)rootView.findViewById(R.id.seekbar3);
			mcount = (TextView)rootView.findViewById(R.id.countview);
			
			mSensorManager = (SensorManager)getActivity().getSystemService(SENSOR_SERVICE);

			return rootView;
		}

		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			// ‰æ–Ê‚ð•\Ž¦
			super.onStart();
			
			List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
			if(sensors.size() > 0){
				Sensor s = sensors.get(0);
				mSensorManager.registerListener(mSensorListener, s, SensorManager.SENSOR_DELAY_UI);
			}
			
		}
		
		SensorEventListener mSensorListener = new SensorEventListener() {
			
			@Override
			public void onSensorChanged(SensorEvent event) {
				// TODO Auto-generated method stub
				// ’l‚ª•Ï‰»‚Ì‚Æ‚«
				
				
				String mtext1 = String.format("%.2f", event.values[SensorManager.DATA_X]);
				String mtext2 = String.format("%.2f", event.values[SensorManager.DATA_Y]);
				String mtext3 = String.format("%.2f", event.values[SensorManager.DATA_Z]);
				mtextview1.setText(mtext1);
				mtextview2.setText(mtext2);
				mtextview3.setText(mtext3);
				
				mseekbar1.setProgress((int)event.values[SensorManager.DATA_X] + 10);
				mseekbar2.setProgress((int)event.values[SensorManager.DATA_Y] + 10);
				mseekbar3.setProgress((int)event.values[SensorManager.DATA_Z] + 10);
				
				float shake = event.values[SensorManager.DATA_X] + event.values[SensorManager.DATA_Y] + event.values[SensorManager.DATA_Z];
				if(shake > 25.0){
					count++;
				}
				mcount.setText("" + count);
			}
			
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub
				
			}
		};

		@Override
		public void onStop() {
			// TODO Auto-generated method stub
			// ‰æ–Ê‚ð”ñ•\Ž¦‚µ‚½
			super.onStop();
			
			mSensorManager.unregisterListener(mSensorListener);
		}
		
		
	}

}
