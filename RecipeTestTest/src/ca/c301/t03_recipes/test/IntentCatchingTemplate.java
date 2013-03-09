package ca.c301.t03_recipes.test;

import org.junit.Before;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import ca.c301.t03_recipes.MainActivity;

public class IntentCatchingTemplate extends ActivityUnitTestCase<MainActivity> {

	protected Intent caughtIntent;

	protected class IntentCatchingContext extends ContextWrapper {
		public IntentCatchingContext(Context base) {
			super(base);
		}

		@Override
		public void startActivity(Intent intent) {
			caughtIntent = intent;
			super.startActivity(intent);
		}

	}

	public IntentCatchingTemplate(Class<MainActivity> activityClass) {
		super(activityClass);
	}
	@Before
    protected void setUp() throws Exception {
        super.setUp();
        IntentCatchingContext contextWrapper = new IntentCatchingContext(getInstrumentation().getTargetContext());
        setActivityContext(contextWrapper);
        startActivity(new Intent(), null, null);
    }
}
