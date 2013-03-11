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

public class HTTPManager {
	// http Connector
	private HttpClient httpclient = new DefaultHttpClient();

	// JSON Utilities
	private Gson gson = new Gson();

	/**
	 * Consumes the POST/Insert operation of the service
	 * @param recipe Is the recipe to be published to the webservice
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	public void addRecipe(Recipe recipe) throws IllegalStateException, IOException{
		// TODO httpPost is currently directed at the testing server, will need to change to "http://cmput301.softwareprocess.es:8080/CMPUT301W13T03/"
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

		//		String status = response.getStatusLine().toString();
		//		System.out.println(status);
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

	public void setID(int id) throws IllegalStateException, IOException{
		// TODO httpPost is currently directed at the testing server, will need to change to "http://cmput301.softwareprocess.es:8080/CMPUT301W13T03/"
		HttpPost httpPost = new HttpPost("http://cmput301.softwareprocess.es:8080/testing/iterator/0");
		StringEntity stringEntity = null;
		try {
			stringEntity = new StringEntity(gson.toJson(id));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		httpPost.setHeader("Accept","application/json");

		httpPost.setEntity(stringEntity);
		HttpResponse response = null;
		try {
			response = httpclient.execute(httpPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//		String status = response.getStatusLine().toString();
		//		System.out.println(status);
		HttpEntity entity = response.getEntity();

		try {
			// May need if statement, check isStreaming();
			entity.consumeContent();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i("HTTPSearch", Integer.toString(getID()));
		//May possibly need to deallocate more resources here. No 4.0 implementation of releaseconnection();
	}

	private int getID() throws ClientProtocolException, IOException {
		int id = 0;
		HttpGet getRequest = new HttpGet("http://cmput301.softwareprocess.es:8080/testing/iterator/0");
		getRequest.addHeader("Accept", "application/json");

		HttpResponse response = httpclient.execute(getRequest);
		String json = getEntityContent(response);

		Type elasticSearchResponseType = new TypeToken<ElasticSearchResponse<Integer>>(){}.getType();

		ElasticSearchResponse<Integer> intResponse = gson.fromJson(json, elasticSearchResponseType);
		id = intResponse.getSource();
		return id;
	}

	/**
	 * Consumes the Get operation of the service
	 * @param id Is the ID of the recipe to be retrieved
	 */
	public Recipe getRecipe(int id){
		Recipe recipe = null;
		try{
			HttpGet getRequest = new HttpGet("http://cmput301.softwareprocess.es:8080/testing/recipezzz/"+id);//S4bRPFsuSwKUDSJImbCE2g?pretty=1

			getRequest.addHeader("Accept","application/json");

			HttpResponse response = httpclient.execute(getRequest);

			//			String status = response.getStatusLine().toString();
			//			System.out.println(status);

			String json = getEntityContent(response);


			// We have to tell GSON what type we expect
			Type elasticSearchResponseType = new TypeToken<ElasticSearchResponse<Recipe>>(){}.getType();
			// Now we expect to get a Recipe response
			ElasticSearchResponse<Recipe> esResponse = gson.fromJson(json, elasticSearchResponseType);
			// We get the recipe from it!
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

	public ArrayList<Recipe> searchRecipes(String str) throws ClientProtocolException, IOException {
		ArrayList<Recipe> results = new ArrayList<Recipe>();
		HttpGet searchRequest = new HttpGet("http://cmput301.softwareprocess.es:8080/testing/recipezzz/_search?pretty=1&q=" +
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

	public void addToInvalid(Recipe recipe) throws ClientProtocolException, IOException{
		HttpPost httpPost = new HttpPost("http://asdoiahspdsdfewdfssdfvcvergedfsljkl.softwareprocess.es:8080/testing/recipezzz/"+recipe.getId());
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

		//		String status = response.getStatusLine().toString();
		//		System.out.println(status);
		HttpEntity entity = response.getEntity();

		try {
			// May need if statement, check isStreaming();
			entity.consumeContent();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

