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

import ca.c301.t03_exceptions.FullFileException;

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
	
	private RecipeDatabaseHandler recipeDatabase;
	private IngredientDatabaseHandler ingredientDatabase;

	/**
	 * Constructor loads any existing DataManager from file.
	 * 
	 * @param c
	 * 				The Android context for the DataManager
	 */
	public DataManager(Context c) {
		recipeDatabase = new RecipeDatabaseHandler(c);
		ingredientDatabase = new IngredientDatabaseHandler(c);
	}
	
	/**
	 * Getter of the RecipeDatabaseHandler
	 * 
	 * @return
	 * 				Returns the recipe database handler
	 */
	public RecipeDatabaseHandler getRecipeDatabase() {
		return recipeDatabase;
	}
	
	/**
	 * Getter of the IngredientDatabaseHandler
	 * 
	 * @return
	 * 				Returns the ingredient database handler
	 */
	public IngredientDatabaseHandler getIngredientDatabase() {
		return ingredientDatabase;
	}

	/**
	 * Handles full device exceptions
	 * 
	 * @param ioe
	 * 				Exception code
	 * @return
	 * 				Boolean - true if no space is left
	 */
	protected boolean exceptionIsFullFile(IOException ioe) {
		return (ioe.getMessage().equals("No space left on device"));
	}
}
