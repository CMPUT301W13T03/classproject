package ca.c301.t03_recipes;

import ca.c301.t03_model.Ingredient;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/*
 * TODO:
 * Add tests for proper input
 */

public class AddIngredientActivity extends Activity {

	private Ingredient ingredient;
	
	private EditText name;
	private EditText amount;
	private EditText unit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_ingredient);
		
		ingredient = new Ingredient();
		
		name = (EditText) findViewById(R.id.editText_ingredient);
		amount = (EditText) findViewById(R.id.editText_amount);
		unit = (EditText) findViewById(R.id.editText_unit_type);
		
		Button saveButton = (Button) findViewById(R.id.button_save);
        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	
            	Intent returnIntent = new Intent();
            	returnIntent.putExtra("name",name.getText().toString());
            	returnIntent.putExtra("amount",Double.valueOf(amount.getText().toString()));
            	returnIntent.putExtra("unit",unit.getText().toString());
            	returnIntent.putExtra("type",0);
            	returnIntent.putExtra("del",0);
            	setResult(RESULT_OK,returnIntent); 
            	
            	finish();
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add_ingredient, menu);
		return true;
	}

}
