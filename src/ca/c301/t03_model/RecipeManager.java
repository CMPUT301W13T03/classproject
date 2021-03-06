package ca.c301.t03_model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;

import ca.c301.t03_exceptions.FullFileException;
import ca.c301.t03_exceptions.NullStringException;
import ca.c301.t03_recipes.RecipeApplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

/**
 * Handles recipe manipulation
 */
public class RecipeManager {

	private static final String TAG = "RecipeManager";
	//	Our server URL: "http://cmput301.softwareprocess.es:8080/cmput301w13t03/recipes/"
	//  Testing Server URL: "http://cmput301.softwareprocess.es:8080/testing/recipezzz/"
	//Our Prototype is still directed at the test server as we are still testing, swapping this to the server URL is just a switch of this line. 
	public String URL = "http://cmput301.softwareprocess.es:8080/cmput301w13t03/recipes/";
	public String IMGURL = "http://cmput301.softwareprocess.es:8080/cmput301w13t03/";
	/**
	 * Constructor with a given context
	 * 
	 * @param c
	 * 				Is the Android context
	 */
	public RecipeManager(Context c) {
		dataManager = new DataManager(c);
	}

	/**
	 * Constructor with a given dataManager
	 * 
	 * @param dataManager
	 * 				Is the dataManager to set
	 */
	public RecipeManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}

	/*
	 * Constructor that doesn't initialize dataManager, this
	 * should probably only be used for testing.
	 */
	public RecipeManager() {
	}

	/**
	 * @uml.property name="dataManager"
	 * @uml.associationEnd aggregation="shared"
	 *                     inverse="recipeManager:ca.c301.t03_model.DataManager"
	 */
	private DataManager dataManager;

	// TODO: This method might get revised to getWebRecipesByName or something
	// like that, since we don't
	// want to pull the entire list every time ideally. Need to look closer at
	// the webservice stuff
	
	/*
	 * Getter of web recipes - NOT IMPLEMENTED
	 * @return
	 */
	public Collection<Recipe> getWebRecipes() {
		return null;
	}

	/**
	 * To retrieve a single recipe from the webservice with a given ID
	 * 
	 * @param id
	 * 				Is the id of the recipe to retrieve
	 * @return
	 * 				The recipe from the webservice
	 */
	public Recipe getSingleRecipe(int id) {
		HTTPManager tempHTTPManager = new HTTPManager();
		Recipe recipe = tempHTTPManager.getRecipe(id, URL);
		if(recipe.hasPhoto()) tempHTTPManager.getImages(recipe, IMGURL);
		return recipe;
	}
	/**
	 * Sets the URL for this recipe manager
	 * 
	 * @param URL
	 * 				Is where searching and adding will be done
	 */
	public void setURL(String URL){
		this.URL = URL;
	}

	/**
	 * To publish a given recipe to the webservice
	 * 
	 * @param recipe
	 * 				Is the recipe to be published
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public void publishRecipeToWeb(Recipe recipe) throws IllegalStateException, IOException {
		HTTPManager tempHTTPManager = new HTTPManager();
		if(recipe.hasPhoto()) tempHTTPManager.addImages(recipe, IMGURL);
		tempHTTPManager.addRecipe(recipe, URL);
	}

	/**
	 * To save a given recipe
	 * 
	 * @param recipe
	 * 				Is the recipe to be saved
	 * @throws FullFileException
	 */
	public void saveRecipe(Recipe recipe) throws FullFileException {
		dataManager.getRecipeDatabase().addRecipe(recipe);
	}

	/**
	 * To update a given recipe in the database
	 * 
	 * @param recipe
	 * 				Is the recipe to be set
	 * @throws FullFileException
	 */
	public void setRecipe(Recipe recipe) throws FullFileException {
		dataManager.getRecipeDatabase().updateRecipe(recipe);
	}

	/**
	 * To email a given recipe to a given email
	 * 
	 * @param emailAddress
	 * 				Is the email address to send the recipe to
	 * @param recipe
	 * 				Is the recipe to be emailed
	 * @param c
	 * 				Is the Android context
	 */
	public void emailRecipe(String emailAddress, Recipe recipe, Context c) {
		EmailHandler emailer = new EmailHandler();
		emailer.sendRecipe(emailAddress, recipe, c);
	}

	/**
	 * To get a locally saved recipe with a given id
	 * 
	 * @param id
	 * 				The ID of the recipe to retrieve
	 * @return
	 * 				The local recipe with the given id
	 */
	public Recipe getLocallySavedRecipeById(int id) {
		return dataManager.getRecipeDatabase().getRecipe(id);
	}

	/**
	 * To delete a locally saved recipe with a given id
	 * 
	 * @param id
	 * 				Is the id of the recipe to be deleted
	 */
	public void deleteLocallySavedRecipeById(int id) throws FullFileException {
		Recipe recipe = dataManager.getRecipeDatabase().getRecipe(id);
		ArrayList<RecipePhoto> photos = new ArrayList<RecipePhoto>(recipe.getRecipePhoto());
		for(int i=0; i < photos.size();i++){
			photos.get(i).getFile().delete();
		}
		dataManager.getRecipeDatabase().deleteRecipe(id);
	}

	/**
	 * To search for and return a possible list of webservice recipes which matched a given name
	 * 
	 * @param name
	 * 				Is the name of the recipe to be searched for
	 * @return
	 * 				An ArrayList<Recipe> containing the search results
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws NullStringException 
	 */
	public ArrayList<Recipe> searchWebForRecipeByName(String name) throws ClientProtocolException, IOException, NullStringException {
		HTTPManager tempHTTPManager = new HTTPManager();
		Log.i("RecipeManager","Searching web for " + name);
		return tempHTTPManager.searchRecipes(name, URL);

	}

	/**
	 * To take a photo for a recipe with a given id
	 * 
	 * @param a
	 * 				Is the activity where the photo is being taken
	 * @return
	 * 				The file where the photo is saved
	 */
	public File takePhotoForRecipe(Activity a) {
		Camera camera = new Camera();
		return camera.takePhoto(a);
	}

	/**
	 * To add an ingredient to the Virtual Pantry, which is the ingredients database
	 * 
	 * @param ingredient
	 * 				Is the ingredient to add
	 */
	public void addIngredient(Ingredient ingredient) throws FullFileException {
		dataManager.getIngredientDatabase().addIngredient(ingredient);
	}

	/**
	 * To get an ingredient from the Virtual Pantry by a given name
	 * 
	 * @param name
	 * 				The name of the ingredient to retrieve
	 * @return
	 * 				The ingredient from the Virtual Pantry
	 */
	public Ingredient getIngredient(String name) {
		return dataManager.getIngredientDatabase().getIngredient(name);
	}
	
	/**
	 * To get all of the ingredients in the Virtual Pantry
	 * 
	 * @return
	 * 				An ArrayList of all ingredients in the Virtual Pantry
	 */
	public ArrayList<Ingredient> getAllIngredients() {
		return dataManager.getIngredientDatabase().getAllIngredients();
	}

	/**
	 * To delete an ingredient with a given name from the Virtual Pantry
	 * 
	 * @param name
	 * 				Is the name of the ingredient to delete
	 */
	public void deleteIngredient(String name) throws FullFileException {
		dataManager.getIngredientDatabase().deleteIngredient(name);
	}

	/**
	 * To get the number of ingredients which match a given name from the Virtual Pantry
	 * 
	 * @param name
	 * 				The name of ingredient to count
	 * @return
	 * 				The number of ingredients matching the name
	 */
	public int getIngredientCount(String name) {
		return dataManager.getIngredientDatabase().getIngredientCount(name);
	}

	/**
	 * To update an ingredient in the Virtual Pantry
	 * 
	 * @param ingredient
	 * 				The ingredient to update
	 * @param name
	 * 				The name of the ingredient to update
	 */
	public void updateIngredient(Ingredient ingredient, String name) throws FullFileException {
		dataManager.getIngredientDatabase().updateIngredient(ingredient, name);
	}

	/**
	 * To get the names of all ingredients in the Virtual Pantry
	 * 
	 * @return
	 * 				An ArrayList of Strings which are the ingredient names
	 */
	public ArrayList<String> getAllIngredientNames() {
		return dataManager.getIngredientDatabase().getAllIngredientNames();
	}

	/**
	 * Locally searches, returning IDs of all local recipes
	 * 
	 * @return
	 *				Arraylist<Integer> of IDs of all local recipes
	 */
	public ArrayList<Recipe> getAllLocalRecipes() {
		return dataManager.getRecipeDatabase().getAllRecipes();
	}

	/**
	 * Locally searches by keyword, returning IDs of all matching recipes
	 * 
	 * @param keyword
	 * 				Is the keyword used in the search
	 * @return
	 * 				Arraylist<Integer> of IDs of all local recipes with names containing the keyword
	 */
	public ArrayList<Recipe> searchLocalKeyword(String keyword) {
		return dataManager.getRecipeDatabase().searchRecipes(keyword);
	}

	/**
	 * To filter a given ArrayList of recipes to return those which only use ingredients from the Virtual Pantry
	 * 
	 * @param recipes
	 * 				The ArrayList of recipes to filter
	 * @return
	 * 				The filtered ArrayList of recipes
	 */
	public ArrayList<Recipe> ingredientMatch(ArrayList<Recipe> recipes) {

		ArrayList<Recipe> output = new ArrayList<Recipe>();

		for (int x = 0; x < recipes.size(); x++) {
			ArrayList<Ingredient> ingredients = recipes.get(x).getIngredients();

			boolean pantryHasAllIngredients = true;

			for (int i = 0; i < ingredients.size(); i++) {
				pantryHasAllIngredients = isInPantry(ingredients.get(i));
				if(!pantryHasAllIngredients) break;
			}
			if (pantryHasAllIngredients) {
				output.add(recipes.get(x));
			}
		}
		return output;
	}
	/**
	 * Checks to see if the ingredient is in the Virtual Pantry
	 * 
	 * @param ingredient
	 * 				The ingredient to check in the Virtual Pantry
	 * @return
	 * 				Boolean, true if the ingredient is in the pantry
	 */
	private boolean isInPantry(Ingredient ingredient)
	{
		ArrayList<String> pantry = this.getAllIngredientNames();
		for (int j = 0; j < pantry.size(); j++) {
			if (ingredient.getName().equalsIgnoreCase(pantry.get(j))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * To filter a given ArrayList of recipes to return those which have at least one photo
	 * 
	 * @param recipes
	 * 				The ArrayList of recipes to filter
	 * @return
	 * 				The filtered ArrayList of recipes
	 */
	public ArrayList<Recipe> photoCheck(ArrayList<Recipe> recipes) {

		ArrayList<Recipe> output = new ArrayList<Recipe>();

		for (int i = 0; i < recipes.size(); i++) {
			if (recipes.get(i).hasPhoto()) {
				output.add(recipes.get(i));
			}
		}

		return output;
	}

	/**
	 * To generate a bitmap given a width and height
	 * 
	 * @param i
	 * 				Is the width of the bitmap
	 * @param j
	 * 				Is the height of the bitmap
	 * @return
	 * 				The generated bitmap
	 */
	public Bitmap generateBitmap(int i, int j) {
		Camera camera = new Camera();
		return camera.generateBitmap(i, j);
	}
	
	/**
	 * To get a count of the number of recipes with a given ID in the recipe database
	 * 
	 * @param id
	 * 				The ID of the recipe to get a count for
	 * @return
	 * 				The number of recipes in the database
	 */
	public int getCount(int id) {
		return dataManager.getRecipeDatabase().getRecipeCount(id);
	}

}
