package ca.c301.t03_recipes;

import ca.c301.t03_model.Camera;
import ca.c301.t03_model.DisplayConverter;
import ca.c301.t03_model.FullFileException;
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
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Activity used to edit or delete locally saved recipes
 */
public class EditRecipeActivity extends Activity {

	private Recipe recipe;
	private DisplayConverter converter;

	private EditText name;
	private EditText instructions;
	private ListView ingredientsList;
	private int id;

	/**
	 * Is responsible for creating the view of the activity,
	 * Buttons are set here
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_recipe);

		converter = new DisplayConverter();	
		recipe = new Recipe();

		Bundle data = getIntent().getExtras();
		id = data.getInt("id");

		ingredientsList = (ListView) findViewById(R.id.listView_ingredients);

		recipe.setName(((RecipeApplication) getApplication()).getRecipeManager().getLocallySavedRecipeById(id).getName());
		recipe.copyIngredients(((RecipeApplication) getApplication()).getRecipeManager().getLocallySavedRecipeById(id).getIngredients());
		recipe.setInstructions(((RecipeApplication) getApplication()).getRecipeManager().getLocallySavedRecipeById(id).getInstructions());

		name = (EditText) findViewById(R.id.editText_name);
		instructions = (EditText) findViewById(R.id.editText_instructions);

		name.setText(recipe.getName());
		instructions.setText(recipe.getInstructions());

		Button saveButton = (Button) findViewById(R.id.button_save);
        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	
            	if (!name.getText().toString().equals("") && !instructions.getText().toString().equals("") &&
            			!recipe.getIngredients().isEmpty()) {
            		Intent returnIntent = new Intent();
                	returnIntent.putExtra("del",0);
                	setResult(RESULT_OK,returnIntent);
                	
                	recipe.setName(name.getText().toString());
                	recipe.setInstructions(instructions.getText().toString());
                	
                	try {
    					((RecipeApplication) getApplication()).getRecipeManager().setRecipe(id, recipe, getApplicationContext());
    				} catch (FullFileException e) {
    					Toast.makeText(getApplicationContext(), "No room to save recipe", Toast.LENGTH_LONG).show();
    					e.printStackTrace();
    				}
                	
                	finish();
            	}
            	else
            		Toast.makeText(getApplicationContext(), 
            			"Please fill in all fields and add at least one ingredient", Toast.LENGTH_LONG).show();
            }
        });
        
        Button deleteButton = (Button) findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	Intent returnIntent = new Intent();
            	returnIntent.putExtra("del",1);
            	setResult(RESULT_OK,returnIntent);
            	
            	try {
					((RecipeApplication) getApplication()).getRecipeManager().deleteLocallySavedRecipeById(id, getApplicationContext());
				} catch (FullFileException e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "No room to save recipe", Toast.LENGTH_LONG).show();
				}
            	
            	finish();
            }
        });
        
        Button addPictureButton = (Button) findViewById(R.id.button_add_picture);
        addPictureButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
/*                Intent intent = new Intent(EditRecipeActivity.this, PhotoActivity.class);
                startActivity(intent);*/
            	takePhoto();
            }
        });
        
        Button addIngredientButton = (Button) findViewById(R.id.button_add_ingredient);
        addIngredientButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	Intent intent = new Intent(EditRecipeActivity.this, AddIngredientActivity.class);            	
                startActivityForResult(intent, 1);
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_edit_recipe, menu);
		return true;
	}

	/**
	 * Is called when this activity resumes
	 * Updates an ArrayAdapter to show list of ingredients
	 * If any ingredient is clicked, starts the EditIngredientActivity,
	 * and intends to get a return result from that activity
	 */
	@Override
	protected void onResume() {
		super.onResume();

		String[] displayList = converter.convertIngredientsList(recipe.getIngredients());

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, displayList);
		ingredientsList.setAdapter(adapter);

		ingredientsList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int index, long id) {
				Intent intent = new Intent(EditRecipeActivity.this, EditIngredientActivity.class);

				Bundle data = new Bundle();
				data.putInt("index", index);
				data.putString("name", recipe.getIngredient(index).getName());
				data.putDouble("amount", recipe.getIngredient(index).getAmount());
				data.putString("unit", recipe.getIngredient(index).getUnitOfMeasurement());

				intent.putExtras(data);

	            startActivityForResult(intent, 1);
			}
		}); 
	}

	/**
	 * Called when returning from another activity
	 * Depending on what the result was from that activity,
	 * It can delete an ingredient, modify an ingredient, or do nothing
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {

			if(resultCode == RESULT_OK){

				if (data.getIntExtra("del", 0) == 1) {
					recipe.deleteIngredient(data.getIntExtra("index", 0));
				}
				else {			        
			        Ingredient ingredient = new Ingredient();
			        ingredient.setName(data.getStringExtra("name"));
			        ingredient.setAmount(data.getDoubleExtra("amount", 0.00));
			        ingredient.setUnitOfMeasurement(data.getStringExtra("unit"));

			        if (data.getIntExtra("type", 0) == 0) {
			        	recipe.addIngredient(ingredient);
			        }
			        else {
			        	recipe.setIngredient(data.getIntExtra("index", 0), ingredient);
			        }
				}
		    }
		}
		//Case for photo result:
		if(requestCode == Camera.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
		{
			if(resultCode == RESULT_OK){
				//handle result

			}
		}
	}
	protected void takePhoto() {
		((RecipeApplication)getApplication()).getRecipeManager().takePhotoForRecipe(this);		
	}

}