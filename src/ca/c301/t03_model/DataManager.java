package ca.c301.t03_model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Collection;


import android.content.Context;
import android.util.Log;
public class DataManager implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6085575872863551305L;
	private static final String TAG = "DataManager";
	public static String FILE_NAME = "recipe_file";
	
	/**
	 * Constructor loads any existing DataManager from file.
	 */
	public DataManager(Context c){
		
		try{
			DataManager savedManager;
			FileInputStream fin = c.openFileInput(FILE_NAME);
			ObjectInputStream ois= new ObjectInputStream(fin);
			savedManager = (DataManager) ois.readObject();
			
			//Copy properties into this object. Remember to change this
			// if properties are added or removed (basically a copy constructor).
			this.recipeBook = savedManager.getRecipeBook();
			this.virtualPantry = savedManager.getVirtualPantry();
			}
			catch(FileNotFoundException f)
			{
				//file not initialized is caught here.
				Log.i(TAG,"Making new file.");
				this.recipeBook = new RecipeBook();
				this.virtualPantry = new VirtualPantry();
				try {
					saveToFile(c);
				} catch (FullFileException e) {
					e.printStackTrace();
				}
			} catch (StreamCorruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
		
		
	}
	/**
	 * Constructor creates DataManager with existing values.
	 * @param book
	 * @param pantry
	 * @param c
	 */
	public DataManager(RecipeBook book, VirtualPantry pantry, Context c){
		this.recipeBook = book;
		this.virtualPantry = pantry;
		try {
			saveToFile(c);
		} catch (FullFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	 * @throws FullFileException 
	 */
	public void saveToFile(Context c) throws FullFileException {
		try {
			FileOutputStream fout = c.openFileOutput(FILE_NAME,Context.MODE_WORLD_READABLE);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(this);
			oos.close();
		} catch (IOException ioe) {
			if(exceptionIsFullFile(ioe))
				throw new FullFileException();
			ioe.printStackTrace();
		}
	}

	protected boolean exceptionIsFullFile(IOException ioe) {
		return (ioe.getMessage().equals("No space left on device"));
	}

	/**
	 * @uml.property  name="recipeBook"
	 * @uml.associationEnd  aggregation="shared" inverse="dataManager:ca.c301.t03_model.RecipeBook"
	 */
	private RecipeBook recipeBook;

	/**
	 * Getter of the property <tt>recipeBook</tt>
	 * @return  Returns the recipeBook.
	 * @uml.property  name="recipeBook"
	 */
	public RecipeBook getRecipeBook() {
		return recipeBook;
	}

	/**
	 * Setter of the property <tt>recipeBook</tt>
	 * @param recipeBook  The recipeBook to set.
	 * @uml.property  name="recipeBook"
	 */
	public void setRecipeBook(RecipeBook recipeBook) {
		this.recipeBook = recipeBook;
	}
}
