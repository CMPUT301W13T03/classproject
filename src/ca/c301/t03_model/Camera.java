package ca.c301.t03_model;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

/**
 * Handles camera operations - NOT IMPLEMENTED
 */
public class Camera {

	Uri imageFileUri;
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	/**
	 * Takes photos
	 */
	public void takePhoto(Activity a) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        
        String folder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/tmp";
        File folderF = new File(folder);
        if (!folderF.exists()) {
            folderF.mkdir();
        }
        
        String imageFilePath = folder + "/" + String.valueOf(System.currentTimeMillis()) + "jpg";
        File imageFile = new File(imageFilePath);
        imageFileUri = Uri.fromFile(imageFile);
        
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
        
        //Any activity that calls this must override onActivityResult
        a.startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		
	}

}
