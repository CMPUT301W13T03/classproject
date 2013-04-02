package ca.c301.t03_recipes;

import java.util.ArrayList;

import ca.c301.t03_exceptions.FullFileException;
import ca.c301.t03_model.Camera;
import ca.c301.t03_model.Ingredient;
import ca.c301.t03_model.DisplayConverter;
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
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Activity used to view list of ingredients in virtual pantry
 */
public class IngredientListActivity extends Activity {

	/**
	 * @uml.property  name="recipeApplication"
	 * @uml.associationEnd  inverse="ingredientListActivity:ca.c301.t03_recipes.RecipeApplication"
	 */
	private RecipeApplication recipeApplication;
	
	private DisplayConverter converter;
	
	private ListView ingredientsList;
	private ArrayList<Ingredient> ingredients;
	
	/**
	 * Is responsible for creating the view of the activity,
	 * A buttons is set here
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ingredient_list);
		
		converter = new DisplayConverter();
		ingredients = new ArrayList<Ingredient>();
		
		ingredientsList = (ListView) findViewById(R.id.listView_ingredients);
		
		Button addIngredientButton = (Button) findViewById(R.id.button_add_ingredient);
        addIngredientButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	Intent intent = new Intent(IngredientListActivity.this, AddIngredientActivity.class);            	
                startActivityForResult(intent, 1);
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_ingredient_list, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		ingredients = ((RecipeApplication) getApplication()).getRecipeManager().getAllIngredients();

		String[] displayList = converter.convertIngredientsList(ingredients);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, displayList);
		ingredientsList.setAdapter(adapter);

		ingredientsList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int index, long id) {
				Intent intent = new Intent(IngredientListActivity.this, EditIngredientActivity.class);

				Bundle data = new Bundle();
				data.putInt("index", 0);
				data.putString("name", ingredients.get(index).getName());
				data.putDouble("amount", ingredients.get(index).getAmount());
				data.putString("unit", ingredients.get(index).getUnitOfMeasurement());

				intent.putExtras(data);

	            startActivityForResult(intent, 1);
			}
		}); 
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {

			if(resultCode == RESULT_OK){

				if (data.getIntExtra("del", 0) == 1) {
					try {
						((RecipeApplication) getApplication()).getRecipeManager().deleteIngredient(data.getStringExtra("name"));
					} catch (FullFileException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {			        
			        Ingredient ingredient = new Ingredient();
			        ingredient.setName(data.getStringExtra("name"));
			        ingredient.setAmount(data.getDoubleExtra("amount", 0.00));
			        ingredient.setUnitOfMeasurement(data.getStringExtra("unit"));

			        if (((RecipeApplication) getApplication()).getRecipeManager().getIngredientCount(ingredient.getName()) == 0) {
			        
			        	if (data.getIntExtra("type", 0) == 0) {
			        		try {
								((RecipeApplication) getApplication()).getRecipeManager().addIngredient(ingredient);
							} catch (FullFileException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			        	}
			        	else {
			        		try {
								((RecipeApplication) getApplication()).getRecipeManager().updateIngredient(ingredient, ingredients.get(data.getIntExtra("index", 0)).getName());
							} catch (FullFileException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			        	}
			        }
			        else {
			        	Toast.makeText(getApplicationContext(), 
								"Ingredient already exists: Please edit the ingredient or create a new one", Toast.LENGTH_LONG).show();
			        }
				}
		    }
		}
	}
}
