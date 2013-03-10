package ca.c301.t03_recipes;

import java.util.ArrayList;

import ca.c301.t03_model.DisplayConverter;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchActivity extends Activity {

	private ArrayList<Recipe> recipes;
	private ArrayList<Integer> ids;
	private DisplayConverter converter;
	private ListView recipeList;
	private EditText keyword;
	private CheckBox onlineCheck;
	private CheckBox offlineCheck;
	
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
		
		keyword = (EditText) findViewById(R.id.editText_search);
		
		Button searchButton = (Button) findViewById(R.id.button_search);
        searchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	if (onlineCheck.isChecked()) {
                    
                }
            	if (offlineCheck.isChecked()) {
                    if ( 1 == 1 ) {
                    	/*
                		 * TODO:
                		 * - implement search keywords
                		 */
                		
                		recipes = ((RecipeApplication) getApplication()).getRecipeManager().getRecipes();
                		
                		ids = new ArrayList<Integer>();
                		
                		for (int i = 0; i < recipes.size(); i++) {
                			ids.add(recipes.get(i).getId());
                		}
                		
                		displayLocalResults();
                    }
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

	@Override
	protected void onResume() {
		super.onResume();
		
		if (onlineCheck.isChecked()) {
            
        }
    	if (offlineCheck.isChecked()) {
            if ( 1 == 1 ) {
            	/*
        		 * TODO:
        		 * - implement search keywords
        		 */
        		
        		recipes = ((RecipeApplication) getApplication()).getRecipeManager().getRecipes();
        		
        		ids = new ArrayList<Integer>();
        		
        		for (int i = 0; i < recipes.size(); i++) {
        			ids.add(recipes.get(i).getId());
        		}
        		
        		displayLocalResults();
            }
        }
	}
	
	private void displayLocalResults() {
		String[] displayList = converter.convertRecipeList(ids, (RecipeApplication) getApplication());
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchActivity.this,android.R.layout.simple_list_item_1, displayList);
		recipeList.setAdapter(adapter);
		
		recipeList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int index, long id) {
				Intent intent = new Intent(SearchActivity.this, ViewRecipeActivity.class);
					
				Bundle data = new Bundle();
				data.putInt("id", ids.get(index));
					
				intent.putExtras(data);
					
	            startActivity(intent);
			}
		}); 
	}
	
}
