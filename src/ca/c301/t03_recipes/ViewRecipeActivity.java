package ca.c301.t03_recipes;

import java.util.ArrayList;

import ca.c301.t03_model.DisplayConverter;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ViewRecipeActivity extends Activity {

	private Recipe recipe;
	private DisplayConverter converter;
	
	private ListView ingredientsList;
	private int id;
	
	/**
	 * Is responsible for creating the view of the activity,
	 * Buttons are set here
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_recipe);
		
		Bundle data = getIntent().getExtras();
		id = data.getInt("id");
		
		converter = new DisplayConverter();
		
		ingredientsList = (ListView) findViewById(R.id.listView_ingredients);
		
		Button shareButton = (Button) findViewById(R.id.button_share);
        shareButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                
            }
        });
        
        Button addPictureButton = (Button) findViewById(R.id.button_add_picture);
        addPictureButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ViewRecipeActivity.this, PhotoActivity.class);
                startActivity(intent);
            }
        });
        
        Button editButton = (Button) findViewById(R.id.button_edit);
        editButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(ViewRecipeActivity.this, EditRecipeActivity.class);
                
                Bundle data = new Bundle();
				data.putInt("id", id);
                
				startActivityForResult(intent, 1);
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_view_recipe, menu);
		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		/*
		 * TODO:
		 * - move code into search button listener
		 * - implement search keywords
		 * - implement check boxes for local/web search
		 */
		
		recipe = ((RecipeApplication) getApplication()).getRecipeManager().getLocallySavedRecipeById(id);
		
		String[] displayList = converter.convertIngredientsList(recipe.getIngredients());
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, displayList);
		ingredientsList.setAdapter(adapter);
		
		final TextView nameDisplay = (TextView) findViewById(R.id.textView_name);
		nameDisplay.setText(recipe.getName());
		
		final TextView instructionsDisplay = (TextView) findViewById(R.id.textView_instructions);
		instructionsDisplay.setText(recipe.getInstructions());
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == 1) {

			if(resultCode == RESULT_OK){
				
				if (data.getIntExtra("del", 0) == 1) {
					finish();
				}
			}
		}
	}

}
