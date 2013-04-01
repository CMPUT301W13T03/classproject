package ca.c301.t03_model;

import java.io.File;
import java.io.Serializable;
import java.net.URI;

import android.graphics.drawable.Drawable;
import android.net.Uri;

/**
 * Handles storage of photos for recipes - NOT IMPLEMENTED
 */
public class RecipePhoto implements Serializable{

	File photoFile;
	public RecipePhoto(File f) {
		photoFile = f;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2415081243980986524L;
	public File getFile(){
		return this.photoFile;
	}
	public Uri getURI() {
		return Uri.fromFile(photoFile);
	}
	public Drawable getDrawable() {
		return Drawable.createFromPath(getURI().getPath());
	}

}
