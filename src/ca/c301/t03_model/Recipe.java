package ca.c301.t03_model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author  Zach
 */
public class Recipe implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * @uml.property  name="name"
	 */
	private String name;
	/**
	 * @uml.property  name="instructions"
	 */
	private String instructions;

	/**
	 * Constructor with no given arguments, name and instructions will be empty strings
	 */
	public Recipe() {
		this.name = "";
		this.instructions = "";
		this.ingredients = new ArrayList<Ingredient>();
	}
	
	/**
	 * Constructor with name and instruction arguments
	 * @param name Is the name of the recipe
	 * @param instructions Is the instructions as a String
	 */
	public Recipe(String name, String instructions){
		this.id = id;
		this.name = name;
		this.instructions = instructions;
	}
	
	/**
	 * Getter of the property <tt>name</tt>
	 * @return Returns the name of this recipe
	 * @uml.property  name="name"
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Getter of the property <tt>instructions</tt>
	 * @return Returns the instructions of this recipe
	 * @uml.property  name="instructions"
	 */
	public String getInstructions() {
		return this.instructions;
	}
	
	/**
	 * Getter for the ingredient at index i
	 * @param i Is the index of the ingredient to be returned
	 * @return Returns the ingredient at the specified index
	 */
	public Ingredient getIngredient(int i) {
		return this.ingredients.get(i);
	}
	
	/**
	 * Setter for the recipe's name
	 * @param name Is the name to be saved for this recipe
	 * @uml.property  name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Setter for the instructions of this recipe
	 * @param instructions Is the instructions to be set
	 * @uml.property  name="instructions"
	 */
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	
	/**
	 * To add an ingredient to this recipe
	 * @param name Is the ingredient to be added
	 */
	public void addIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
	}
	
	/**
	 * Sets the ingredient at a given index i to a given argument
	 * @param i Is the index of the ingredient to be set
	 * @param ingredient Is the ingredient to be set
	 */
	public void setIngredient(int i, Ingredient ingredient) {
		this.ingredients.set(i, ingredient);
	}
	
	/**
	 * Deletes the ingredient at a given index
	 * @param i Is the index of the ingredient to be deleted
	 */
	public void deleteIngredient(int i) {
		this.ingredients.remove(i);
	}

	/**
	 * @uml.property  name="ingredients"
	 * @uml.associationEnd  multiplicity="(0 -1)" ordering="true" aggregation="shared" inverse="recipe:ca.c301.t03_model.Ingredient"
	 */
	private ArrayList<Ingredient> ingredients;

	/**
	 * Getter of the property <tt>ingredients</tt>
	 * @return  Returns the ingredients.
	 * @uml.property  name="ingredients"
	 */
	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}

	/**
	 * Setter of the property <tt>ingredients</tt>
	 * @param ingredients  The ingredients to set.
	 * @uml.property  name="ingredients"
	 */
	public void setIngredients(ArrayList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	/**
	 * @uml.property  name="recipePhoto"
	 * @uml.associationEnd  multiplicity="(0 -1)" aggregation="shared" inverse="recipe:ca.c301.t03_model.RecipePhoto"
	 */
	private Collection<RecipePhoto> recipePhoto;

	/**
	 * Getter of the property <tt>recipePhoto</tt>
	 * @return  Returns the recipePhoto.
	 * @uml.property  name="recipePhoto"
	 */
	public Collection<RecipePhoto> getRecipePhoto() {
		return recipePhoto;
	}

	/**
	 * Setter of the property <tt>recipePhoto</tt>
	 * @param recipePhoto  The recipePhoto to set.
	 * @uml.property  name="recipePhoto"
	 */
	public void setRecipePhoto(Collection<RecipePhoto> recipePhoto) {
		this.recipePhoto = recipePhoto;
	}

	/**
	 * @uml.property  name="id"
	 */
	private int id;

	/**
	 * Getter of the property <tt>id</tt>
	 * @return  Returns the id.
	 * @uml.property  name="id"
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter of the property <tt>id</tt>
	 * @param id  The id to set.
	 * @uml.property  name="id"
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * To add a photo to a recipe
	 * @param photo Is the photo to be added
	 */
	public void addPhoto(RecipePhoto photo) {
		// TODO Auto-generated method stub
		
	}

}
