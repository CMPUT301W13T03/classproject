package ca.c301.t03_model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import android.R;
import android.content.Context;

public class RecipeManager {

	public RecipeManager(Context c) {
		dataManager = new DataManager(c);
	}

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

	public Recipe getSingleRecipe(int id) {
		HTTPManager tempHTTPManager = new HTTPManager();
		return tempHTTPManager.getRecipe(id);
	}

	/**
			 */
	public void publishRecipeToWeb(Recipe recipe) {
		try {
			HTTPManager tempHTTPManager = new HTTPManager();
			tempHTTPManager.addRecipe(recipe);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @throws FullFileException 
				 */
	public void saveRecipe(Recipe recipe, Context c) throws FullFileException {
		dataManager.getRecipeBook().addRecipe(recipe);
		dataManager.saveToFile(c);
	}

	/**
					 */
	public void emailRecipe(String emailAddress, Recipe recipe) {
		EmailHandler emailer = new EmailHandler();
		emailer.sendRecipe(emailAddress, recipe);
	}

	/**
	 * @return
	 */
	public Recipe getLocallySavedRecipeById(int id) {
		return dataManager.getRecipeBook().findRecipeByID(id);
	}

	/**
		 */
	public void deleteLocallySavedRecipeById(int id) {
		dataManager.getRecipeBook().deleteRecipeByID(id);
	}

	/**
		 */
	public ArrayList<Recipe> searchWebForRecipeByName(String name) {
		return null;
	}

	public void takePhotoForRecipe(int recipeId) {
		Camera camera = new Camera();
		RecipePhoto photo = camera.takePhoto();
		Recipe recipe = dataManager.getRecipeBook().findRecipeByID(recipeId);
		recipe.addPhoto(photo);
	}

	/**
			 */
	public void addIngredientToPantry(Ingredient ingredient) {
	}

	/**
		 */
	public Ingredient getLocalIngredientById(int id) {
		return null;
	}

}
