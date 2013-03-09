package ca.c301.t03_recipes.test;

import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import ca.c301.t03_model.Ingredient;
import ca.c301.t03_model.RecipeManager;
import ca.c301.t03_recipes.MainActivity;


public class IngredientsTest extends ActivityInstrumentationTestCase2<MainActivity>{

	IngredientsTest()
	{
		super(MainActivity.class);
	}
	@Test
	void testGetIngredient()
	{
		RecipeManager manager = new RecipeManager(getActivity());
		Ingredient ingredient = new Ingredient("Water");
		ingredient.setId(0);
		manager.addIngredientToPantry(ingredient);
		Ingredient retIngredient = manager.getLocalIngredientById(0);
		assertNotNull(retIngredient);
		assertSame(retIngredient,ingredient);
		
	}
}
