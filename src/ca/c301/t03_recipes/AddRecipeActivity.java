package ca.c301.t03_recipes;

import ca.c301.t03_recipes.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AddRecipeActivity extends Activity {
 
	/** 
	 * @uml.property name="recipeApplication"
	 * @uml.associationEnd inverse="addRecipeActivity:ca.c301.t03_recipes.RecipeApplication"
	 */
	private RecipeApplication recipeApplication;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_recipe);
		
		Button saveButton = (Button) findViewById(R.id.button_save);
        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
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
                startActivity(intent);
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

}
