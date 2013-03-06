package ca.c301.t03_model;

import java.util.Collection;

public class RecipeManager {

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
	private EmailHandler photoManager;

	/**
	 * @uml.property name="dataManager"
	 * @uml.associationEnd aggregation="shared"
	 *                     inverse="recipeManager:ca.c301.t03_model.DataManager"
	 */
	private DataManager dataManager;

	/**
		 */
	public Collection<Recipe> getWebRecipes() {
		return null;
	}

	/**
			 */
	public void publishRecipeToWeb(Recipe recipe) {
	}

	/**
				 */
	public void saveRecipe(Recipe recipe) {
	}

	/**
					 */
	public void emailRecipe(String emailAddress, Recipe recipe) {
	}

	/**
						 */
	public void getLocallySavedRecipeById(int id) {
	}

}
