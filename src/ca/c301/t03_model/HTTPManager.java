package ca.c301.t03_model;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

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
		HttpPost httpPost = new HttpPost("http://cmput301.softwareprocess.es:8080/testing/"+recipe.getId());
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
		BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
		String output;
		System.err.println("Output from Server -> ");
		while ((output = br.readLine()) != null) {
			System.err.println(output);
		}

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
	 * Consumes the Get operation of the service
	 * @param id Is the ID of the recipe to be retrieved
	 */
	public Recipe getRecipe(int id){
		Recipe recipe = null;
		try{
			HttpGet getRequest = new HttpGet("http://cmput301.softwareprocess.es:8080/testing/"+id+"?pretty=1");//S4bRPFsuSwKUDSJImbCE2g?pretty=1

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
