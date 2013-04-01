package ca.c301.t03_model;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

/**
 * Handles the Recipes Database, which are the local recipes
 */
public class RecipeDatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
 
    private static final String DATABASE_NAME = "recipeDatabase";
 
    private static final String TABLE_RECIPES = "recipes";
 
    private static final String KEY_ID = "id";
    private static final String NAME = "name";
    private static final String RECIPE = "recipe";
 
    /**
     * Constructor given a context
     * @param context
     * 				The Android context for the RecipeDatabaseHandler
     */
    public RecipeDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    /**
     * To create the database table for recipes
     * 
     * @param db
     * 				Is the database where the table will be created
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECIPES_TABLE = "CREATE TABLE " + TABLE_RECIPES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + NAME + " TEXT,"
                + RECIPE + " BLOB" + ")";
        db.execSQL(CREATE_RECIPES_TABLE);
    }
 
    /**
     * To upgrade, must drop the old recipes table and then create a new one
     * 
     * @param db
     * 				Is the database to drop and create the table
     * @param oldVersion
     * 				The version number of the old database
     * @param newVersion
     * 				The version number of the new database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        onCreate(db);
    }
    
    /**
     * To add a recipe to the recipes database
     * 
     * @param recipe
     * 				Is the recipe to add
     */
    public void addRecipe(Recipe recipe) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	 
    	String sql                      =   "INSERT INTO " + TABLE_RECIPES + " (" + KEY_ID + "," + NAME + "," + RECIPE + ") VALUES(?,?,?)";
        SQLiteStatement insertStmt      =   db.compileStatement(sql);
        insertStmt.clearBindings();
        insertStmt.bindString(1, Integer.toString(recipe.getId()));
        insertStmt.bindString(2, recipe.getName());
        insertStmt.bindBlob(3, SerializeHandler.serializeObject(recipe));
        insertStmt.executeInsert();
        db.close();
    }
     
    /**
     * To get the recipe which has a given ID
     * 
     * @param id
     * 				The ID of the recipe to return
     * @return
     * 				The recipe which has the given ID
     */
    public Recipe getRecipe(int id) {
    	
    	Recipe recipe = new Recipe();
    	
        String selectQuery = "SELECT RECIPE FROM " + TABLE_RECIPES + " WHERE " + KEY_ID + " = " + Integer.toString(id);
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        if (cursor.moveToFirst()) {
        	recipe = (Recipe) SerializeHandler.deserializeObject(cursor.getBlob(0));
        }
     
        return recipe;
	}
    
    /**
     * To search for recipes which have a name that partially matches a given keyword
     * 
     * @param keyword
     * 				The keyword to partially match with a recipe name
     * @return
     * 				An ArrayList of recipes which have names partially matching the given keyword
     */
    public ArrayList<Recipe> searchRecipes(String keyword) {
	 	ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
    	
        String selectQuery = "SELECT " + RECIPE + " FROM " + TABLE_RECIPES + " WHERE " + NAME + " LIKE '%" + keyword + "%'";
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = (Recipe) SerializeHandler.deserializeObject(cursor.getBlob(0));
                recipeList.add(recipe);
            } while (cursor.moveToNext());
        }
     
        return recipeList;
	}
     
    /**
     * To get all recipes in the recipes database
     * 
     * @return
     * 				An ArrayList of all recipes in the database
     */
    public ArrayList<Recipe> getAllRecipes() {
    	ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
    	
        String selectQuery = "SELECT " + RECIPE + " FROM " + TABLE_RECIPES;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = (Recipe) SerializeHandler.deserializeObject(cursor.getBlob(0));
                recipeList.add(recipe);
            } while (cursor.moveToNext());
        }
     
        return recipeList;
	}

    /**
     * To update a recipe in the database, it first deletes it, then readds it
     * 
     * @param recipe
     * 					Is the recipe to update
     */
    public void updateRecipe(Recipe recipe) {
    	deleteRecipe(recipe.getId());
    	addRecipe(recipe);
	}
    
    /**
     * To delete a recipe from the database, by a given ID
     * 
     * @param id
     * 				Is the ID of the recipe to delete
     */
    public void deleteRecipe(int id) {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECIPES, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }
    
}
