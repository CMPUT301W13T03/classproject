package ca.c301.t03_recipes;

import ca.c301.t03_recipes.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button addRecipeButton = (Button) findViewById(R.id.button_add_recipe);
        addRecipeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
                startActivity(intent);
            }
        });
        
        Button searchRecipesButton = (Button) findViewById(R.id.button_search_recipes);
        searchRecipesButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        
        Button viewIngredientsButton = (Button) findViewById(R.id.button_view_ingredients);
        viewIngredientsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(MainActivity.this, IngredientListActivity.class);
                startActivity(intent);
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/**
	 * @uml.property  name="recipeApplication"
	 * @uml.associationEnd  inverse="mainActivity:ca.c301.t03_view.RecipeApplication"
	 */
	private RecipeApplication recipeApplication;

	/**
	 * Getter of the property <tt>recipeApplication</tt>
	 * @return  Returns the recipeApplication.
	 * @uml.property  name="recipeApplication"
	 */
	public RecipeApplication getRecipeApplication() {
		return recipeApplication;
	}

	/**
	 * Setter of the property <tt>recipeApplication</tt>
	 * @param recipeApplication  The recipeApplication to set.
	 * @uml.property  name="recipeApplication"
	 */
	public void setRecipeApplication(RecipeApplication recipeApplication) {
		this.recipeApplication = recipeApplication;
	}

}
