package ca.c301.t03_recipes.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import ca.c301.t03_model.DataManager;
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
		//Delete any existing file.
		getActivity().getFileStreamPath(DataManager.FILE_NAME).delete();
		
		Recipe recipe = new Recipe(0, "Salad", "Put in some veggies brother.");
		RecipeManager manager = new RecipeManager(getActivity());
		manager.saveRecipe(recipe, getActivity());
		Recipe retrievedRecipe = manager.getLocallySavedRecipeById(0);
		// Check same recipe is returned.
		assertSame(retrievedRecipe, recipe);

		// Check file status
		String filename = DataManager.FILE_NAME;
		DataManager savedManager;
		FileInputStream fin;
		try {
			fin = getActivity().openFileInput(filename);
			getActivity().getFilesDir();
			ObjectInputStream ois = new ObjectInputStream(fin);
			savedManager = (DataManager) ois.readObject();
			assertNotNull(savedManager);
			assertNotNull(savedManager.findRecipeByID(0));
			assertEquals("Salad",savedManager.findRecipeByID(0).getName());
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail("Recipe file not found.");
		} catch (Exception e) {
			e.printStackTrace();
			fail("Error reading from file");
		}
	}
}
