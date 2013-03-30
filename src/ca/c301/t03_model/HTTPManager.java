package ca.c301.t03_model;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Contains functions that handle all web related functionality, most of the adding and searching of recipes is heavily based off of the ESDemo code.
 */
public class HTTPManager {

	private HttpClient httpclient = new DefaultHttpClient();
	private Gson gson = new Gson();

	/**
	 * Sends a recipe to be added to the indicated server
	 * @param recipe is the recipe to be published to the webservice
	 * @param URL is the URL where the recipe is to be stored
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public void addRecipe(Recipe recipe, String URL) throws IllegalStateException, IOException{
		// TODO Implement the server-side ID tracking
		//int id = getID();
		//recipe.setId(id);
		HttpPost httpPost = new HttpPost("http://cmput301.softwareprocess.es:8080/testing/recipezzz/"+recipe.getId());
		StringEntity stringEntity = null;
		try {
			stringEntity = new StringEntity(gson.toJson(recipe));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		httpPost.setHeader("Accept","application/json");

		httpPost.setEntity(stringEntity);
		HttpResponse response = null;
		response = httpclient.execute(httpPost);

		HttpEntity entity = response.getEntity();

		try {
			// May need if statement, check isStreaming();
			entity.consumeContent();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//May possibly need to deallocate more resources here. No 4.0 implementation of releaseconnection();
	}

	/**
	 * Retrieves a specific ID recipe from the server
	 * @param id Is the ID of the recipe to be retrieved
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
	 * Searches web for provided keyword
	 * @param str
	 * 		Provided keyword for search
	 * @return
	 * 		Matching recipes stored in an ArrayList of recipes
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public ArrayList<Recipe> searchRecipes(String str, String URL) throws ClientProtocolException, IOException {
		ArrayList<Recipe> results = new ArrayList<Recipe>();
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
	 * @param response Is the http response to be read
	 * @throws IOException 
	 */
	public String getEntityContent(HttpResponse response) throws IOException {
		BufferedReader br = new BufferedReader(
				new InputStreamReader((response.getEntity().getContent())));
		String output;
		System.err.println("Output from Server -> ");
		String json = "";
		while ((output = br.readLine()) != null) {
			System.err.println(output);
			json += output;
		}
		System.err.println("JSON:"+json);
		return json;
	}
}

