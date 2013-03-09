package ca.c301.t03_model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;

public class RecipeManager {

	public RecipeManager(Context c) {
		dataManager = new DataManager(c);
	}
	public RecipeManager(DataManager dataManager)
	{
		this.dataManager = dataManager;
	}

	/**
	 * @uml.property name="hTTPManager"
	 * @uml.associationEnd inverse="recipeManager:ca.c301.t03_model.HTTPManager"
	 */
	private HTTPManager httpManager;

	/**
	 * @uml.property name="emailHandler"
	 * @uml.associationEnd 
	 *                     inverse="recipeManager:ca.c301.t03_model.EmailHandler"
	 */
	private EmailHandler emailHandler;

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
		return httpManager.getRecipe(id);
	}

	/**
			 */
	public void publishRecipeToWeb(Recipe recipe) {
		try {
			httpManager.addRecipe(recipe);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
				 */
	public void saveRecipe(Recipe recipe, Context c) {
		dataManager.getRecipeBook().addRecipe(recipe);
		dataManager.saveToFile(c);
	}

	/**
					 */
	public void emailRecipe(String emailAddress, Recipe recipe) {
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
	}

	/**
			 */
	public void addIngredientToPantry(Ingredient ingredient) {
	}

		
		/**
		 */
		public Ingredient getLocalIngredientById(int id){
			return null;
		}

}
