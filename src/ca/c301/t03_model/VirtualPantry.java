package ca.c301.t03_model;

import java.util.Collection;

public class VirtualPantry {

	/** 
	 * @uml.property name="ingredients"
	 * @uml.associationEnd multiplicity="(0 -1)" aggregation="shared" inverse="virtualPantry:ca.c301.t03_model.Ingredient"
	 */
	private Collection<Ingredient> ingredient;

	/**
	 * Getter of the property <tt>ingredients</tt>
	 * @return  Returns the ingredient.
	 * @uml.property  name="ingredients"
	 */
	public Collection<Ingredient> getIngredients() {
		return ingredient;
	}

	/**
	 * Setter of the property <tt>ingredients</tt>
	 * @param ingredients  The ingredient to set.
	 * @uml.property  name="ingredients"
	 */
	public void setIngredients(Collection<Ingredient> ingredients) {
		ingredient = ingredients;
	}

}
