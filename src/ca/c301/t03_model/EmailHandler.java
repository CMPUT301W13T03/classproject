package ca.c301.t03_model;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/**
 * Handles email operations.
 */
public class EmailHandler {

	private final String SUBJECT_PREFIX = "Check out my recipe: ";
	private final String BODY_INTRO = "";
	/**
	 * To send a given recipe to a given email address
	 * @param emailAddress Is the email address
	 * @param recipe Is the recipe to be emailed
	 */
	public void sendRecipe(String emailAddress, Recipe recipe, Context c) {
		String emailSubject = parseEmailSubject(recipe);
		String emailBody = parseEmailBody(recipe);
		
	Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.setType("message/rfc822");
		Log.i("EmailHandler","Email Address: " + emailAddress);
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[]{emailAddress});
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, emailSubject);
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, emailBody);
		emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		c.startActivity(Intent.createChooser(emailIntent, "Send Email"));
		c.startActivity(emailIntent);



	}

	private String parseEmailSubject(Recipe recipe) {
		return SUBJECT_PREFIX + recipe.getName();
	}

	private String parseEmailBody(Recipe recipe) {
		String body = new String(recipe.getName());
		body += "\n";
		body += recipe.getInstructions();
		ArrayList<Ingredient> ingrList = recipe.getIngredients();
		for(int i = 0; i < ingrList.size(); i++)
		{
			body += "\n";
			Ingredient ingr = ingrList.get(i);
			body += (ingr.getName() + " " + ingr.getAmount() + ingr.getUnitOfMeasurement());
		}
		return body;
	}

}
