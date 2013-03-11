package ca.c301.t03_recipes;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.os.StrictMode;
import ca.c301.t03_model.RecipeManager;

/**
 * Application used by activities to access code in the model
 */
public class RecipeApplication extends Application{

	/**
	 * @uml.property  name="recipeManager"
	 * @uml.associationEnd  aggregation="composite" inverse="recipeApplication:ca.c301.t03_model.RecipeManager"
	 */
	private RecipeManager recipeManager;

	// I need this for testing. -Zach
	public RecipeApplication(){
		super();
	}
	public RecipeApplication(Instrumentation instrumentation) {
		attachBaseContext(instrumentation.getTargetContext());
	}
	//TODO None of this eventually.
	@TargetApi(9)
	@Override
	public void onCreate()
	{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
	}
	/**
	 * Getter of the property <tt>recipeManager</tt>
	 * @return  Returns the recipeManager.
	 * @uml.property  name="recipeManager"
	 */
	public RecipeManager getRecipeManager() {
		return recipeManager;
	}

	/**
	 * Setter of the property <tt>recipeManager</tt>
	 * @param recipeManager  The recipeManager to set.
	 * @uml.property  name="recipeManager"
	 */
	public void setRecipeManager() {
		this.recipeManager = new RecipeManager(getApplicationContext());
	}

	
}
