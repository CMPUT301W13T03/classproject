package ca.c301.t03_recipes;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import ca.c301.t03_model.Recipe;
import ca.c301.t03_model.RecipePhoto;

/**
 * Activity used to view a recipe's photos
 * 
 * Source: http://mobisoftinfotech.com/android-gallery-widget-example-and-tutorial/
 */
public class PhotoViewerActivity extends Activity {

	private static final String TAG = "PhotoViewerActivity";
	Recipe recipe;

    private ImageView selectedImageView;

    private ImageView leftArrowImageView;

    private ImageView rightArrowImageView;

    private Gallery gallery;

    private int selectedImagePosition = 0;

    private List<Drawable> drawables;

    private GalleryImageAdapter galImageAdapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_photo_viewer);
/*        testImageView = (ImageView) findViewById(R.id.testImageView);
*/
        recipe = (Recipe)getIntent().getSerializableExtra("recipe");
        
        
        getDrawablesList();
        setupUI();
        
/*        RecipePhoto photo = ((ArrayList<RecipePhoto>)recipe.getRecipePhoto()).get(0);
        Log.i(TAG,"file://" + photo.getURI().getPath());
        if(testImageView == null)Log.i(TAG,"imageview null");
        Drawable d = Drawable.createFromPath(photo.getURI().getPath());
        Log.i(TAG,"drawable height: " + d.getIntrinsicHeight());
        testImageView.setImageDrawable(d);*/
        
    }
    private void setupUI() {

        selectedImageView = (ImageView) findViewById(R.id.selected_imageview);
        leftArrowImageView = (ImageView) findViewById(R.id.left_arrow_imageview);
        rightArrowImageView = (ImageView) findViewById(R.id.right_arrow_imageview);
        gallery = (Gallery) findViewById(R.id.gallery);

        leftArrowImageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (selectedImagePosition > 0) {
                    --selectedImagePosition;

                }

                gallery.setSelection(selectedImagePosition, false);
            }
        });

        rightArrowImageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (selectedImagePosition < drawables.size() - 1) {
                    ++selectedImagePosition;

                }

                gallery.setSelection(selectedImagePosition, false);

            }
        });

        gallery.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                selectedImagePosition = pos;

                if (selectedImagePosition > 0 && selectedImagePosition < drawables.size() - 1) {

                    leftArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_left_enabled));
                    rightArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_enabled));

                } else if (selectedImagePosition == 0) {

                    leftArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_left_disabled));

                } else if (selectedImagePosition == drawables.size() - 1) {

                    rightArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_disabled));
                }

                changeBorderForSelectedImage(selectedImagePosition);
                setSelectedImage(selectedImagePosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        galImageAdapter = new GalleryImageAdapter(this, drawables);

        gallery.setAdapter(galImageAdapter);

        if (drawables.size() > 0) {

            gallery.setSelection(selectedImagePosition, false);

        }

        if (drawables.size() == 1) {

            rightArrowImageView.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_disabled));
        }

    }

    private void changeBorderForSelectedImage(int selectedItemPos) {

    	//don't need this.
/*        int count = gallery.getChildCount();

        for (int i = 0; i < count; i++) {

            ImageView imageView = (ImageView) gallery.getChildAt(i);
            imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.image_border));
            imageView.setPadding(3, 3, 3, 3);

        }

        ImageView imageView = (ImageView) gallery.getSelectedView();
        imageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_image_border));
        imageView.setPadding(3, 3, 3, 3);*/
    }

    private void getDrawablesList() {

        drawables = new ArrayList<Drawable>();
        ArrayList<RecipePhoto> photos = (ArrayList<RecipePhoto>) recipe.getRecipePhoto();
        Log.i(TAG, "photo list size = " + photos.size());
        for(int i = 0; i < photos.size(); i++)
        {
        	drawables.add(photos.get(i).getDrawable());
        }



    }

    private void setSelectedImage(int selectedImagePosition) {

        BitmapDrawable bd = (BitmapDrawable) drawables.get(selectedImagePosition);
        Bitmap b = Bitmap.createScaledBitmap(bd.getBitmap(), (int) (bd.getIntrinsicHeight() * 0.9), (int) (bd.getIntrinsicWidth() * 0.7), false);
        selectedImageView.setImageBitmap(b);
        selectedImageView.setScaleType(ScaleType.FIT_XY);

    }
}

