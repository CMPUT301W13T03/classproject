package ca.c301.t03_recipes;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import ca.c301.t03_model.RecipeManager;

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
