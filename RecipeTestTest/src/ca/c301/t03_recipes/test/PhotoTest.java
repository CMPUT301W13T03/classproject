package ca.c301.t03_recipes.test;

import org.junit.Test;

import android.provider.MediaStore;

import ca.c301.t03_model.Recipe;
import ca.c301.t03_model.RecipeManager;
import ca.c301.t03_recipes.MainActivity;

public class PhotoTest extends IntentCatchingTemplate{
    public PhotoTest(){
    	super(MainActivity.class);
    }
    @Test
    public void testPhotoIntent(){
    	RecipeManager manager = new RecipeManager(getActivity());
    	Recipe recipe = new Recipe("Name", "Instructions");
    	manager.saveRecipe(recipe, getActivity());
    	
    	manager.takePhotoForRecipe(0);
    	assertNotNull(caughtIntent);
    	assertEquals(caughtIntent.getAction(),MediaStore.ACTION_IMAGE_CAPTURE);
    	
    }
    //TODO gotta dive into how this picture stuff actually works so I can test it.

}
