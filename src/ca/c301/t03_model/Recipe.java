package ca.c301.t03_model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import android.util.Log;

/**
 * Recipe object used to store name, instructions, and ingredients
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
	 * @uml.property name="hasPhoto"
	 */
	private boolean hasPhoto;
	
	private int photoCount;

	/**
	 * Constructor with no given arguments, name and instructions will be empty strings
	 */
	public Recipe() {
		this.name = "";
		this.instructions = "";
		this.ingredients = new ArrayList<Ingredient>();
		this.recipePhoto = new ArrayList<RecipePhoto>();
		this.id = generateID();
	}
	
	/**
	 * Constructor with name and instruction arguments
	 * 
	 * @param name
	 * 				Is the name of the recipe
	 * @param instructions
	 * 				Is the instructions as a String
	 */
	public Recipe(String name, String instructions){
		this.id = generateID();
		this.name = name;
		this.instructions = instructions;
		this.ingredients = new ArrayList<Ingredient>();
		this.recipePhoto = new ArrayList<RecipePhoto>();
	}
	
	/**
	 * To generate an ID for a recipe based on the current time
	 * 
	 * @return
	 * 				The ID based on current time
	 */
	private int generateID() {
		String tempID = generateIDString();
		return Integer.parseInt(tempID);
	}
	/**
	 * Generates an ID for a recipe as a String.
	 * @return ID as a string
	 */
	private String generateIDString(){
		String tempID = "";
		Calendar cal = Calendar.getInstance();
		tempID = tempID.concat(Integer.toString(cal.get(Calendar.DAY_OF_YEAR)));
		tempID = tempID.concat(Integer.toString(cal.get(Calendar.MINUTE)));
		tempID = tempID.concat(Integer.toString(cal.get(Calendar.SECOND)));
		tempID = tempID.concat(Integer.toString(cal.get(Calendar.MILLISECOND)));
		return tempID;
	}
	/**
	 * To attach the photo from a given file to a given recipe
	 * 

	 * @param f
	 * 				The file which contains the photo to attach
	 */
	public void attachPhotoFile(File f){
		RecipePhoto photo = new RecipePhoto(f);
		this.addPhoto(photo);
	}

	/**
	 * Getter of the property <tt>name</tt>
	 * 
	 * @return
	 * 				The name of this recipe
	 * @uml.property  name="name"
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Getter of the property <tt>instructions</tt>
	 * 
	 * @return
	 * 				The instructions of this recipe
	 * @uml.property  name="instructions"
	 */
	public String getInstructions() {
		return this.instructions;
	}
	
	/**
	 * Getter for the ingredient at index i
	 * 
	 * @param i
	 * 				Is the index of the ingredient to be returned
	 * @return
	 * 				The ingredient at the specified index
	 */
	public Ingredient getIngredient(int i) {
		return this.ingredients.get(i);
	}
	
	/**
	 * Getter for the number of photos saved to this recipe
	 * 
	 * @return
	 * 				The number of photos of this recipe
	 */
	public int getPhotoCount(){
		return this.photoCount;
	}
	
	/**
	 * Setter for the recipe's name
	 * 
	 * @param name
	 * 				Is the name to be saved for this recipe
	 * @uml.property  name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Setter for the instructions of this recipe
	 * 
	 * @param instructions
	 * 				Is the instructions to be set
	 * @uml.property  name="instructions"
	 */
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	
	/**
	 * To add an ingredient to this recipe
	 * 
	 * @param name
	 * 				Is the ingredient to be added
	 */
	public void addIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
	}
	
	/**
	 * Sets the ingredient at a given index i to a given argument
	 * 
	 * @param i
	 * 				Is the index of the ingredient to be set
	 * @param ingredient
	 * 				Is the ingredient to be set
	 */
	public void setIngredient(int i, Ingredient ingredient) {
		this.ingredients.set(i, ingredient);
	}
	
	/**
	 * Deletes the ingredient at a given index
	 * 
	 * @param i
	 * 				Is the index of the ingredient to be deleted
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
	 * 
	 * @return
	 * 				The Array List of ingredients
	 * @uml.property  name="ingredients"
	 */
	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}

	/**
	 * Setter of the property <tt>ingredients</tt>
	 * 
	 * @param ingredients
	 * 				The ingredients to set.
	 * @uml.property  name="ingredients"
	 */
	public void setIngredients(ArrayList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	/**
	 * Copies ingredients list to current recipe
	 * 
	 * @param ingredients
	 * 				The ingredients list to be copied
	 */
	public void copyIngredients(ArrayList<Ingredient> ingredients) {
		this.ingredients.addAll(ingredients);
	}

	/**
	 * @uml.property  name="recipePhoto"
	 * @uml.associationEnd  multiplicity="(0 -1)" aggregation="shared" inverse="recipe:ca.c301.t03_model.RecipePhoto"
	 */
	private ArrayList<RecipePhoto> recipePhoto;

	/**
	 * Getter of the property <tt>recipePhoto</tt>
	 * 
	 * @return
	 * 				The recipePhoto
	 * @uml.property  name="recipePhoto"
	 */
	public Collection<RecipePhoto> getRecipePhoto() {
		return recipePhoto;
	}


	/**
	 * @uml.property  name="id"
	 */
	private int id;

	/**
	 * Getter of the property <tt>id</tt>
	 * 
	 * @return
	 * 				The id
	 * @uml.property  name="id"
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter of the property <tt>id</tt>
	 * 
	 * @param id
	 * 				The id to set
	 * @uml.property  name="id"
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * To check whether this recipe has a photo
	 * 
	 * @return
	 * 				A Boolean, true if this recipe has a photo
	 */
	public boolean hasPhoto(){
		return hasPhoto;
	}
	
	/**
	 * To add a photo to a recipe
	 * 
	 * @param photo
	 * 				Is the photo to be added
	 */
	public void addPhoto(RecipePhoto photo) {
		if(hasPhoto == false) hasPhoto = true;
		this.photoCount = this.photoCount + 1;
		recipePhoto.add(photo);
		
	}

}
