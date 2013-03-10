package ca.c301.t03_recipes.test;

import java.util.ArrayList;

import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import ca.c301.t03_model.Ingredient;
import ca.c301.t03_model.Recipe;
import ca.c301.t03_model.RecipeManager;
import ca.c301.t03_recipes.MainActivity;

public class HTTPTest extends ActivityInstrumentationTestCase2<MainActivity>{

	public HTTPTest(){
		super(MainActivity.class);
	}
	@Test
	public void testPublishRecipe(){
		Recipe recipe = new Recipe();
		recipe.setId(18473);
		recipe.setName("Cup of Water");
		Ingredient water = new Ingredient("Water");
		recipe.addIngredient(water);
		recipe.setInstructions("Put it in a cup, you idiot.");
		RecipeManager manager = new RecipeManager(getActivity());
		manager.publishRecipeToWeb(recipe);
		Recipe webRecipe = manager.getSingleRecipe(18473);
		assertSame(webRecipe, recipe);
	}
	public void testBadConnection(){
		//TODO Need more implementation info (mock object?)
		fail("Test not implemented");
	}
	@Test
	public void testSaveRecipeLocally(){
		//TODO Need more implementation info (mock object?)
		fail("Test not implemented");
		
	}
	@Test
	public void testSearchForRecipe(){
		Recipe recipe0 = new Recipe("Salad", "Put some vegetables in a bowl.");
		Recipe recipe1 = new Recipe("Cookie", "Make a cookie");
		RecipeManager manager = new RecipeManager(getActivity());
		manager.publishRecipeToWeb(recipe0);
		manager.publishRecipeToWeb(recipe1);
		ArrayList<Recipe> results = manager.searchWebForRecipeByName("Cookie");
		assertNotNull(results);
		assertTrue(findRecipeInList(results, recipe1));

	}
	public boolean findRecipeInList(ArrayList<Recipe> recipeList, Recipe recipe)
	{
		for(int i = 0; i < recipeList.size(); i++)
		{
			Recipe curRecipe = recipeList.get(i);
			if(recipe.getName() == curRecipe.getName() && recipe.getInstructions() == curRecipe.getInstructions()) return true;
		}
		return false;
	}
}
