package ca.c301.t03_recipes.test;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import android.test.ActivityInstrumentationTestCase2;
import ca.c301.t03_exceptions.FullFileException;
import ca.c301.t03_model.DataManager;
import ca.c301.t03_model.Ingredient;
import ca.c301.t03_model.Recipe;
import ca.c301.t03_model.RecipeManager;
import ca.c301.t03_recipes.MainActivity;

/*
 * Tests dealing specifically with Ingredients.
 */
public class IngredientsTest extends ActivityInstrumentationTestCase2<MainActivity>{
	
	public IngredientsTest()
	{
		super(MainActivity.class);
	}
	
	//Test creating an ingredient and adding it to local storage.
	@Test
	public void testCreateIngredient(){
		Ingredient ingredient = new Ingredient();
		RecipeManager manager = new RecipeManager(getActivity());

		ingredient.setName("name");
		ingredient.setAmount(1.0);
		ingredient.setUnitOfMeasurement("g");
		
		int count = manager.getIngredientCount(ingredient.getName());
			
		try {
			manager.addIngredient(ingredient);
		} catch (FullFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(count + 1,manager.getIngredientCount(ingredient.getName()));
			
		try {
			manager.deleteIngredient(ingredient.getName());
		} catch (FullFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	//Test retrieving an ingredient already in local storage.
	@Test
	public void testRetrieveIngredient() {
		Ingredient ingredient = new Ingredient();
		RecipeManager manager = new RecipeManager(getActivity());

		ingredient.setName("name");
		ingredient.setAmount(1.0);
		ingredient.setUnitOfMeasurement("g");
		
		int count = manager.getIngredientCount(ingredient.getName());
			
		try {
			manager.addIngredient(ingredient);
		} catch (FullFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<Ingredient> retrievedIngredient = manager.getAllIngredients();

		// Check same ingredient is returned.
		assertEquals(retrievedIngredient.get(0).getName(),ingredient.getName());
		assertEquals(retrievedIngredient.get(0).getAmount(),ingredient.getAmount());
		assertEquals(retrievedIngredient.get(0).getUnitOfMeasurement(),ingredient.getUnitOfMeasurement());
			
		try {
			manager.deleteIngredient(retrievedIngredient.get(0).getName());
		} catch (FullFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//Test to make sure an ingredient can be deleted from local storage.
	@Test
	public void testDeleteIngredient() throws IllegalStateException, IOException{
		Ingredient ingredient = new Ingredient();
		RecipeManager manager = new RecipeManager(getActivity());

		ingredient.setName("name");
		ingredient.setAmount(1.0);
		ingredient.setUnitOfMeasurement("g");
		
		try {
			manager.addIngredient(ingredient);
			manager.deleteIngredient(ingredient.getName());
		} catch (FullFileException e) {
			e.printStackTrace();
		}
		assertEquals(manager.getIngredientCount(ingredient.getName()), 0);
	}
	//Test to make sure multiple ingredients can be saved and retrieved.
	@Test
	public void testSaveAndRetrieveFromMany(){
		Ingredient ingredient0 = new Ingredient();
		Ingredient ingredient1 = new Ingredient();
		Ingredient ingredient2 = new Ingredient();
		
		ingredient0.setName("ingredient0");
		ingredient0.setAmount(0.0);
		ingredient0.setUnitOfMeasurement("g");
		
		ingredient0.setName("ingredient1");
		ingredient0.setAmount(1.0);
		ingredient0.setUnitOfMeasurement("g");
		
		ingredient0.setName("ingredient2");
		ingredient0.setAmount(2.0);
		ingredient0.setUnitOfMeasurement("g");
			
		RecipeManager manager = new RecipeManager(getActivity());

		try {
			manager.addIngredient(ingredient0);
			manager.addIngredient(ingredient1);
			manager.addIngredient(ingredient2);
		} catch (FullFileException e) {
			e.printStackTrace();
		}

		ArrayList<Ingredient> retrievedIngredient = manager.getAllIngredients();
			
		assertEquals(retrievedIngredient.get(1).getName(),ingredient1.getName());
		assertEquals(retrievedIngredient.get(1).getAmount(),ingredient1.getAmount());
		assertEquals(retrievedIngredient.get(1).getUnitOfMeasurement(),ingredient1.getUnitOfMeasurement());
			
		try {
			manager.deleteIngredient(ingredient0.getName());
			manager.deleteIngredient(ingredient1.getName());
			manager.deleteIngredient(ingredient2.getName());
		} catch (FullFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
