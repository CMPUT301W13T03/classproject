package ca.c301.t03_recipes.test;

import java.util.ArrayList;

import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import ca.c301.t03_exceptions.FullFileException;
import ca.c301.t03_model.Ingredient;
import ca.c301.t03_model.Recipe;
import ca.c301.t03_model.RecipeManager;
import ca.c301.t03_model.RecipePhoto;
import ca.c301.t03_recipes.MainActivity;

public class SearchTest extends ActivityInstrumentationTestCase2<MainActivity>{

	public SearchTest() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}
	
	//Test searching for a recipe locally.
	@Test
	public void testSearchRecipe() {
		Recipe recipe = new Recipe("Name08051992", "Instructions");
		RecipeManager manager = new RecipeManager(getActivity());
		
		try {
			manager.saveRecipe(recipe);
		} catch (FullFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Recipe> retrievedRecipe = manager.searchLocalKeyword("08051992");	
		
		try {
			manager.deleteLocallySavedRecipeById(retrievedRecipe.get(0).getId());
		} catch (FullFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		// Check same recipe is returned.
		assertEquals(retrievedRecipe.get(0).getId(),recipe.getId());
		assertEquals(retrievedRecipe.get(0).getName(),recipe.getName());
		assertEquals(retrievedRecipe.get(0).getInstructions(),recipe.getInstructions());
	}
	
	//Test searching for a recipe locally using ingredients list (same implementation for online).
	@Test
	public void testSearchRecipeIngredients() {
		Recipe recipe = new Recipe("Name08051992", "Instructions");
		Recipe recipe2 = new Recipe("2Name08051992", "2Instructions");
		recipe2.setId(recipe2.getId() + 10);
			
		Ingredient ingredient = new Ingredient();
		ingredient.setName("TESTfood");
		ingredient.setAmount(1.0);
		ingredient.setUnitOfMeasurement("g");
			
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setName("TESTfood2");
		ingredient2.setAmount(1.0);
		ingredient2.setUnitOfMeasurement("g");
			
		recipe.addIngredient(ingredient);
			
		recipe2.addIngredient(ingredient);
		recipe2.addIngredient(ingredient2);
			
		RecipeManager manager = new RecipeManager(getActivity());

		try {
			manager.addIngredient(ingredient);
			manager.saveRecipe(recipe);
			manager.saveRecipe(recipe2);
		} catch (FullFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Recipe> retrievedRecipe = manager.searchLocalKeyword("08051992");
			
		retrievedRecipe = manager.ingredientMatch(retrievedRecipe);

		try {
			manager.deleteLocallySavedRecipeById(recipe.getId());
			manager.deleteLocallySavedRecipeById(recipe2.getId());
			manager.deleteIngredient(ingredient.getName());
		} catch (FullFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
			
		// Check only one recipe is returned.
		assertEquals(retrievedRecipe.size(), 1);
			
		// Check same recipe is returned.
		assertEquals(retrievedRecipe.get(0).getId(),recipe.getId());
		assertEquals(retrievedRecipe.get(0).getName(),recipe.getName());
		assertEquals(retrievedRecipe.get(0).getInstructions(),recipe.getInstructions());
	}
	
	//Test searching for a recipe locally using photos (implementation same as online).
	@Test
	public void testSearchRecipePhotos() {
		Recipe recipe = new Recipe("Name08051992", "Instructions");
		Recipe recipe2 = new Recipe("2Name08051992", "2Instructions");
		recipe2.setId(recipe2.getId() + 10);
		
		recipe.setHasPhoto();
		
		RecipeManager manager = new RecipeManager(getActivity());
		
		try {
			manager.saveRecipe(recipe);
			manager.saveRecipe(recipe2);
		} catch (FullFileException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Recipe> retrievedRecipe = manager.searchLocalKeyword("08051992");	
		
		retrievedRecipe = manager.photoCheck(retrievedRecipe);
			
		try {
			manager.deleteLocallySavedRecipeById(recipe.getId());
			manager.deleteLocallySavedRecipeById(recipe2.getId());
		} catch (FullFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Check only one recipe is returned.
		assertEquals(retrievedRecipe.size(), 1);
		
		// Check same recipe is returned.
		assertEquals(retrievedRecipe.get(0).getId(),recipe.getId());
		assertEquals(retrievedRecipe.get(0).getName(),recipe.getName());
		assertEquals(retrievedRecipe.get(0).getInstructions(),recipe.getInstructions());
	}
}
