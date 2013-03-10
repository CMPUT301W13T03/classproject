package ca.c301.t03_model;

import java.io.Serializable;
import java.util.ArrayList;


public class RecipeBook implements Serializable{

	/**
	 * Constructor with no arguments
	 */
	public RecipeBook()
	{
		recipes = new ArrayList<Recipe>();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 7101319372516909911L;
	/** 
	 * @uml.property name="recipes"
	 * @uml.associationEnd multiplicity="(0 -1)" ordering="true" aggregation="shared" inverse="recipeBook:ca.c301.t03_model.Recipe"
	 */
	private ArrayList<Recipe> recipes;
	//RecipeBook assigns id's.
	int curID;
	
	/**
	 * To return a recipe which has a given id
	 * @param id Is the id of the recipe to be returned
	 * @return Returns the recipe with the given id
	 */
	public Recipe findRecipeByID(int id)
	{
		for(int i = 0; i < recipes.size(); i++)
		{
			Recipe curRecipe = recipes.get(i);
			if(curRecipe.getId() == id)
				return curRecipe;
		}
		return null;
	}
	
	/**
	 * To delete a recipe which has a given id
	 * @param id Is the id of the recipe to be deleted
	 */
	public void deleteRecipeByID(int id)
	{
		for(int i = 0; i < recipes.size(); i++)
		{
			Recipe curRecipe = recipes.get(i);
			if(curRecipe.getId() == id)
				recipes.remove(i);
		}
	}
	
	/**
	 * To add a given recipe to this recipeBook
	 * @param recipe Is the recipe to be added
	 */
	public void addRecipe(Recipe recipe)
	{
		recipes.add(recipe);
		//Give the recipe an id
		recipe.setId(curID);
		curID++;
	}

	public ArrayList<Recipe> getRecipes() {
		return recipes;
	}
}
