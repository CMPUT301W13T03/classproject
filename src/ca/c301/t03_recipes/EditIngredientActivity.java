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
public class EditIngredientActivity extends Activity {

private Ingredient ingredient;
	
	private EditText name;
	private EditText amount;
	private EditText unit;
	private int index;
	
	/**
	 * Is responsible for creating the view of the activity,
	 * Edit texts and buttons are set here
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_ingredient);
		
		Bundle data = getIntent().getExtras();
		index = data.getInt("index");
		
		ingredient = new Ingredient();
		
		name = (EditText) findViewById(R.id.editText_ingredient);
		amount = (EditText) findViewById(R.id.editText_amount);
		unit = (EditText) findViewById(R.id.editText_unit_type);
		
		name.setText(data.getString("name"));
		amount.setText(String.valueOf(data.getDouble("amount")));
		unit.setText(data.getString("unit"));
		
		Button saveButton = (Button) findViewById(R.id.button_save);
        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	
            	if (!name.getText().toString().equals("") && !amount.getText().toString().equals("") &&
            			!unit.getText().toString().equals("")) {
            		
            		Intent returnIntent = new Intent();
                	returnIntent.putExtra("name",name.getText().toString());
                	returnIntent.putExtra("amount",Double.valueOf(amount.getText().toString()));
                	returnIntent.putExtra("unit",unit.getText().toString());
                	returnIntent.putExtra("type",1);
                	returnIntent.putExtra("index", index);
                	returnIntent.putExtra("del",0);
                	setResult(RESULT_OK,returnIntent); 
                	
                	finish();
            	}
            }
        });
        
        Button deleteButton = (Button) findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
            	
            	Intent returnIntent = new Intent();
            	returnIntent.putExtra("del",1);
            	returnIntent.putExtra("index", index);
            	setResult(RESULT_OK,returnIntent); 
            	
            	finish();
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_edit_ingredient, menu);
		return true;
	}

}
