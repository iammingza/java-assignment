package com.wongnai.interview.movie.external;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

@Component
public class MovieDataServiceImpl implements MovieDataService {
	public static final String MOVIE_DATA_URL
			= "https://raw.githubusercontent.com/prust/wikipedia-movie-data/master/movies.json";

	@Autowired
	private RestOperations restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

//	@Override
	public MoviesResponse CollectData(JSONArray jsonArray) throws IOException, JSONException {
		MoviesResponse response = new MoviesResponse();
		JSONArray json = jsonArray;
		for(int i=0;i<json.length();i++) {
			JSONObject obj = json.getJSONObject(i);
			//Get title
			String title = obj.getString("title");
			//Get year
			int year = obj.getInt("year");
			//Get cast
			JSONArray arrcast = obj.getJSONArray("cast");
			ArrayList<String> cast = new ArrayList<String>();
			if (arrcast != null) {
				for (int j = 0; j < arrcast.length(); j++) {
					cast.add(arrcast.get(j).toString());
				}
			}
			//Get genres
			JSONArray arrgenres = obj.getJSONArray("genres");
			ArrayList<String> genres = new ArrayList<String>();
			if (arrgenres != null) {
				for (int j = 0; j < arrgenres.length(); j++) {
					genres.add(arrgenres.get(j).toString());
				}
			}
			MovieData detail = new MovieData();
			detail.setTitle(title);
			detail.setYear(year);
			detail.setCast(cast);
			detail.setGenres(genres);
			response.add(detail);
		}
		return response;
	}
	public MoviesResponse fetchAll() {
		JSONArray jsonArray;
		MoviesResponse response = null;
		try {
			jsonArray = readJsonFromUrl(MOVIE_DATA_URL);
			response = CollectData(jsonArray);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response;
	}
	public static JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONArray json = new JSONArray(jsonText);
			return json;
		} finally {
			is.close();
		}
	}
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
}
