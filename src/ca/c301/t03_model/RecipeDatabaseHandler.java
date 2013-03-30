package ca.c301.t03_model;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;

public class RecipeDatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
 
    private static final String DATABASE_NAME = "recipeDatabase";
 
    private static final String TABLE_RECIPES = "recipes";
 
    private static final String KEY_ID = "id";
    private static final String NAME = "name";
    private static final String RECIPE = "recipe";
 
    public RecipeDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECIPES_TABLE = "CREATE TABLE " + TABLE_RECIPES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + NAME + " TEXT,"
                + RECIPE + " BLOB" + ")";
        db.execSQL(CREATE_RECIPES_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        onCreate(db);
    }
    
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

    public void updateRecipe(Recipe recipe) {
    	deleteRecipe(recipe.getId());
    	addRecipe(recipe);
	}
    
    public void deleteRecipe(int id) {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RECIPES, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }
    
}
