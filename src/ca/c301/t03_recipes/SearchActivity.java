package ca.c301.t03_recipes;

import java.util.ArrayList;

import ca.c301.t03_model.DisplayConverter;
import ca.c301.t03_model.Recipe;
import ca.c301.t03_recipes.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SearchActivity extends Activity {

	/**
	 * Is responsible for creating the view of the activity,
	 */
	
	private ArrayList<Recipe> recipes;
	private DisplayConverter converter;
	private ListView recipeList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		
		converter = new DisplayConverter();
		recipeList = (ListView) findViewById(R.id.listView_results);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_search, menu);
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		//recipes = ((RecipeApplication) getApplication()).getRecipeManager().getRecipes();
		/*
		ArrayList<Integer> ids = new ArrayList<Integer>();
		
		for (int i = 0; i < recipes.size(); i++) {
			ids.add(recipes.get(i).getId());
		}
		
		String[] displayList = converter.convertRecipeList(ids, (RecipeApplication) getApplication());
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, displayList);
		recipeList.setAdapter(adapter);*/
	}
	
}
