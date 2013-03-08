package ca.c301.t03_recipes.test;

import java.util.ArrayList;

import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import ca.c301.t03_model.Recipe;
import ca.c301.t03_model.RecipeManager;
import ca.c301.t03_recipes.MainActivity;

public class HTTPTest extends ActivityInstrumentationTestCase2<MainActivity>{

	public HTTPTest(){
		super(MainActivity.class);
	}
	@Test
	void testPublishRecipe(){
		Recipe recipe = new Recipe(0, "Salad", "Put some vegetables in a bowl.");
		RecipeManager manager = new RecipeManager(getActivity());
		manager.publishRecipeToWeb(recipe);
		ArrayList<Recipe> webRecipeList = (ArrayList<Recipe>) manager.getWebRecipes();
		assertTrue(findRecipeInList(webRecipeList, recipe));
	}
	void testBadConnection(){
		//TODO Need more implementation info (mock object?)
		fail("Test not implemented");
	}
	@Test
	void testSaveRecipeLocally(){
		//TODO Need more implementation info (mock object?)
		fail("Test not implemented");
		
	}
	@Test
	void testSearchForRecipe(){
		Recipe recipe0 = new Recipe(0, "Salad", "Put some vegetables in a bowl.");
		Recipe recipe1 = new Recipe(1, "Cookie", "Make a cookie");
		RecipeManager manager = new RecipeManager(getActivity());
		manager.publishRecipeToWeb(recipe0);
		manager.publishRecipeToWeb(recipe1);
		
		ArrayList<Recipe> results = manager.searchWebForRecipeByName("Cookie");
		assertTrue(findRecipeInList(results, recipe1));

	}
	boolean findRecipeInList(ArrayList<Recipe> recipeList, Recipe recipe)
	{
		for(int i = 0; i < recipeList.size(); i++)
		{
			Recipe curRecipe = recipeList.get(i);
			if(recipe.getName() == curRecipe.getName() && recipe.getInstructions() == curRecipe.getInstructions()) return true;
		}
		return false;
	}
}
