package ca.c301.t03_model;

import java.io.File;
import java.io.Serializable;
import java.net.URI;

import android.graphics.drawable.Drawable;
import android.net.Uri;

/**
 * Handles storage of photos for recipes
 */
public class RecipePhoto implements Serializable{

	File photoFile;
	
	/**
	 * Constructor given a file containing the photo
	 * 
	 * @param f
	 * 				Is the file containing the photo
	 */
	public RecipePhoto(File f) {
		photoFile = f;
	}

	private static final long serialVersionUID = -2415081243980986524L;
	
	/**
	 * Getter of the file containing the photo
	 * 
	 * @return
	 * 				The file containing the photo
	 */
	public File getFile(){
		return this.photoFile;
	}
	
	/**
	 * Getter of the URI of the photo file
	 * 
	 * @return
	 * 				The URI for the file containing the photo
	 */
	public Uri getURI() {
		return Uri.fromFile(photoFile);
	}
	
	/**
	 * Gets a Drawable object from the photo file
	 * 
	 * @return
	 * 				A Drawable object of the photo file
	 */
	public Drawable getDrawable() {
		return Drawable.createFromPath(getURI().getPath());
	}

}
