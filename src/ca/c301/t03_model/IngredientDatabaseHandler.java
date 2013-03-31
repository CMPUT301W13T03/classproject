package ca.c301.t03_model;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class IngredientDatabaseHandler extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	 
    private static final String DATABASE_NAME = "ingredientDatabase";
 
    private static final String TABLE_INGREDIENTS = "ingredients";
 
    private static final String KEY_NAME = "name";
    private static final String INGREDIENT = "ingredient";
 
    public IngredientDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RECIPES_TABLE = "CREATE TABLE " + TABLE_INGREDIENTS + "("
                + KEY_NAME + " TEXT PRIMARY KEY,"
                + INGREDIENT + " BLOB" + ")";
        db.execSQL(CREATE_RECIPES_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
        onCreate(db);
    }
    
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
     
    public ArrayList<Ingredient> getAllIngredients() {
    	ArrayList<Ingredient> ingredientList = new ArrayList<Ingredient>();
    	
        String selectQuery = "SELECT " + INGREDIENT + " FROM " + TABLE_INGREDIENTS;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        if (cursor.moveToFirst()) {
            do {
                Ingredient ingredient = (Ingredient) SerializeHandler.deserializeObject(cursor.getBlob(0));
                ingredientList.add(ingredient);
            } while (cursor.moveToNext());
        }
     
        return ingredientList;
	}
    
    public ArrayList<String> getAllIngredientNames() {
    	ArrayList<String> ingredientList = new ArrayList<String>();
    	
        String selectQuery = "SELECT " + KEY_NAME + " FROM " + TABLE_INGREDIENTS;
     
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
     
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(0);
                ingredientList.add(name);
            } while (cursor.moveToNext());
        }
     
        return ingredientList;
	}
    
    public int getIngredientCount(String name) {
        String countQuery = "SELECT  * FROM " + TABLE_INGREDIENTS + " WHERE " + KEY_NAME + " LIKE '" + name + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
        int count = cursor.getCount();
        
        cursor.close();
        
        return count;
    }

    public void updateIngredient(Ingredient ingredient) {
    	deleteIngredient(ingredient.getName());
    	addIngredient(ingredient);
	}
    
    public void deleteIngredient(String name) {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INGREDIENTS, KEY_NAME + " = ?",
                new String[] { name });
        db.close();
    }
}
