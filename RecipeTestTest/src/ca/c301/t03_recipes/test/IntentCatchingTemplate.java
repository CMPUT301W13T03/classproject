package ca.c301.t03_recipes.test;

import org.junit.Before;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import ca.c301.t03_recipes.MainActivity;
import ca.c301.t03_recipes.RecipeApplication;
/*
 * This is a template class used for catching intents thrown by test cases. It is
 * useful for seeing if a method is correctly signalling another application using
 * an intent.
 */
public class IntentCatchingTemplate extends ActivityInstrumentationTestCase2<MainActivity> {


	public Intent caughtIntent;
	public int caughtRequestCode;
	public Activity fakeActivity;
	protected class FakeActivity extends Activity{
		@Override
		public void startActivityForResult(Intent intent, int requestCode) {
			caughtIntent = intent;
			caughtRequestCode = requestCode;
//			super.startActivity(intent);
		}
		@Override
		public void startActivity(Intent i){
			caughtIntent = i;
		}
	}
	public IntentCatchingTemplate(Class<MainActivity> activityClass) {
		super(activityClass);
		fakeActivity = new FakeActivity();
	}
	
}
