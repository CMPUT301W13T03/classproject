package ca.c301.t03_model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

import ca.c301.t03_exceptions.NullStringException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Contains functions that handle all web related functionality, most of the adding and searching of recipes is heavily based off of the ESDemo code.
 */
public class HTTPManager{

	private HttpClient httpclient = new DefaultHttpClient();
	private Gson gson = new Gson();

	/**
	 * Sends a recipe to be added to the indicated server
	 * 
	 * @param recipe
	 * 				Is the recipe to be published to the webservice
	 * @param URL
	 * 				Is the URL where the recipe is to be stored
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public void addRecipe (Recipe recipe, String URL) throws IllegalStateException, IOException{

		HttpPost httpPost = new HttpPost(URL+recipe.getId());
		StringEntity stringEntity = null;
		Log.i("ID", Integer.toString(recipe.getId()));
		try {
			stringEntity = new StringEntity(gson.toJson(recipe));
		} catch (UnsupportedEncodingException e) {
			return;
		}
		httpPost.setHeader("Accept","application/json");

		httpPost.setEntity(stringEntity);
		//		httpclient.execute(httpPost);
		new SendRecipeTask().execute(httpPost);

	}

	/**
	 * To add the images of the given recipe to the given URL
	 * 
	 * @param recipe
	 * 				Is the recipe containing the images to save
	 * @param IMGURL
	 * 				Is the URL used when saving the images
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public void addImages (Recipe recipe, String IMGURL) throws IllegalStateException, IOException{

		ArrayList<RecipePhoto> photos = new ArrayList<RecipePhoto>(recipe.getRecipePhoto());
		String encoded = null;
		for(int i=0; i<photos.size(); i++){
			encoded = fileToBase64(photos.get(i).getURI().getPath());
			OnlineImage image = new OnlineImage(encoded, photos.get(i).getURI().getPath());
			HttpPost httpPost = new HttpPost(IMGURL+recipe.getId()+"/"+i);
			Log.i("ID", IMGURL+recipe.getId()+"/"+i);
			StringEntity stringEntity = null;
			try {
				stringEntity = new StringEntity(gson.toJson(image));
			} catch (UnsupportedEncodingException e) {
				return;
			}
			httpPost.setHeader("Accept","application/json");

			httpPost.setEntity(stringEntity);

			new SendRecipeTask().execute(httpPost);
		}

	}
	/**
	 * Retrieves a specific ID recipe from the server
	 * 
	 * @param id
	 * 				Is the ID of the recipe to be retrieved
	 * @return
	 * 				The recipe which has the given ID
	 */
	public Recipe getRecipe(int id, String URL){
		Recipe recipe = null;
		try{
			HttpGet getRequest = new HttpGet(URL+id);

			getRequest.addHeader("Accept","application/json");

			HttpResponse response = httpclient.execute(getRequest);

			String json = getEntityContent(response);


			Type elasticSearchResponseType = new TypeToken<ElasticSearchResponse<Recipe>>(){}.getType();
			ElasticSearchResponse<Recipe> esResponse = gson.fromJson(json, elasticSearchResponseType);
			recipe = esResponse.getSource();
			if(response.getEntity() != null){
				response.getEntity().consumeContent();
			}

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
		return recipe;
	}

	/**
	 * To save the images of a given recipe from a given URL to a file
	 * 
	 * @param recipe
	 * 				Is the recipe to get the images
	 * @param IMGURL
	 * 				Is the URL to retrieve from
	 */
	public void getImages(Recipe recipe, String IMGURL) {
		
		OnlineImage image;
		
		for(int i=0; i < recipe.getPhotoCount();i++){
			try{
				HttpGet getRequest = new HttpGet(IMGURL+recipe.getId()+"/"+i);

				getRequest.addHeader("Accept","application/json");

				HttpResponse response = httpclient.execute(getRequest);

				String json = getEntityContent(response);


				Type elasticSearchResponseType = new TypeToken<ElasticSearchResponse<OnlineImage>>(){}.getType();
				ElasticSearchResponse<OnlineImage> esResponse = gson.fromJson(json, elasticSearchResponseType);
				image = esResponse.getSource();
				base64ToFile(image.getPath(), image.getImage());
				
				if(response.getEntity() != null){
					response.getEntity().consumeContent();
				}

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * Searches web for provided keyword
	 * 
	 * @param str
	 * 				Provided keyword for search
	 * @return
	 * 				Matching recipes stored in an ArrayList of recipes
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NullStringException 
	 */
	public ArrayList<Recipe> searchRecipes(String str, String URL) throws ClientProtocolException, IOException, NullStringException {
		ArrayList<Recipe> results = new ArrayList<Recipe>();
		if(str.length() == 0){
			throw new NullStringException();
		}
		HttpGet searchRequest = new HttpGet(URL+"_search?pretty=1&q=" +
				java.net.URLEncoder.encode(str,"UTF-8"));
		searchRequest.setHeader("Accept","application/json");
		HttpResponse response = httpclient.execute(searchRequest);
		String status = response.getStatusLine().toString();
		Log.i("HTTPSearch", status);

		String json = getEntityContent(response);
		Log.i("HTTPSearch", json);

		Type elasticSearchSearchResponseType = new TypeToken<ElasticSearchSearchResponse<Recipe>>(){}.getType();
		ElasticSearchSearchResponse<Recipe> esResponse = gson.fromJson(json, elasticSearchSearchResponseType);
		for (ElasticSearchResponse<Recipe> r : esResponse.getHits()) {
			Recipe recipe = r.getSource();
			results.add(recipe);
		}
		return results;
	}

	/**
	 * Gets the contents of an http response as a String
	 * 
	 * @param response
	 * 				Is the http response to be read
	 * @throws IOException 
	 */
	public String getEntityContent(HttpResponse response) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader((response.getEntity().getContent())));
		String output;
		String json = "";
		while ((output = br.readLine()) != null) {
			System.err.println(output);
			json += output;
		}
		System.err.println("JSON:"+json);
		return json;
	}

	private class SendRecipeTask extends AsyncTask<HttpPost, Integer, HttpResponse>{

		@Override
		protected HttpResponse doInBackground(HttpPost... httpPost) {
			HttpResponse response = null;
			try {
				response = httpclient.execute(httpPost[0]);
			} catch (ClientProtocolException e) {
				Log.i("SendTask", "ClientProtocol");
			} catch (IOException e) {
				Log.i("SendTask", "IOException");
			}
			Log.i("ID", response.getStatusLine().toString());
			return response;
		}
	}
	
	private class OnlineImage{
		String path;
		String image;
		OnlineImage(String image, String path){
			this.image = image;
			this.path = path;
		}
		public String getImage(){
			return this.image;
		}
		public String getPath(){
			return this.path;
		}
	}

	/**
	 * To read a file to a String in base 64
	 * 
	 * @param path
	 * 				The path of the file to convert to base 64
	 * @return
	 * 				The String representation of the file in base 64
	 * @throws IOException
	 */
	public static String fileToBase64(String path) throws IOException {
		byte[] bytes = fileToByteArray(path);
		return Base64.encodeBytes(bytes);
	}

	/**
	 * To read a file to a byte array
	 * 
	 * @param path
	 * 				The path of the file to convert to a byte array
	 * @return
	 * 				The byte array representation of the given file
	 * @throws IOException
	 */
	public static byte[] fileToByteArray(String path) throws IOException {
		File imagefile = new File(path);
		byte[] data = new byte[(int) imagefile.length()];
		FileInputStream fis = new FileInputStream(imagefile);
		fis.read(data);
		fis.close();
		return data;
	}
	
	/**
	 * To save a String in base 64 to a file
	 * 
	 * @param path
	 * 				The path of the file to be saved
	 * @param strBase64
	 * 				The String in base 64 to save
	 * @throws IOException
	 */
	public static void base64ToFile(String path, String strBase64)
			throws IOException {
		byte[] bytes = Base64.decode(strBase64);
		byteArrayTofile(path, bytes);
	}

	/**
	 * To save a byte array as a file
	 * 
	 * @param path
	 * 				The path of the file to be saved
	 * @param bytes
	 * 				The byte array to save
	 * @throws IOException
	 */
	public static void byteArrayTofile(String path, byte[] bytes)
			throws IOException {
		File imagefile = new File(path);
		File dir = new File(imagefile.getParent());
		if (!dir.exists()) {
			dir.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(imagefile);
		fos.write(bytes);
		fos.close();
	}
}

