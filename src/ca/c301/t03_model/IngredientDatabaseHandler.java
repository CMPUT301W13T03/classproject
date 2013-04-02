package ca.c301.t03_model;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Handles the Ingredients Database, which is also called Virtual Pantry
 */
public class IngredientDatabaseHandler extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	 
    private static final String DATABASE_NAME = "ingredientDatabase";
 
    private static final String TABLE_INGREDIENTS = "ingredients";
 
    private static final String KEY_NAME = "name";
    private static final String INGREDIENT = "ingredient";
 
    /**
     * Constructor for IngredientDatabaseHandler
     * 
     * @param context
     * 				The Android context for the IngredientDatabaseHandler
     */
    public IngredientDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    /**
     * To create the database table for ingredients, which will be the Virtual Pantry
     * 
     * @param db
     * 				Is the database where the table will be created
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECIPES_TABLE = "CREATE TABLE " + TABLE_INGREDIENTS + "("
                + KEY_NAME + " TEXT PRIMARY KEY,"
                + INGREDIENT + " BLOB" + ")";
        db.execSQL(CREATE_RECIPES_TABLE);
    }
 
    /**
     * To upgrade, must drop the old ingredients table and then create a new one
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
        onCreate(db);
    }
    
    /**
     * To add an ingredient to the Virtual Pantry
     * 
     * @param ingredient
     * 				Is the ingredient to add
     */
    public void addIngredient(Ingredient ingredient) {
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	String sql                      =   "INSERT INTO " + TABLE_INGREDIENTS + " (" + KEY_NAME + "," + INGREDIENT + ") VALUES(?,?)";
        SQLiteStatement insertStmt      =   db.compileStatement(sql);
        insertStmt.clearBindings();
        insertStmt.bindString(1, ingredient.getName());
        insertStmt.bindBlob(2, SerializeHandler.serializeObject(ingredient));
        insertStmt.executeInsert();
        db.close();
    }
    
    public Ingredient getIngredient(String name) {
    	
    	Ingredient ingredient = new Ingredient();
    	
        String selectQuery = "SELECT " + INGREDIENT + " FROM " + TABLE_INGREDIENTS + " WHERE " + KEY_NAME + " LIKE '" + name + "'";
     
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        if (cursor.moveToFirst()) {
        	ingredient = (Ingredient) SerializeHandler.deserializeObject(cursor.getBlob(0));
        }
        else {
        	return null;
        }
     
        return ingredient;
	}
    
    /**
     * To get all ingredients in the Virtual Pantry
     * 
     * @return
     * 				An ArrayList of all ingredients
     */
    public ArrayList<Ingredient> getAllIngredients() {
    	ArrayList<Ingredient> ingredientList = new ArrayList<Ingredient>();
    	
        String selectQuery = "SELECT " + INGREDIENT + " FROM " + TABLE_INGREDIENTS;
     
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        if (cursor.moveToFirst()) {
            do {
                Ingredient ingredient = (Ingredient) SerializeHandler.deserializeObject(cursor.getBlob(0));
                ingredientList.add(ingredient);
            } while (cursor.moveToNext());
        }
     
        return ingredientList;
	}
    
    /**
     * To get the names of all ingredients in the Virtual Pantry
     * 
     * @return
     * 				An ArrayList of Strings which are all the ingredient names
     */
    public ArrayList<String> getAllIngredientNames() {
    	ArrayList<String> ingredientList = new ArrayList<String>();
    	
        String selectQuery = "SELECT " + KEY_NAME + " FROM " + TABLE_INGREDIENTS;
     
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                ingredientList.add(name);
            } while (cursor.moveToNext());
        }
     
        return ingredientList;
	}
    
    /**
     * To get the number of ingredients matching a given name in the Virtual Pantry
     * 
     * @param name
     * 				The name of the ingredients to count
     * @return
     * 				The number of ingredients matching the given name
     */
    public int getIngredientCount(String name) {
        String countQuery = "SELECT  * FROM " + TABLE_INGREDIENTS + " WHERE " + KEY_NAME + " LIKE '" + name + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
        int count = cursor.getCount();
        
        cursor.close();
        
        return count;
    }

    /**
     * To update an ingredient in the Virtual Pantry
     * 
     * @param ingredient
     * 				The ingredient to update
     */
    public void updateIngredient(Ingredient ingredient, String name) {
    	deleteIngredient(name);
    	addIngredient(ingredient);
	}
    
    /**
     * To delete an ingredient from the database
     * 
     * @param name
     * 				The name of the ingredient to delete
     */
    public void deleteIngredient(String name) {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INGREDIENTS, KEY_NAME + " = ?",
                new String[] { name });
        db.close();
    }
}
