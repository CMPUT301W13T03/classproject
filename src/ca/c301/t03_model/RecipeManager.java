package ca.c301.t03_model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;

import ca.c301.t03_recipes.RecipeApplication;

import android.R;
import android.content.Context;

public class RecipeManager {

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

	/**
		 */
	// TODO: This method might get revised to getWebRecipesByName or something
	// like that, since we don't
	// want to pull the entire list every time ideally. Need to look closer at
	// the webservice stuff
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
		return tempHTTPManager.getRecipe(id);
	}

	/**
	 * To publish a given recipe to the webservice
	 * @param recipe Is the recipe to be published
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public void publishRecipeToWeb(Recipe recipe) throws IllegalStateException, IOException {
			HTTPManager tempHTTPManager = new HTTPManager();
			tempHTTPManager.addRecipe(recipe);
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
	
	public void resetServerID(){
		HTTPManager httpManager = new HTTPManager();
		try {
			httpManager.setID(0);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * To search for and return a possible list of webservices recipes which matched a given name
	 * @param name Is the name of the recipe to be searched for
	 * @return Returns an ArrayList<Recipe> containing the search results
	 */
	public ArrayList<Recipe> searchWebForRecipeByName(String name) {
		HTTPManager httpManager = new HTTPManager();
		ArrayList<Recipe> results = new ArrayList<Recipe>();
		try {
			results = httpManager.searchRecipes(name);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
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

	public ArrayList<Recipe> getRecipes() {
		return dataManager.getRecipeBook().getRecipes();
	}

	public void saveWebRecipeByID(int i) {
		// TODO Auto-generated method stub

	}
	
	public void publishToFail(Recipe recipe) throws ClientProtocolException, IOException{
		HTTPManager httpManager = new HTTPManager();
		httpManager.addToInvalid(recipe);
	}
	
	public ArrayList<Integer> searchLocalAll() {
		
		ArrayList<Recipe> recipes = this.getRecipes();
		ArrayList<Integer> ids = new ArrayList<Integer>();
		
		for (int i = 0; i < recipes.size(); i++) {
			ids.add(recipes.get(i).getId());
		}
		
		return ids;
	}
	
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
