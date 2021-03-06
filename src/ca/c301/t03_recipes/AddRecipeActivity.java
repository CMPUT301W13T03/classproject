package ca.c301.t03_recipes;

import java.io.File;
import java.io.IOException;

import ca.c301.t03_exceptions.FullFileException;
import ca.c301.t03_model.Camera;
import ca.c301.t03_model.DisplayConverter;
import ca.c301.t03_model.Ingredient;
import ca.c301.t03_model.Recipe;
import ca.c301.t03_recipes.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/**
 * Activity used to add new recipes
 */
public class AddRecipeActivity extends Activity {

	/** 
	 * @uml.property name="recipeApplication"
	 * @uml.associationEnd inverse="addRecipeActivity:ca.c301.t03_recipes.RecipeApplication"
	 */
	private RecipeApplication recipeApplication;

	private Recipe recipe;
	private DisplayConverter converter;

	private EditText name;
	private EditText instructions;
	private ListView ingredientsList;

	private File photoFile;


	/**
	 * Is responsible for creating the view of the activity,
	 * Buttons are set here
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_recipe);

		recipe = new Recipe();
		converter = new DisplayConverter();

		name = (EditText) findViewById(R.id.editText_name);
		instructions = (EditText) findViewById(R.id.editText_instructions);
		ingredientsList = (ListView) findViewById(R.id.listView_ingredients);
		
		initializeButtons();
	}

	/**
	 * Initializes clickable buttons with their listeners.
	 */
	private void initializeButtons() {
		initSaveButton();
		initSavePublishButton();
		initAddIngredientButton();
		initAddPictureButton();
	}

	/**
	 * Initializes button for adding a picture to the recipe.
	 */
	private void initAddPictureButton() {
		Button addPictureButton = (Button) findViewById(R.id.button_add_picture);
		addPictureButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				takePhoto();
			}
		});
		
	}

	/**
	 * Intializes a button for adding an ingredient to a recipe.
	 */
	private void initAddIngredientButton() {
		Button addIngredientButton = (Button) findViewById(R.id.button_add_ingredient);
		addIngredientButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(AddRecipeActivity.this, AddIngredientActivity.class);            	
				startActivityForResult(intent, 1);
			}
		});
		
	}
	/**
	 * Initializes button for saving and publishing a recipe.
	 */
	private void initSavePublishButton() {
		Button savePublishButton = (Button) findViewById(R.id.button_save_publish);
		savePublishButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				saveRecipe();

				if (!name.getText().toString().equals("") && !instructions.getText().toString().equals("") &&
						!recipe.getIngredients().isEmpty()) {

					recipe.setName(name.getText().toString());
					recipe.setInstructions(instructions.getText().toString());

					try {
						((RecipeApplication) getApplication()).getRecipeManager().saveRecipe(recipe);
					} catch (FullFileException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					// ADD CODE TO POST RECIPE TO WEB HERE
					try {
						((RecipeApplication) getApplication()).getRecipeManager().publishRecipeToWeb(recipe);
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						Context context = getApplicationContext();
						CharSequence text = "Unable to Access Internet";
						int duration = Toast.LENGTH_LONG;

						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
					}
					finish();
				}
				else
					Toast.makeText(getApplicationContext(), 
						"Please fill in all fields and add at least one ingredient", Toast.LENGTH_LONG).show();
			}

		});
		
	}

	/**
	 * Initializes button for saving recipes
	 */
	private void initSaveButton() {
		Button saveButton = (Button) findViewById(R.id.button_save);
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				if (!name.getText().toString().equals("") && !instructions.getText().toString().equals("") &&
						!recipe.getIngredients().isEmpty()) {

					recipe.setName(name.getText().toString());
					recipe.setInstructions(instructions.getText().toString());

					try {
						((RecipeApplication) getApplication()).getRecipeManager().saveRecipe(recipe);
					} catch (FullFileException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					finish();
				}
				else {
					Toast.makeText(getApplicationContext(), "Please fill in all fields and add at least one ingredient", Toast.LENGTH_LONG).show();
				}
			}
		});
		
	}
	/**
	 * Saves a recipe to db.
	 */
	private void saveRecipe() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_recipe, menu);
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
				Intent intent = new Intent(AddRecipeActivity.this, EditIngredientActivity.class);

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
	 * It may also add a photo.
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
		// Case for photo result:
		if (requestCode == Camera.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// handle result
				recipe.attachPhotoFile(photoFile);
			}
		}
		
	}
	protected void takePhoto() {
		//Store file and add it to the recipe if the Photo activity returns RESULT_OK
		photoFile = ((RecipeApplication)getApplication()).getRecipeManager().takePhotoForRecipe(this);		
	}

}
