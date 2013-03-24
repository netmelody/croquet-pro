package org.netmelody.croquet.android;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CourtActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_court);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.court, menu);
		return true;
	}

}
