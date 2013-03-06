package ca.c301.t03_model;

import java.util.Collection;

public class DataManager {

	/** 
	 * @uml.property name="recipes"
	 * @uml.associationEnd multiplicity="(0 -1)" aggregation="shared" inverse="dataManager:ca.c301.t03_model.Recipe"
	 */
	private Collection<Recipe> recipe;

	/**
	 * Getter of the property <tt>recipes</tt>
	 * @return  Returns the recipe.
	 * @uml.property  name="recipes"
	 */
	public Collection<Recipe> getRecipes() {
		return recipe;
	}

	/**
	 * Setter of the property <tt>recipes</tt>
	 * @param recipes  The recipe to set.
	 * @uml.property  name="recipes"
	 */
	public void setRecipes(Collection<Recipe> recipes) {
		recipe = recipes;
	}

	/** 
	 * @uml.property name="virtualPantry"
	 * @uml.associationEnd aggregation="shared" inverse="dataManager:ca.c301.t03_model.VirtualPantry"
	 */
	private VirtualPantry virtualPantry;

	/** 
	 * Getter of the property <tt>virtualPantry</tt>
	 * @return  Returns the virtualPantry.
	 * @uml.property  name="virtualPantry"
	 */
	public VirtualPantry getVirtualPantry() {
		return virtualPantry;
	}

	/** 
	 * Setter of the property <tt>virtualPantry</tt>
	 * @param virtualPantry  The virtualPantry to set.
	 * @uml.property  name="virtualPantry"
	 */
	public void setVirtualPantry(VirtualPantry virtualPantry) {
		this.virtualPantry = virtualPantry;
	}

}
