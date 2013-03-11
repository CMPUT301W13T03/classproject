package ca.c301.t03_recipes;

import ca.c301.t03_recipes.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Activity used to take and save new photos
 */
public class PhotoActivity extends Activity {
	
	/**
	 * Is responsible for creating the view of the activity,
	 * Buttons are set here
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
		
		Button saveButton = (Button) findViewById(R.id.button_save);
        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	finish();
            }
        });
        
        Button discardButton = (Button) findViewById(R.id.button_discard);
        discardButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	
            }
        });
        
        Button takePhotoButton = (Button) findViewById(R.id.button_take_photo);
        takePhotoButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_photo, menu);
		return true;
	}

}
