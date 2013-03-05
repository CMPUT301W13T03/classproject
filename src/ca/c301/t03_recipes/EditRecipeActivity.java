package ca.c301.t03_recipes;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class EditRecipeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_recipe);
		
		Button saveButton = (Button) findViewById(R.id.button_save);
        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	finish();
            }
        });
        
        Button deleteButton = (Button) findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	finish();
            }
        });
        
        Button addPictureButton = (Button) findViewById(R.id.button_add_picture);
        addPictureButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(EditRecipeActivity.this, PhotoActivity.class);
                startActivity(intent);
            }
        });
        
        Button addIngredientButton = (Button) findViewById(R.id.button_add_ingredient);
        addIngredientButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_edit_recipe, menu);
		return true;
	}

}
