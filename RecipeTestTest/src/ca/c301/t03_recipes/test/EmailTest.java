package ca.c301.t03_recipes.test;

import java.util.ArrayList;

import org.junit.Test;

import android.content.Intent;
import android.util.Log;

import ca.c301.t03_model.Ingredient;
import ca.c301.t03_model.Recipe;
import ca.c301.t03_model.RecipeManager;
import ca.c301.t03_recipes.MainActivity;

public class EmailTest extends IntentCatchingTemplate{

	public EmailTest() {
		super(MainActivity.class);
	}
	/*
	 * Test to see if any intent is sent to send an email.
	 */
	@Test
	public void testSendEmail(){
		caughtIntent = null;
		RecipeManager manager = new RecipeManager(intentCatcher);
		String recipeName = "Nachos";
		String recipeInstruction = "Cheese and nachos and baking";
		Recipe recipe = new Recipe(recipeName,recipeInstruction);
		recipe.setIngredients(new ArrayList<Ingredient>());
		
		//Need to pass intentCatcher as the context so it can catch
		// the email intent.
		manager.emailRecipe("null@null", recipe, intentCatcher);
		assertNotNull(caughtIntent);
		
		assertTrue(caughtIntent.getAction() == android.content.Intent.ACTION_SEND);

	}
	/*
	 * Test to make sure the intent for sending emails is correctly formatted and
	 * contains data from the recipe in some form.
	 */
	@Test
	public void testEmailFormat(){
		RecipeManager manager = new RecipeManager(intentCatcher);
		String recipeName = "Nachos";
		String recipeInstruction = "Cheese and nachos and baking";
		String emailAddress = "null@null";
		Recipe recipe = new Recipe(recipeName,recipeInstruction);
		recipe.setIngredients(new ArrayList<Ingredient>());
		
		manager.emailRecipe(emailAddress, recipe, intentCatcher);
		
		assertNotNull(caughtIntent);

		
		//TODO do we need the pictures also? If so use other equals
		assertEquals(caughtIntent.getType(), "plain/text");
//		assertEquals(caughtIntent.getType(), "image/jpg");
		
		assertEquals(caughtIntent.getStringExtra(android.content.Intent.EXTRA_EMAIL),emailAddress);
		
		String body = caughtIntent.getStringExtra(android.content.Intent.EXTRA_TEXT);
		Log.i("EmailTest", "Email Body: " + body);
		assertTrue(body.contains(recipeName));
		assertTrue(body.contains(recipeInstruction));
		
	}
}
