package org.netmelody.croquet.android;

import android.app.Activity;
import android.os.Bundle;

public final class CourtActivity extends Activity {

	private CourtView courtView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		courtView = new CourtView(this);
		setContentView(courtView);
	}

	@Override
	protected void onPause() {
		super.onPause();
		courtView.onPause();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		courtView.onResume();
	}
}
