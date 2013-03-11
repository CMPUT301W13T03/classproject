package ca.c301.t03_recipes.test;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import ca.c301.t03_model.DataManager;
import ca.c301.t03_model.FullFileException;
import ca.c301.t03_model.Ingredient;
import ca.c301.t03_model.Recipe;
import ca.c301.t03_model.RecipeManager;
import ca.c301.t03_recipes.MainActivity;

public class HTTPTest extends ActivityInstrumentationTestCase2<MainActivity>{
	private final static String TEST_FILE_NAME = "http_test_file";

	public HTTPTest(){
		super(MainActivity.class);
	}
	@Before
	public void setUp() throws Exception{
		//Delete any existing file.
		getActivity().getFileStreamPath(TEST_FILE_NAME).delete();
	}
	@Test
	public void testPublishRecipe(){
		Recipe recipe = new Recipe();
		recipe.setId(18473);
		recipe.setName("Cup of Water");
		Ingredient water = new Ingredient("Water");
		recipe.addIngredient(water);
		recipe.setInstructions("Put it in a cup, you idiot.");
		RecipeManager manager = new RecipeManager(new DataManager(getActivity(),TEST_FILE_NAME));
		manager.publishRecipeToWeb(recipe);
		Recipe webRecipe = manager.getSingleRecipe(18473);
		assertEquals(webRecipe.getName(), recipe.getName());
		assertEquals(webRecipe.getIngredient(0).getName(), recipe.getIngredient(0).getName());
		assertEquals(webRecipe.getIngredient(0).getAmount(), recipe.getIngredient(0).getAmount());
	}
	public void testBadConnection(){
		//TODO Need more implementation info (mock object?)
		fail("Test not implemented yet");
	}
	@Test
	public void testSaveRecipeLocally(){
		Recipe recipe = new Recipe("Name","Instructions");
		recipe.setId(1900);
		RecipeManager manager = new RecipeManager(new DataManager(getActivity(),TEST_FILE_NAME));
		
		manager.publishRecipeToWeb(recipe);
		Recipe downloadedRecipe = manager.getSingleRecipe(1900);
		try {
			manager.saveRecipe(downloadedRecipe, getActivity());
		} catch (FullFileException e) {
			fail("Full file exception");
			e.printStackTrace();
		}
		//id would be 0 because its the first one saved.
		Recipe savedRecipe = manager.getLocallySavedRecipeById(0);
		assertNotNull(savedRecipe);
		assertEquals(savedRecipe.getName(),"Name");
		assertEquals(savedRecipe.getInstructions(),"Instructions");
		
	}
	@Test
	public void testSearchForRecipe(){
		Recipe recipe0 = new Recipe("Salad", "Put some vegetables in a bowl.");
		Recipe recipe1 = new Recipe("Cookie", "Make a cookie");
		RecipeManager manager = new RecipeManager(new DataManager(getActivity(),TEST_FILE_NAME));
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
