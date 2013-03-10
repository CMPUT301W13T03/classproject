package ca.c301.t03_model;

import java.util.ArrayList;

import ca.c301.t03_recipes.RecipeApplication;

public class DisplayConverter {

	/**
	 * To convert an ArrayList<Ingredient> of ingredients into an array of Strings
	 * @param ingredients Is the ArrayList to convert
	 * @return Returns an array of Strings which contains the ingredients information
	 */
	public String[] convertIngredientsList(ArrayList<Ingredient> ingredients) {
		
		ArrayList<String> stringList = new ArrayList<String>();
		
		for (int i = 0; i < ingredients.size(); i++) {
			stringList.add(ingredients.get(i).getName() + "\n" + String.valueOf(ingredients.get(i).getAmount()) + " " + ingredients.get(i).getUnitOfMeasurement());
		}
		
		String[] outArray = new String[stringList.size()];
		stringList.toArray(outArray);
		
		return outArray;
	}
	
	public String[] convertRecipeList(ArrayList<Integer> id, RecipeApplication app) {
		
		ArrayList<String> stringList = new ArrayList<String>();
		
		for (int i = 0; i < id.size(); i++) {
			stringList.add(app.getRecipeManager().getLocallySavedRecipeById(id.get(i)).getName());
		}
		
		String[] outArray = new String[stringList.size()];
		stringList.toArray(outArray);
		
		return outArray;
	}
}
