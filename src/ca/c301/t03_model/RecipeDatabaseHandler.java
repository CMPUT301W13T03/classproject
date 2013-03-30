package ca.c301.t03_model;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.*;

public class RecipeDatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
 
    private static final String DATABASE_NAME = "recipeDatabase";
 
    private static final String TABLE_RECIPES = "recipes";
 
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_RECIPE = "recipe";
 
    public RecipeDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECIPES_TABLE = "CREATE TABLE " + TABLE_RECIPES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_RECIPE + " TEXT" + ")";
        db.execSQL(CREATE_RECIPES_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        onCreate(db);
    }
    
    public void addContact(Recipe recipe) {}
     
    public Recipe getRecipe(int id) {
		return null;
	}
     
    public ArrayList<Recipe> getAllRecipes() {
		return null;
	}
     
    public int getRecipesCount() {
		return 0;
	}

    public int updateRecipe(int id) {
		return 0;
	}
    
    public void deleteRecipe(int id) {}
}
