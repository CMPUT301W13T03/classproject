package ca.c301.t03_recipes;

import java.io.File;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ca.c301.t03_exceptions.FullFileException;
import ca.c301.t03_model.Camera;
import ca.c301.t03_model.DisplayConverter;
import ca.c301.t03_model.Recipe;

/**
 * Activity used to view recipes after searching
 */
public class ViewRecipeActivity extends Activity {

	private static final String TAG = "ViewRecipeActivity";
	private Recipe recipe;
	private DisplayConverter converter;
	
	private ListView ingredientsList;
	private int id;
	private int online;
	private File photoFile;
	
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
                //Code for emailing the recipe.
            	new emailDialogFragment().show(getFragmentManager(),"emailDialog");
            }
        });
        Button viewPicturesButton = (Button) findViewById(R.id.button_view_pictures);
        
        viewPicturesButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ViewRecipeActivity.this, PhotoViewerActivity.class);
				intent.putExtra("recipe", recipe);
				
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
            			Log.i(TAG,"Before saving photo list size is: " + recipe.getRecipePhoto().size());
            			Toast.makeText(getApplicationContext(), "Recipe Downloaded", Toast.LENGTH_LONG).show();
						((RecipeApplication) getApplication()).getRecipeManager().saveRecipe(recipe);
						Log.i(TAG,"After saving photo list size is: " + recipe.getRecipePhoto().size());
					} catch (FullFileException e) {
						// TODO Auto-generated catch block
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
	
	/**
	 * Is called when this activity resumes
	 * Updates to show recipe if it was edited
	 * Returns to search if recipe was deleted
	 */
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
	
	/**
	 * Receives 0 if recipe was edited or 1 if it was deleted
	 */
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
	@TargetApi(11)
	public class emailDialogFragment extends DialogFragment{
		EditText emailAddressEntry;
		@TargetApi(11)
		@Override
		  public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the Builder class for convenient dialog construction
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        
	     // Get the layout inflater
	        LayoutInflater inflater = getActivity().getLayoutInflater();

	        // Inflate and set the layout for the dialog
	        // Pass null as the parent view because its going in the dialog layout
	        final View dialogShareView = inflater.inflate(R.layout.dialog_share, null);
	        builder.setView(dialogShareView);
	        emailAddressEntry = (EditText)dialogShareView.findViewById(R.id.emailAddress);

	        builder.setMessage(R.string.dialog_email_hint)
	               .setPositiveButton(R.string.dialog_email_send, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   String emailAddress = ((EditText)dialogShareView.findViewById(R.id.emailAddress)).getText().toString();
	                	   try{
	                	   ((RecipeApplication)getApplication()).getRecipeManager().emailRecipe(emailAddress, recipe, getActivity());
	                	   }
	                	   catch(ActivityNotFoundException e){
	                		   Toast.makeText(getApplicationContext(), "No email app found!", Toast.LENGTH_LONG).show();
	                	   }

	                   }
	               })
	               .setNegativeButton(R.string.dialog_email_cancel, new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   emailDialogFragment.this.getDialog().cancel();
	                   }
	               });
	        // Create the AlertDialog object and return it
	        return builder.create();
	    }

	}
	
}
