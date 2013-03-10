package ca.c301.t03_recipes.test;

import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import ca.c301.t03_model.DataManager;
import ca.c301.t03_model.Ingredient;
import ca.c301.t03_model.RecipeManager;
import ca.c301.t03_model.VirtualPantry;
import ca.c301.t03_recipes.MainActivity;


public class IngredientsTest extends ActivityInstrumentationTestCase2<MainActivity>{

	IngredientsTest()
	{
		super(MainActivity.class);
	}
	@Test
	void testSaveIngredient()
	{
		DataManager dataManager = new DataManager(getActivity());
		RecipeManager manager = new RecipeManager(dataManager);
		Ingredient ingredient = new Ingredient("Name");
		
		VirtualPantry pantry = dataManager.getVirtualPantry();
		assertNotNull(pantry);
		manager.addIngredientToPantry(ingredient);
		Ingredient retIngredient = dataManager.getVirtualPantry().findIngredient(0);
		assertNotNull(retIngredient);
		assertSame(retIngredient,ingredient);
		
	}
	@Test
	void testGetIngredient()
	{
		DataManager dataManager = new DataManager(getActivity());
		RecipeManager manager = new RecipeManager(dataManager);
		Ingredient ingredient = new Ingredient("Name");
		
		VirtualPantry pantry = dataManager.getVirtualPantry();
		assertNotNull(pantry);
		dataManager.getVirtualPantry().addIngredient(ingredient);
		Ingredient retIngredient = manager.getLocalIngredientById(0);
		assertNotNull(retIngredient);
		assertSame(retIngredient,ingredient);
		
	}
}
