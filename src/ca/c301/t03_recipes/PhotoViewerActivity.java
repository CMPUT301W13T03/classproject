package ca.c301.t03_recipes;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;
import ca.c301.t03_model.Recipe;
import ca.c301.t03_model.RecipePhoto;

public class PhotoViewerActivity extends Activity {

	private static final String TAG = "PhotoViewerActivity";
	ImageView testImageView;
	Recipe recipe;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_photo_viewer);
        testImageView = (ImageView) findViewById(R.id.testImageView);

        int id = getIntent().getIntExtra("id",-1);
        recipe = ((RecipeApplication)getApplication()).getRecipeManager().getLocallySavedRecipeById(id);
        
        RecipePhoto photo = ((ArrayList<RecipePhoto>)recipe.getRecipePhoto()).get(0);
        Log.i(TAG,"file://" + photo.getURI().getPath());
        if(testImageView == null)Log.i(TAG,"imageview null");
        Drawable d = Drawable.createFromPath(photo.getURI().getPath());
        Log.i(TAG,"drawable height: " + d.getIntrinsicHeight());
        testImageView.setImageDrawable(d);
        
    }


    
}
