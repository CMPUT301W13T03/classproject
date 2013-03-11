package ca.c301.t03_model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;

import android.content.Context;
import android.util.Log;

public class RecipeManager {

	//	Our server URL: "http://cmput301.softwareprocess.es:8080/CMPUT301W13T03/recipes/"
	//  Testing Server URL: "http://cmput301.softwareprocess.es:8080/testing/recipezzz/"
	//Our Prototype is still directed at the test server as we are still testing, swapping this to the server URL is just a switch of this line. 
	public String URL = "http://cmput301.softwareprocess.es:8080/testing/recipezzz/";
	/**
	 * Constructor with a given context
	 * @param c Is the Android context
	 */
	public RecipeManager(Context c) {
		dataManager = new DataManager(c);
	}

	/**
	 * Constructor with a given dataManager
	 * @param dataManager Is the dataManager to set
	 */
	public RecipeManager(DataManager dataManager) {
		this.dataManager = dataManager;
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
	/**
	 * Getter of web recipes - NOT IMPLEMENTED
	 * @return
	 */
	public Collection<Recipe> getWebRecipes() {
		return null;
	}

	/**
	 * To retrieve a single recipe from the webservice with a given id
	 * @param id Is the id of the recipe to retrieve
	 * @return Returns the recipe from the webservice
	 */
	public Recipe getSingleRecipe(int id) {
		HTTPManager tempHTTPManager = new HTTPManager();
		return tempHTTPManager.getRecipe(id, URL);
	}
	/**
	 * Sets the URL for this recipe manager
	 * @param URL where searching and adding will be done
	 */
	public void setURL(String URL){
		this.URL = URL;
	}

	/**
	 * To publish a given recipe to the webservice
	 * @param recipe is the recipe to be published
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public void publishRecipeToWeb(Recipe recipe) throws IllegalStateException, IOException {
		HTTPManager tempHTTPManager = new HTTPManager();
		tempHTTPManager.addRecipe(recipe, URL);
	}

	/**
	 * To save a given recipe
	 * @param recipe Is the recipe to be saved
	 * @param c Is the Android context
	 * @throws FullFileException
	 */
	public void saveRecipe(Recipe recipe, Context c) throws FullFileException {
		dataManager.getRecipeBook().addRecipe(recipe);
		dataManager.saveToFile(c);
	}

	/**
	 * To set a given recipe with a given ID
	 * @param id Is the ID that new recipe is to be set at
	 * @param recipe Is the recipe to be set
	 * @param c Is the application context
	 * @throws FullFileException
	 */
	public void setRecipe(int id, Recipe recipe, Context c) throws FullFileException {
		dataManager.getRecipeBook().setRecipeByID(id, recipe);
		dataManager.saveToFile(c);
	}

	/**
	 * To email a given recipe to a given email
	 * @param emailAddress Is the email address to send the recipe to
	 * @param recipe Is the recipe to be emailed
	 */
	public void emailRecipe(String emailAddress, Recipe recipe) {
		EmailHandler emailer = new EmailHandler();
		emailer.sendRecipe(emailAddress, recipe);
	}

	/**
	 * To get a locally saved recipe with a given id
	 * @return Returns the local recipe with the given id
	 */
	public Recipe getLocallySavedRecipeById(int id) {
		return dataManager.getRecipeBook().findRecipeByID(id);
	}

	/**
	 * To delete a locally saved recipe with a given id
	 * @param id Is the id of the recipe to be deleted
	 */
	public void deleteLocallySavedRecipeById(int id, Context c) throws FullFileException {
		dataManager.getRecipeBook().deleteRecipeByID(id);
		dataManager.saveToFile(c);
	}

	/**
	 * To search for and return a possible list of webservices recipes which matched a given name
	 * @param name Is the name of the recipe to be searched for
	 * @return Returns an ArrayList<Recipe> containing the search results
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public ArrayList<Recipe> searchWebForRecipeByName(String name) throws ClientProtocolException, IOException {
		HTTPManager tempHTTPManager = new HTTPManager();
		Log.i("RecipeManager","Searching web for " + name);
		return tempHTTPManager.searchRecipes(name, URL);

	}

	/**
	 * To take a photo for a recipe with a given id
	 * @param recipeId Is the id of the recipe to take a photo for
	 */
	public void takePhotoForRecipe(int recipeId) {
		Camera camera = new Camera();
		RecipePhoto photo = camera.takePhoto();
		Recipe recipe = dataManager.getRecipeBook().findRecipeByID(recipeId);
		recipe.addPhoto(photo);
	}

	/**
	 * To add an ingredient to the Virtual Pantry
	 * @param ingredient Is the ingredient to add
	 */
	public void addIngredientToPantry(Ingredient ingredient) {
	}

	/**
	 * To get an ingredient by id
	 * @param id Is the id of the ingredient to get
	 * @return Returns the ingredient with the given id
	 */
	public Ingredient getLocalIngredientById(int id) {
		return null;
	}

	/**
	 * To get all local recipes
	 * @return Returns ArrayList of all local recipes
	 */
	public ArrayList<Recipe> getRecipes() {
		return dataManager.getRecipeBook().getRecipes();
	}

	/**
	 * Locally searches, returning IDs of all local recipes
	 * @return Returns Arraylist<Integer> of IDs of all local recipes
	 */
	public ArrayList<Integer> searchLocalAll() {

		ArrayList<Recipe> recipes = this.getRecipes();
		ArrayList<Integer> ids = new ArrayList<Integer>();

		for (int i = 0; i < recipes.size(); i++) {
			ids.add(recipes.get(i).getId());
		}

		return ids;
	}

	/**
	 * Locally searches by keyword, returning IDs of all matching recipes
	 * @param keyword Is the keyword used in the search
	 * @return Returns Arraylist<Integer> of IDs of all local recipes with names containing the keyword
	 */
	public ArrayList<Integer> searchLocalKeyword(String keyword) {

		ArrayList<Recipe> recipes = this.getRecipes();
		ArrayList<Integer> ids = new ArrayList<Integer>();

		Pattern pattern = Pattern.compile(".*" + keyword + ".*", Pattern.CASE_INSENSITIVE);
		Matcher matcher;

		for (int i = 0; i < recipes.size(); i++) {
			matcher = pattern.matcher(recipes.get(i).getName());
			if (matcher.find()) {
				ids.add(recipes.get(i).getId());
			}
		}

		return ids;
	}

}
