package ca.c301.t03_recipes.test;

import org.junit.Before;
import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import ca.c301.t03_model.DataManager;
import ca.c301.t03_model.Ingredient;
import ca.c301.t03_model.RecipeManager;
import ca.c301.t03_model.VirtualPantry;
import ca.c301.t03_recipes.MainActivity;


public class IngredientsTest extends ActivityInstrumentationTestCase2<MainActivity>{
	private final static String TEST_FILE_NAME = "ingredients_test_file";

	public IngredientsTest()
	{
		super(MainActivity.class);
	}
	@Before
	public void setUp() throws Exception{
		//Delete any existing file.
		getActivity().getFileStreamPath(TEST_FILE_NAME).delete();
	}
	@Test
	public void testSaveIngredient()
	{
		DataManager dataManager = new DataManager(getActivity(),TEST_FILE_NAME);
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
	public void testGetIngredient()
	{
		DataManager dataManager = new DataManager(getActivity(),TEST_FILE_NAME);
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
