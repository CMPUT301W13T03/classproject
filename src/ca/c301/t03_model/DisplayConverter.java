package ca.c301.t03_model;

import java.util.ArrayList;

import ca.c301.t03_recipes.RecipeApplication;

/**
 * Class containing functions required to convert data for display in list views
 */
public class DisplayConverter {

	/**
	 * To convert an ArrayList<Ingredient> of ingredients into an array of Strings
	 * 
	 * @param ingredients
	 * 				Is the ArrayList to convert
	 * @return
	 * 				An array of Strings which contains the ingredients information
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
	
	/**
	 * To convert an ArrayList<Recipe> of recipes into an array of Strings for display
	 * 
	 * @param recipes
	 * 				recipe ArrayList to be converted
	 * @return
	 * 				Array containing recipe names
	 */
	public String[] convertRecipeList(ArrayList<Recipe> recipes){
		
		ArrayList<String> stringList = new ArrayList<String>();
		
		for (int i = 0; i < recipes.size(); i++) {
			stringList.add(recipes.get(i).getName());
		}
		String[] outArray = new String[stringList.size()];
		stringList.toArray(outArray);
		
		return outArray;	
		}
}
