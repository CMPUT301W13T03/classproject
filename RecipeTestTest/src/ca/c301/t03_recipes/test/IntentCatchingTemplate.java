package ca.c301.t03_recipes.test;

import org.junit.Before;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import ca.c301.t03_recipes.MainActivity;
import ca.c301.t03_recipes.RecipeApplication;
/*
 * This is a template class used for catching intents thrown by test cases. It is
 * useful for seeing if a method is correctly signalling another application using
 * an intent.
 */
public class IntentCatchingTemplate extends ActivityUnitTestCase<MainActivity> {

	protected Intent caughtIntent;
	protected IntentCatchingContext intentCatcher;

	protected class IntentCatchingContext extends ContextWrapper {
		public IntentCatchingContext(Context base) {
			super(base);
		}

		@Override
		public void startActivity(Intent intent) {
			caughtIntent = intent;
//			super.startActivity(intent);
		}

	}

	public IntentCatchingTemplate(Class<MainActivity> activityClass) {
		super(activityClass);
	}
	@Before
    protected void setUp() throws Exception {
        super.setUp();
        Application app = new RecipeApplication(getInstrumentation());
        setApplication(app);
        
        intentCatcher = new IntentCatchingContext(getInstrumentation().getTargetContext());
        setActivityContext(intentCatcher);
        startActivity(new Intent(), null, null);
    }
}
