package ca.c301.t03_recipes;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;

import ca.c301.t03_exceptions.NullStringException;
import ca.c301.t03_model.DisplayConverter;
import ca.c301.t03_model.Recipe;
import ca.c301.t03_recipes.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/**
 * Activity used to search recipes
 */
public class SearchActivity extends Activity {

	private ArrayList<Recipe> recipes;
	private DisplayConverter converter;
	private ListView recipeList;
	private EditText keyword;
	private CheckBox onlineCheck;
	private CheckBox offlineCheck;
	private CheckBox ingredientsCheck;
	private CheckBox photoCheck;

	/**
	 * Is responsible for creating the view of the activity,
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		converter = new DisplayConverter();
		recipeList = (ListView) findViewById(R.id.listView_results);

		onlineCheck = (CheckBox) findViewById(R.id.checkBox_online);
		offlineCheck = (CheckBox) findViewById(R.id.checkBox_offline);
		ingredientsCheck = (CheckBox) findViewById(R.id.checkBox_ingredients);
		photoCheck = (CheckBox) findViewById(R.id.checkBox_photos);

		keyword = (EditText) findViewById(R.id.editText_search);

		Button searchButton = (Button) findViewById(R.id.button_search);
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (onlineCheck.isChecked()) {
					try {
						recipes = ((RecipeApplication) getApplication()).getRecipeManager().searchWebForRecipeByName(keyword.getText().toString());
					} catch (ClientProtocolException e) {
					} catch (IOException e) {
						Context context = getApplicationContext();
						CharSequence text = "Unable to Access Internet";
						int duration = Toast.LENGTH_LONG;

						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
					} catch (NullStringException e) {
						Context context = getApplicationContext();
						CharSequence text = "You must input a Search String";
						int duration = Toast.LENGTH_LONG;

						Toast toast = Toast.makeText(context, text, duration);
						toast.show();
						return;
					}
					
					if (ingredientsCheck.isChecked()) {
						recipes = ((RecipeApplication) getApplication()).getRecipeManager().ingredientMatch(recipes, ((RecipeApplication) getApplication()).getIngredientDatabase());
					}
					/*
					if (photoCheck.isChecked()) {
						recipes = ((RecipeApplication) getApplication()).getRecipeManager().photoCheck(recipes);
					}
					*/
					displayResults(1);
				}
				else if (offlineCheck.isChecked()) {
					if ( keyword.getText().toString().equals("") ) {                		
						recipes = ((RecipeApplication) getApplication()).getRecipeDatabase().getAllRecipes();

					}
					else {
						recipes = ((RecipeApplication) getApplication()).getRecipeDatabase().searchRecipes(keyword.getText().toString());
					}
					
					if (ingredientsCheck.isChecked()) {
						recipes = ((RecipeApplication) getApplication()).getRecipeManager().ingredientMatch(recipes, ((RecipeApplication) getApplication()).getIngredientDatabase());
					}
					
					if (photoCheck.isChecked()) {
						recipes = ((RecipeApplication) getApplication()).getRecipeManager().photoCheck(recipes);
					}

					displayResults(0);
				}
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_search, menu);
		return true;
	}

	/**
	 * Is called when this activity resumes
	 * Updates an ArrayAdapter to show search results
	 * If any ingredient is clicked, starts the ViewRecipeActivity
	 */
	@Override
	protected void onResume() {
		super.onResume();

		if (onlineCheck.isChecked()) {
			try {
				recipes = ((RecipeApplication) getApplication()).getRecipeManager().searchWebForRecipeByName(keyword.getText().toString());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				Context context = getApplicationContext();
				CharSequence text = "Unable to Access Internet";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}	catch (NullStringException e) {
				Context context = getApplicationContext();
				CharSequence text = "You must input a Search String";
				int duration = Toast.LENGTH_LONG;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
				return;
			}
			
			if (ingredientsCheck.isChecked()) {
				recipes = ((RecipeApplication) getApplication()).getRecipeManager().ingredientMatch(recipes, ((RecipeApplication) getApplication()).getIngredientDatabase());
			}
			/*
			if (photoCheck.isChecked()) {
				recipes = ((RecipeApplication) getApplication()).getRecipeManager().photoCheck(recipes);
			}
			*/
			displayResults(1);
		}
		else if (offlineCheck.isChecked()) {
			if ( keyword.getText().toString().equals("") ) {        		
				recipes = ((RecipeApplication) getApplication()).getRecipeDatabase().getAllRecipes();
			}

			else {
				recipes = ((RecipeApplication) getApplication()).getRecipeDatabase().searchRecipes(keyword.getText().toString());
			}
			
			if (ingredientsCheck.isChecked()) {
				recipes = ((RecipeApplication) getApplication()).getRecipeManager().ingredientMatch(recipes, ((RecipeApplication) getApplication()).getIngredientDatabase());
			}
			
			if (photoCheck.isChecked()) {
				recipes = ((RecipeApplication) getApplication()).getRecipeManager().photoCheck(recipes);
			}

			displayResults(0);
		}
	}

	/**
	 * Displays results from local search in the list view
	 */
	private void displayResults(final int online) {
		
		if (recipes.isEmpty()) {
			Toast.makeText(getApplicationContext(), "No results", Toast.LENGTH_LONG).show();
		}
		String[] displayList = converter.convertRecipeList(recipes);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchActivity.this,android.R.layout.simple_list_item_1, displayList);
		recipeList.setAdapter(adapter);

		recipeList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int index, long id) {
				Intent intent = new Intent(SearchActivity.this, ViewRecipeActivity.class);

				Bundle data = new Bundle();
				data.putInt("id", recipes.get(index).getId());
				data.putInt("online", online);
				intent.putExtras(data);

				startActivity(intent);
			}
		}); 
	}

}
