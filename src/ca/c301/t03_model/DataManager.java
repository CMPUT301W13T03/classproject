package ca.c301.t03_model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


import android.content.Context;
import android.util.Log;
public class DataManager implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6085575872863551305L;
	String FILE_NAME = "recipe_file";
	
	/**
	 * Constructor loads any existing DataManager from file.
	 */
	DataManager(Context c){
		
		try{
			DataManager savedManager;
			FileInputStream fin = c.openFileInput(FILE_NAME);
			ObjectInputStream ois= new ObjectInputStream(fin);
			savedManager = (DataManager) ois.readObject();
			
			//Copy properties into this object. Remember to change this
			// if properties are added or removed (basically a copy constructor).
			this.recipes = savedManager.getRecipes();
			this.virtualPantry = savedManager.getVirtualPantry();
			}
			catch(FileNotFoundException f)
			{
				//file not initialized is caught here.
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
		
		
	}

	/** 
	 * @uml.property name="recipes"
	 * @uml.associationEnd multiplicity="(0 -1)" aggregation="shared" inverse="dataManager:ca.c301.t03_model.Recipe"
	 */
	private Collection<Recipe> recipes;

	/**
	 * Getter of the property <tt>recipes</tt>
	 * @return  Returns the recipe.
	 * @uml.property  name="recipes"
	 */
	public Collection<Recipe> getRecipes() {
		return recipes;
	}

	/**
	 * Setter of the property <tt>recipes</tt>
	 * @param recipes  The recipe to set.
	 * @uml.property  name="recipes"
	 */
	public void setRecipes(Collection<Recipe> recipes) {
		this.recipes = recipes;
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
	/**
	 * Should be called whenever a change is made to the data in DataManager
	 * @param c
	 */
	public void saveToFile(Context c) {
		try {
			FileOutputStream fout = c.openFileOutput(FILE_NAME,Context.MODE_WORLD_READABLE);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(this);
			oos.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
