package ca.c301.t03_model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

	
	public Recipe() {
		this.name = "";
		this.instructions = "";
		this.ingredients = new ArrayList<Ingredient>();
	}
	public Recipe(String name, String instructions){
		this.id = id;
		this.name = name;
		this.instructions = instructions;
	}
	
	/**
	 * @return
	 * @uml.property  name="name"
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return
	 * @uml.property  name="instructions"
	 */
	public String getInstructions() {
		return this.instructions;
	}
	

	public Ingredient getIngredient(int i) {
		return this.ingredients.get(i);
	}
	
	/**
	 * @param name
	 * @uml.property  name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @param instruction
	 * @uml.property  name="instructions"
	 */
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	
	public void addIngredient(Ingredient ingredient) {
		this.ingredients.add(ingredient);
	}
	

	
	public void setIngredient(int i, Ingredient ingredient) {
		this.ingredients.set(i, ingredient);
	}
	

	public void deleteIngredient(int i) {
		this.ingredients.remove(i);
	}

	/**
	 * @uml.property  name="ingredients"
	 * @uml.associationEnd  multiplicity="(0 -1)" ordering="true" aggregation="shared" inverse="recipe:ca.c301.t03_model.Ingredient"
	 */
	private List<Ingredient> ingredients;

	/**
	 * Getter of the property <tt>ingredients</tt>
	 * @return  Returns the ingredients.
	 * @uml.property  name="ingredients"
	 */
	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	/**
	 * Setter of the property <tt>ingredients</tt>
	 * @param ingredients  The ingredients to set.
	 * @uml.property  name="ingredients"
	 */
	public void setIngredients(List<Ingredient> ingredients) {
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

}
