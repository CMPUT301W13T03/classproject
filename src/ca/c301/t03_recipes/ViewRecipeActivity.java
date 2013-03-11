package ca.c301.t03_recipes;

import java.util.ArrayList;

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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ViewRecipeActivity extends Activity {

	private Recipe recipe;
	private DisplayConverter converter;
	
	private ListView ingredientsList;
	private int id;
	private int online;
	
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
		online = data.getInt("online");
		
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
        
        Button editDownloadButton = (Button) findViewById(R.id.button_edit_download);
        
        if (online == 1) {
        	editDownloadButton.setText("Download");
        }
        editDownloadButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	if (online == 0) {
            		Intent intent = new Intent(ViewRecipeActivity.this, EditRecipeActivity.class);
                    
                    Bundle data = new Bundle();
    				data.putInt("id", id);
    				intent.putExtras(data);
                    
    				startActivityForResult(intent, 1);
            	}
            	else {
            		
            		// PUT DOWNLOAD CODE HERE
            		//Actually saving code, its downloaded.
            		try {
						((RecipeApplication) getApplication()).getRecipeManager().saveRecipe(recipe, getApplicationContext());
					} catch (FullFileException e) {
						Toast.makeText(getApplicationContext(), "No room on disk to save.", Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}            		
            	}
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
		
		if (online == 0) {
			recipe = ((RecipeApplication) getApplication()).getRecipeManager().getLocallySavedRecipeById(id);
		}
		else {
			// LOAD ONLINE RECIPE HERE
			//TODO getting by localid for now.
			recipe = ((RecipeApplication) getApplication()).getRecipeManager().getSingleRecipe(id);
		}
		
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
