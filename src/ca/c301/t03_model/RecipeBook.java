package ca.c301.t03_model;

import java.io.Serializable;
import java.util.ArrayList;


public class RecipeBook implements Serializable{

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
	public void deleteRecipeByID(int id)
	{
		for(int i = 0; i < recipes.size(); i++)
		{
			Recipe curRecipe = recipes.get(i);
			if(curRecipe.getId() == id)
				recipes.remove(i);
		}
	}
	public void addRecipe(Recipe recipe)
	{
		recipes.add(recipe);
		//Give the recipe an id
		recipe.setId(curID);
		curID++;
	}

}
