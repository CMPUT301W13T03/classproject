package ca.c301.t03_recipes.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.junit.Before;
import org.junit.Test;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import ca.c301.t03_exceptions.FullFileException;
import ca.c301.t03_model.DataManager;
import ca.c301.t03_model.Recipe;
import ca.c301.t03_model.RecipeManager;
import ca.c301.t03_recipes.MainActivity;
/*
 * Tests dealing with the management of local recipe objects and general file storage.
 */
public class RecipeStorageTest extends ActivityInstrumentationTestCase2<MainActivity>{

	private final static String TEST_FILE_NAME = "recipe_test_file";
	public RecipeStorageTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	//Test creating a recipe and adding it to local storage.
	@Test
	public void testCreateRecipe(){
		Recipe recipe = new Recipe("Name", "Instructions");
		RecipeManager manager = new RecipeManager(getActivity());

		try {
			manager.saveRecipe(recipe);
		} catch (FullFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Recipe savedRecipe = manager.getLocallySavedRecipeById(recipe.getId());

		assertEquals(savedRecipe.getId(),recipe.getId());
		assertEquals(savedRecipe.getName(),recipe.getName());
		assertEquals(savedRecipe.getInstructions(),recipe.getInstructions());
		
		try {
			manager.deleteLocallySavedRecipeById(savedRecipe.getId());
		} catch (FullFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Test retrieving a recipe already in local storage.
	@Test
	public void testRetrieveRecipe() {
		Recipe recipe = new Recipe("Name", "Instructions");
		RecipeManager manager = new RecipeManager(getActivity());

		try {
			manager.saveRecipe(recipe);
		} catch (FullFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Recipe retrievedRecipe = manager.getLocallySavedRecipeById(recipe.getId());

		// Check same recipe is returned.
		assertEquals(retrievedRecipe.getId(),recipe.getId());
		assertEquals(retrievedRecipe.getName(),recipe.getName());
		assertEquals(retrievedRecipe.getInstructions(),recipe.getInstructions());
		
		try {
			manager.deleteLocallySavedRecipeById(retrievedRecipe.getId());
		} catch (FullFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//Test to make sure a recipe can be deleted from local storage.
	@Test
	public void testDeleteRecipe() throws IllegalStateException, IOException{
		Recipe recipe = new Recipe("Name","Instructions");

		RecipeManager manager = new RecipeManager(getActivity());
		try {
			manager.saveRecipe(recipe);
			manager.deleteLocallySavedRecipeById(recipe.getId());
		} catch (FullFileException e) {
			e.printStackTrace();
		}
		assertNull(manager.getLocallySavedRecipeById(recipe.getId()));
		


	}
	//Test to make sure multiple recipes can be saved and retrieved.
	@Test
	public void testSaveAndRetrieveFromMany(){
		Recipe recipe0 = new Recipe("Name0", "Instructions0");
		Recipe recipe1 = new Recipe("Name1", "Instructions1");
		recipe1.setId(recipe1.getId() + 5);
		Recipe recipe2 = new Recipe("Name2", "Instructions2");
		recipe2.setId(recipe2.getId() + 10);
		
		RecipeManager manager = new RecipeManager(getActivity());

		try {
			manager.saveRecipe(recipe0);
			manager.saveRecipe(recipe1);
			manager.saveRecipe(recipe2);
		} catch (FullFileException e) {
			e.printStackTrace();
		}

		Recipe retrievedRecipe = manager.getLocallySavedRecipeById(recipe1.getId());
		
		assertEquals(retrievedRecipe.getId(),recipe1.getId());
		assertEquals(retrievedRecipe.getName(),recipe1.getName());
		assertEquals(retrievedRecipe.getInstructions(),recipe1.getInstructions());
		
		try {
			manager.deleteLocallySavedRecipeById(recipe0.getId());
			manager.deleteLocallySavedRecipeById(recipe1.getId());
			manager.deleteLocallySavedRecipeById(recipe2.getId());
		} catch (FullFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	
	//Test to make sure that exception that is thrown when there is no room to save is thrown correctly.
	@Test
	public void testFullFile(){
		boolean exceptionCaught = false;
		Recipe recipe = new Recipe("Name", "Instructions");
		FullDataManager dataManager = new FullDataManager(getActivity(),TEST_FILE_NAME);
		RecipeManager manager = new RecipeManager(dataManager);
		try {
			manager.saveRecipe(recipe, getActivity());
		} catch (FullFileException e) {
			exceptionCaught = true;
		}
		assertTrue(exceptionCaught);
	}
	//Mock object for faking a full file exception.
	class FullDataManager extends DataManager{
		public FullDataManager(Context c) {
			super(c);
		}
		public FullDataManager(MainActivity activity, String testFileName) {
			super(activity,testFileName);
		}
		private static final long serialVersionUID = 3710965338088278651L;
		@Override
		protected boolean exceptionIsFullFile(IOException e)
		{
			return true;
		}

	}

	*/
	
}