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

/**
 * Handles all input/output required for the application
 */
public class DataManager implements Serializable {
	
	private static final long serialVersionUID = 6085575872863551305L;
	private static final String TAG = "DataManager";
	public static String DEFAULT_FILE_NAME = "recipe_file";
	private String fileName;

	/**
	 * Constructor that takes a file name
	 * @param c
	 * 			Application context
	 * @param fileName
	 * 			Name of file for storage
	 */
	public DataManager(Context c, String fileName) {
		this.fileName = fileName;
		loadFromFile(c);
	}

	/**
	 * Constructor loads any existing DataManager from file.
	 * 
	 * @param c
	 *            The Android context for the DataManager
	 */
	public DataManager(Context c) {
		this.fileName = DEFAULT_FILE_NAME;
		loadFromFile(c);
	}

	/**
	 * Constructor creates DataManager with existing values.
	 * 
	 * @param book
	 *            Is the existing RecipeBook
	 * @param pantry
	 *            Is the existing VirtualPantry
	 * @param c
	 *            Is the Application context
	 * @param fileName 
	 */
	public DataManager(RecipeBook book, VirtualPantry pantry, Context c, String fileName) {
		this.recipeBook = book;
		this.virtualPantry = pantry;
		this.fileName = fileName;
		try {
			saveToFile(c);
		} catch (FullFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Loads recipes and virtual pantry from locally saved file
	 * @param c
	 *            Is the application context
	 */
	private void loadFromFile(Context c) {
		try {
			DataManager savedManager;
			FileInputStream fin = c.openFileInput(fileName);
			ObjectInputStream ois = new ObjectInputStream(fin);
			savedManager = (DataManager) ois.readObject();

			// Copy properties into this object. Remember to change this
			// if properties are added or removed (basically a copy
			// constructor).
			this.recipeBook = savedManager.getRecipeBook();
			this.virtualPantry = savedManager.getVirtualPantry();
		} catch (FileNotFoundException f) {
			// file not initialized is caught here.
			Log.i(TAG, "Making new file.");
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
	 * @uml.property name="virtualPantry"
	 * @uml.associationEnd aggregation="shared"
	 *                     inverse="dataManager:ca.c301.t03_model.VirtualPantry"
	 */
	private VirtualPantry virtualPantry;

	/**
	 * Getter of the property <tt>virtualPantry</tt>
	 * 
	 * @return Returns the virtualPantry.
	 * @uml.property name="virtualPantry"
	 */
	public VirtualPantry getVirtualPantry() {
		return virtualPantry;
	}

	/**
	 * Setter of the property <tt>virtualPantry</tt>
	 * 
	 * @param virtualPantry
	 *            The virtualPantry to set.
	 * @uml.property name="virtualPantry"
	 */
	public void setVirtualPantry(VirtualPantry virtualPantry) {
		this.virtualPantry = virtualPantry;
	}

	/**
	 * Should be called whenever a change is made to the data in DataManager
	 * 
	 * @param c
	 *            The Android context
	 * @throws FullFileException
	 */
	public void saveToFile(Context c) throws FullFileException {
		try {
			FileOutputStream fout = c.openFileOutput(fileName,
					Context.MODE_WORLD_READABLE);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(this);
			oos.close();
		} catch (IOException ioe) {
			if (exceptionIsFullFile(ioe))
				throw new FullFileException();
			ioe.printStackTrace();
		}
	}

	/**
	 * Handles full device exceptions
	 * @param ioe
	 * 				Exception code
	 * @return
	 * 				Boolean - true if no space is left
	 */
	protected boolean exceptionIsFullFile(IOException ioe) {
		return (ioe.getMessage().equals("No space left on device"));
	}

	/**
	 * @uml.property name="recipeBook"
	 * @uml.associationEnd aggregation="shared"
	 *                     inverse="dataManager:ca.c301.t03_model.RecipeBook"
	 */
	private RecipeBook recipeBook;

	/**
	 * Getter of the property <tt>recipeBook</tt>
	 * 
	 * @return Returns the recipeBook.
	 * @uml.property name="recipeBook"
	 */
	public RecipeBook getRecipeBook() {
		return recipeBook;
	}

	/**
	 * Setter of the property <tt>recipeBook</tt>
	 * 
	 * @param recipeBook
	 *            The recipeBook to set.
	 * @uml.property name="recipeBook"
	 */
	public void setRecipeBook(RecipeBook recipeBook) {
		this.recipeBook = recipeBook;
	}
}
