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
	//Delete testfile before each test.
	@Before
	public void setUp() throws Exception{
		//Delete any existing file.
		getActivity().getFileStreamPath(TEST_FILE_NAME).delete();
	}
	
	/*
	
	//Test creating a recipe and adding it to local storage.
	@Test
	public void testCreateRecipe(){
		Recipe recipe = new Recipe("Name", "Instructions");
		DataManager testDataManager = new DataManager(getActivity(),TEST_FILE_NAME);
		RecipeManager manager = new RecipeManager(testDataManager);

		try {
			manager.saveRecipe(recipe, getActivity());
		} catch (FullFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Recipe savedRecipe = testDataManager.getRecipeBook().findRecipeByID(0);

		assertSame(savedRecipe,recipe);
	}
	//Test retrieving a recipe already in local storage.
	@Test
	public void testRetrieveRecipe() {
		RecipeBook recipeBook = new RecipeBook();
		Recipe recipe = new Recipe("Name", "Instructions");
		DataManager dataManager = new DataManager(recipeBook, null, getActivity(),TEST_FILE_NAME);
		RecipeManager manager = new RecipeManager(dataManager);

		recipeBook.addRecipe(recipe);
		Recipe retrievedRecipe = manager.getLocallySavedRecipeById(0);

		// Check same recipe is returned.
		assertSame(retrievedRecipe, recipe);
	}
	//Test to make sure DataManager file that holds recipes and ingredients is saved.
	@Test
	public void testFileCreation()
	{
		DataManager dataManager = new DataManager(getActivity(),TEST_FILE_NAME);
		RecipeManager manager = new RecipeManager(dataManager);

		FileInputStream fin;

		try {
			fin = getActivity().openFileInput(TEST_FILE_NAME);
			getActivity().getFilesDir();
			ObjectInputStream ois = new ObjectInputStream(fin);
			DataManager savedManager;
			savedManager = (DataManager) ois.readObject();
			assertNotNull(savedManager);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail("Recipe file not found.");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Error reading from file");
		}
	}
	//Test to make sure DataManager file that holds recipes and ingredients correctly stores a recipe.
	@Test
	public void testSavedRecipe(){
		Recipe recipe = new Recipe("Name", "Instructions");
		DataManager dataManager = new DataManager(getActivity(),TEST_FILE_NAME);
		RecipeManager manager = new RecipeManager(dataManager);
		try {
			manager.saveRecipe(recipe, getActivity());
		} catch (FullFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Check file status
		DataManager savedManager;
		FileInputStream fin;
		try {
			fin = getActivity().openFileInput(TEST_FILE_NAME);
			getActivity().getFilesDir();
			ObjectInputStream ois = new ObjectInputStream(fin);
			savedManager = (DataManager) ois.readObject();
			assertEquals("Name",savedManager.getRecipeBook().findRecipeByID(0).getName());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail("Recipe file not found.");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Error reading from file");
		}

	}
	//Test to make sure a recipe can be deleted from local storage.
	@Test
	public void testDeleteRecipe() throws IllegalStateException, IOException{
		Recipe recipe = new Recipe("Name","Instructions");
		recipe.setId(1900);
		RecipeManager manager = new RecipeManager(new DataManager(getActivity(),TEST_FILE_NAME));
		try {
			manager.saveRecipe(recipe, getActivity());
			manager.deleteLocallySavedRecipeById(1900, getActivity());
		} catch (FullFileException e) {
			e.printStackTrace();
		}
		assertNull(manager.getLocallySavedRecipeById(1990));
		


	}
	//Test to make sure multiple recipes can be saved and retrieved.
	@Test
	public void testSaveAndRetrieveFromMany(){
		Recipe recipe0 = new Recipe("Name0", "Instructions0");
		Recipe recipe1 = new Recipe("Name1", "Instructions1");
		Recipe recipe2 = new Recipe("Name2", "Instructions2");

		DataManager dataManager = new DataManager(getActivity(),TEST_FILE_NAME);
		RecipeManager manager = new RecipeManager(dataManager);

		try {
			manager.saveRecipe(recipe0, getActivity());
			manager.saveRecipe(recipe1, getActivity());
			manager.saveRecipe(recipe2, getActivity());
		} catch (FullFileException e) {
			e.printStackTrace();
		}

		assertSame(recipe2,manager.getLocallySavedRecipeById(2));	

	}
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