package ca.c301.t03_recipes.test;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;
import junit.framework.TestSuite;

import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;

import ca.c301.t03_model.Recipe;
import ca.c301.t03_model.RecipeManager;
import ca.c301.t03_recipes.MainActivity;

public class RecipeStorageTest extends ActivityInstrumentationTestCase2<MainActivity>{

	public RecipeStorageTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}

	@Test
	public void testAddRecipe() {
		Recipe recipe = new Recipe(0,"Salad", "Put in some veggies brother.");
		RecipeManager manager = new RecipeManager();
		manager.saveRecipe(recipe);
		Recipe retrievedRecipe = manager.getLocallySavedRecipeById(0);
		assertSame(retrievedRecipe,recipe);
	}

}
