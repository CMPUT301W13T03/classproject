package ca.c301.t03_recipes.test;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import ca.c301.t03_model.DataManager;
import ca.c301.t03_model.FullFileException;
import ca.c301.t03_model.Ingredient;
import ca.c301.t03_model.Recipe;
import ca.c301.t03_model.RecipeManager;
import ca.c301.t03_recipes.MainActivity;
/*
 * Tests dealing with web specific operations.
 */
public class HTTPTest extends ActivityInstrumentationTestCase2<MainActivity>{
	private final static String TEST_FILE_NAME = "http_test_file";

	public HTTPTest(){
		super(MainActivity.class);
	}

	//Delete testfile before each test.
	@Before
	public void setUp() throws Exception{
		//Delete any existing file.
		getActivity().getFileStreamPath(TEST_FILE_NAME).delete();
	}
	//Test to make sure publishing and retrieving recipes from the web works correctly. Must be done all in
	//one test.
	@Test
	public void testPublishRecipe() throws IllegalStateException, IOException{
		Recipe recipe = new Recipe();
		recipe.setName("Cup of Water");
		Ingredient water = new Ingredient("Water");
		recipe.addIngredient(water);
		recipe.setInstructions("Put it in a cup, you idiot.");
		RecipeManager manager = new RecipeManager(new DataManager(getActivity(),TEST_FILE_NAME));
		manager.publishRecipeToWeb(recipe);
		Recipe webRecipe = manager.getSingleRecipe(recipe.getId());
		assertEquals(webRecipe.getName(), recipe.getName());
		assertEquals(webRecipe.getIngredient(0).getName(), recipe.getIngredient(0).getName());
		assertEquals(webRecipe.getIngredient(0).getAmount(), recipe.getIngredient(0).getAmount());
	}
	// Test to make sure error where the application is unable to connect to the internet is handled properly.
	public void testBadConnection(){
		//TODO Need more implementation info (mock object?)
		Recipe recipe = new Recipe ("Burger", "Cook a Burger");
		recipe.setId(93732);
		RecipeManager manager = new RecipeManager(getActivity());
		manager.setURL("http://asdoiahspdsdfewdfssdfvcvergedfsljkl.softwareprocess.es:8080/testing/recipezzz/");
		try {
			manager.publishRecipeToWeb(recipe);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			return;
		}
	}
	//Test to make sure a recipe can be downloaded from the web and saved locally.
	@Test
	public void testSaveRecipeLocally() throws IllegalStateException, IOException{
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
		//id would be 0  because its the first one saved.
		Recipe savedRecipe = manager.getLocallySavedRecipeById(0);
		assertNotNull(savedRecipe);
		assertEquals(savedRecipe.getName(),"Name");
		assertEquals(savedRecipe.getInstructions(),"Instructions");

	}
	//Test to see if a web recipe can be retrieved by its name.
	@Test
	public void testSearchForRecipe() throws IllegalStateException, IOException{
		Recipe recipe0 = new Recipe("Salad", "Put some vegetables in a bowl.");
		Recipe recipe1 = new Recipe("Cookie", "Make a cookie");
		Recipe recipe2 = new Recipe("Cookies", "Who makes one cookie? Idiot.");
		recipe0.setId(0);
		recipe1.setId(1);
		recipe2.setId(2);
		RecipeManager manager = new RecipeManager(new DataManager(getActivity(),TEST_FILE_NAME));
		//THIS IS NOT YET IMPLEMENTED
		//manager.resetServerID();
		manager.publishRecipeToWeb(recipe1);
		manager.publishRecipeToWeb(recipe0);
		manager.publishRecipeToWeb(recipe2);
		ArrayList<Recipe> results = manager.searchWebForRecipeByName("Cookie");
		assertNotNull(results);
		assertTrue(findRecipeInList(results, recipe1));

	}
	//Helper method for searching for recipes.
	public boolean findRecipeInList(ArrayList<Recipe> recipeList, Recipe recipe)
	{
		for(int i = 0; i < recipeList.size(); i++)
		{
			Recipe curRecipe = recipeList.get(i);
			Log.i("HTTPSearch", curRecipe.getName() + "|" + curRecipe.getInstructions());
			if(recipe.getName().equals(curRecipe.getName()) && recipe.getInstructions().equals(curRecipe.getInstructions())) return true;
		}
		return false;
	}
}
