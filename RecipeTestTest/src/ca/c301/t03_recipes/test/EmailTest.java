package ca.c301.t03_recipes.test;

import org.junit.Test;

import ca.c301.t03_model.Recipe;
import ca.c301.t03_model.RecipeManager;
import ca.c301.t03_recipes.MainActivity;

public class EmailTest extends IntentCatchingTemplate{

	public EmailTest() {
		super(MainActivity.class);
	}
	/**
	 * Test to see if any intent is sent.
	 */
	@Test
	public void testSendEmail(){
		caughtIntent = null;
		RecipeManager manager = new RecipeManager(getActivity());
		Recipe recipe = new Recipe(0,"Nachos","Cheese and nachos and baking");
		manager.emailRecipe("null@null", recipe);
		assertNotNull(caughtIntent);
		assertTrue(caughtIntent.getAction() == android.content.Intent.ACTION_SEND);

	}
	@Test
	public void testEmailFormat(){
		RecipeManager manager = new RecipeManager(getActivity());
		String recipeName = "Nachos";
		String recipeInstruction = "Cheese and nachos and baking";
		String emailAddress = "null@null";
		
		Recipe recipe = new Recipe(0,recipeName,recipeInstruction);
		manager.emailRecipe(emailAddress, recipe);
		
		assertNotNull(caughtIntent);

		
		//TODO do we need the pictures also? If so use other equals
		assertEquals(caughtIntent.getType(), "plain/text");
//		assertEquals(caughtIntent.getType(), "image/jpg");
		
		assertEquals(caughtIntent.getStringExtra(android.content.Intent.EXTRA_EMAIL),emailAddress);
		
		String body = caughtIntent.getStringExtra(android.content.Intent.EXTRA_TEXT);
		assertTrue(body.contains(recipeName));
		assertTrue(body.contains(recipeInstruction));
		
	}
}
