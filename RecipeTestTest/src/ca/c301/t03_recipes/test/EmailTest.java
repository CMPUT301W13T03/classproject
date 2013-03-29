package ca.c301.t03_recipes.test;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import android.content.Intent;
import android.util.Log;

import ca.c301.t03_model.DataManager;
import ca.c301.t03_model.Ingredient;
import ca.c301.t03_model.Recipe;
import ca.c301.t03_model.RecipeManager;
import ca.c301.t03_recipes.MainActivity;

public class EmailTest extends IntentCatchingTemplate{

	String recipeName;
	String recipeInstruction;
	Recipe recipe;
	String emailAddress;
	public EmailTest() {
		super(MainActivity.class);
	}
	@Before
	public void setUp() throws Exception{
		caughtIntent = null;
		RecipeManager manager = new RecipeManager();
		recipeName = "Nachos";
		emailAddress = "fake@fake.com";
		recipeInstruction = "Cheese and nachos and baking";
		
		recipe = new Recipe(recipeName,recipeInstruction);
		recipe.setIngredients(new ArrayList<Ingredient>());
		
		//Need to pass intentCatcher as the context so it can catch
		// the email intent.
		manager.emailRecipe(emailAddress, recipe, fakeActivity);
	}
	/*
	 * Tests to see if any intent is sent to send an email.
	 */
	@Test
	public void testEmailIntentNotNull(){
		assertNotNull(caughtIntent);
	}
	@Test
	public void testEmailIntentAction(){
		assertTrue(caughtIntent.getAction() == android.content.Intent.ACTION_SEND);
	}
	/*
	 * Tests to make sure the intent for sending emails is correctly formatted and
	 * contains data from the recipe in some form.
	 */
	@Test
	public void testIntentFormat(){	
		assertEquals(caughtIntent.getType(), "message/rfc822");		
	}
	@Test
	public void testCorrectAddress(){
		String[] addressList = caughtIntent.getStringArrayExtra(android.content.Intent.EXTRA_EMAIL);
		assertEquals(emailAddress,addressList[0]);
	}
	@Test
	public void testNamePresent(){
		String body = caughtIntent.getStringExtra(android.content.Intent.EXTRA_TEXT);
		assertTrue(body.contains(recipeName));
	}
	@Test
	public void testInstructionsPresent(){
		String body = caughtIntent.getStringExtra(android.content.Intent.EXTRA_TEXT);
		assertTrue(body.contains(recipeInstruction));
	}
}
