package com.wongnai.interview.movie.search;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wongnai.interview.movie.external.MovieData;
import com.wongnai.interview.movie.external.MoviesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieSearchService;
import com.wongnai.interview.movie.external.MovieDataService;

@Component("simpleMovieSearchService")
public class SimpleMovieSearchService implements MovieSearchService {
	@Autowired
	private MovieDataService movieDataService;

	@Override
	public List<Movie> search(String queryText) {
		//TODO: Step 2 => Implement this method by using data from MovieDataService
		// All test in SimpleMovieSearchServiceIntegrationTest must pass.
		// Please do not change @Component annotation on this class
		List<Movie> res = new ArrayList<>();
		MoviesResponse data = movieDataService.fetchAll();
		for(int i=0;i<data.size();i++){
			if(queryText != "" && CheckString(data.get(i).getTitle(),queryText)){
				Movie movie = new Movie(data.get(i).getTitle());
				movie.setActors(data.get(i).getCast());
				res.add(movie);
			}
		}
		return res;
	}
	public boolean CheckString(String fullString,String querytext){
		boolean checked = false;
		fullString = fullString.toLowerCase();
		querytext = querytext.toLowerCase();
		if(fullString.equals(querytext)){
			checked = false;
		}
		else{
			String pattern = "\\b"+querytext+"\\b";
			Pattern p=Pattern.compile(pattern);
			Matcher m=p.matcher(fullString);
			checked = m.find();
		}
		return checked;
	}

}
