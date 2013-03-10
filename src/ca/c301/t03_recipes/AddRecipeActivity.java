package ca.c301.t03_recipes;

import ca.c301.t03_model.Converter;
import ca.c301.t03_model.Ingredient;
import ca.c301.t03_model.Recipe;
import ca.c301.t03_recipes.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/*
 * TODO:
 * Add tests for proper input
 */

public class AddRecipeActivity extends Activity {
 
	/** 
	 * @uml.property name="recipeApplication"
	 * @uml.associationEnd inverse="addRecipeActivity:ca.c301.t03_recipes.RecipeApplication"
	 */
	private RecipeApplication recipeApplication;
	
	private Recipe recipe;
	private Converter converter;
	
	private EditText name;
	private EditText instructions;
	private ListView ingredientsList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_recipe);
		
		recipe = new Recipe();
		converter = new Converter();
		
		name = (EditText) findViewById(R.id.editText_name);
		instructions = (EditText) findViewById(R.id.editText_instructions);
		ingredientsList = (ListView) findViewById(R.id.listView_ingredients);
		
		Button saveButton = (Button) findViewById(R.id.button_save);
        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	recipe.setName(name.getText().toString());
            	recipe.setInstructions(instructions.getText().toString());
            	
            	((RecipeApplication) getApplication()).getRecipeManager().saveRecipe(recipe, getApplicationContext());
            	
            	finish();
            }
        });
        
        Button savePublishButton = (Button) findViewById(R.id.button_save_publish);
        savePublishButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	finish();
            }
        });
        
        Button addIngredientButton = (Button) findViewById(R.id.button_add_ingredient);
        addIngredientButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	Intent intent = new Intent(AddRecipeActivity.this, AddIngredientActivity.class);            	
                startActivityForResult(intent, 1);
            }
        });
        
        Button addPictureButton = (Button) findViewById(R.id.button_add_picture);
        addPictureButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(AddRecipeActivity.this, PhotoActivity.class);
                startActivity(intent);
            }
        });
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_recipe, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (recipe.getIngredients().isEmpty() != true) {
			String[] displayList = converter.convertList(recipe.getIngredients());
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, displayList);
			ingredientsList.setAdapter(adapter);
			
			ingredientsList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view, int index, long id) {
				
				}
			});
			
		}
        
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {

			if(resultCode == RESULT_OK){      
		        String ingName = data.getStringExtra("name");
		        Double ingAmount = data.getDoubleExtra("amount", 0.00);
		        String ingUnit = data.getStringExtra("unit");
		        
		        Ingredient ingredient = new Ingredient();
		        ingredient.setName(ingName);
		        ingredient.setAmount(ingAmount);
		        ingredient.setUnitOfMeasurement(ingUnit);
		        
		        recipe.addIngredient(ingredient);
		    }
		    if (resultCode == RESULT_CANCELED) {    
		         //Write your code on no result return 
		    }
		}
	}

}
