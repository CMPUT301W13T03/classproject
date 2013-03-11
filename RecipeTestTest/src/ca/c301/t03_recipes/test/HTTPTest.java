package ca.c301.t03_recipes.test;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

<<<<<<< HEAD
import org.apache.http.client.ClientProtocolException;
=======
>>>>>>> branch 'master' of ssh://git@github.com/CMPUT301W13T03/classproject.git
import org.junit.Before;
import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
<<<<<<< HEAD
import android.util.Log;
=======
>>>>>>> branch 'master' of ssh://git@github.com/CMPUT301W13T03/classproject.git
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
	@Before
	public void setUp() throws Exception{
		//Delete any existing file.
		getActivity().getFileStreamPath(TEST_FILE_NAME).delete();
	}
	@Test
	public void testPublishRecipe() throws IllegalStateException, IOException{
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
<<<<<<< HEAD
		Recipe recipe = new Recipe ("Burger", "Cook a Burger");
		recipe.setId(93732);
		RecipeManager manager = new RecipeManager(getActivity());
		try {
			manager.publishToFail(recipe);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			return;
		}
		catch (IOException e){
			
		}
		fail("Bad Connection test failed");
=======
		fail("Test not implemented yet");
>>>>>>> branch 'master' of ssh://git@github.com/CMPUT301W13T03/classproject.git
	}
	@Test
	public void testSaveRecipeLocally() throws IllegalStateException, IOException{
		Recipe recipe = new Recipe("Name","Instructions");
		recipe.setId(1900);
		RecipeManager manager = new RecipeManager(new DataManager(getActivity(),TEST_FILE_NAME));
<<<<<<< HEAD

=======
		
>>>>>>> branch 'master' of ssh://git@github.com/CMPUT301W13T03/classproject.git
		manager.publishRecipeToWeb(recipe);
		Recipe downloadedRecipe = manager.getSingleRecipe(1900);
		try {
			manager.saveRecipe(downloadedRecipe, getActivity());
		} catch (FullFileException e) {
			fail("Full file exception");
			e.printStackTrace();
		}
<<<<<<< HEAD
		//id would be 0  because its the first one saved.
=======
		//id would be 0 because its the first one saved.
>>>>>>> branch 'master' of ssh://git@github.com/CMPUT301W13T03/classproject.git
		Recipe savedRecipe = manager.getLocallySavedRecipeById(0);
		assertNotNull(savedRecipe);
		assertEquals(savedRecipe.getName(),"Name");
		assertEquals(savedRecipe.getInstructions(),"Instructions");
		
	}
	@Test
	public void testSearchForRecipe() throws IllegalStateException, IOException{
		Recipe recipe0 = new Recipe("Salad", "Put some vegetables in a bowl.");
		Recipe recipe1 = new Recipe("Cookie", "Make a cookie");
<<<<<<< HEAD
		Recipe recipe2 = new Recipe("Cookies", "Who makes one cookie? Idiot.");
		recipe0.setId(0);
		recipe1.setId(1);
		recipe2.setId(2);
		RecipeManager manager = new RecipeManager(new DataManager(getActivity(),TEST_FILE_NAME));
		//THIS IS NOT YET IMPLEMENTED
		//manager.resetServerID();
=======
		RecipeManager manager = new RecipeManager(new DataManager(getActivity(),TEST_FILE_NAME));
		manager.publishRecipeToWeb(recipe0);
>>>>>>> branch 'master' of ssh://git@github.com/CMPUT301W13T03/classproject.git
		manager.publishRecipeToWeb(recipe1);
		manager.publishRecipeToWeb(recipe0);
		manager.publishRecipeToWeb(recipe2);
		ArrayList<Recipe> results = manager.searchWebForRecipeByName("Cookie");
		assertNotNull(results);
		assertTrue(findRecipeInList(results, recipe1));

	}
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
