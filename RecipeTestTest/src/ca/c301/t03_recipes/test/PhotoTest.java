package ca.c301.t03_recipes.test;

import org.junit.Before;
import org.junit.Test;

import android.provider.MediaStore;

import ca.c301.t03_model.DataManager;
import ca.c301.t03_model.FullFileException;
import ca.c301.t03_model.Recipe;
import ca.c301.t03_model.RecipeManager;
import ca.c301.t03_recipes.MainActivity;

public class PhotoTest extends IntentCatchingTemplate{
	
    private static final String TEST_FILE_NAME = "photo_test_file";
	public PhotoTest(){
    	super(MainActivity.class);
    }
	@Before
	public void setUp() throws Exception{
		//Delete any existing file.
		getActivity().getFileStreamPath(TEST_FILE_NAME).delete();
	}
    @Test
    public void testPhotoIntent(){
    	RecipeManager manager = new RecipeManager(new DataManager(getActivity(),TEST_FILE_NAME));
    	Recipe recipe = new Recipe("Name", "Instructions");
    	try {
			manager.saveRecipe(recipe, getActivity());
		} catch (FullFileException e) {
			e.printStackTrace();
			fail("Full file error.");
		}
    	
    	manager.takePhotoForRecipe(0);
    	assertNotNull(caughtIntent);
    	assertEquals(caughtIntent.getAction(),MediaStore.ACTION_IMAGE_CAPTURE);
    	
    }
    //TODO gotta dive into how this picture stuff actually works so I can test it.

}
