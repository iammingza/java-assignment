package com.wongnai.interview.movie.sync;

import javax.transaction.Transactional;
import javax.validation.constraints.Null;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.external.MovieData;
import com.wongnai.interview.movie.external.MoviesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.external.MovieDataService;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieDataSynchronizer {
	@Autowired
	private MovieDataService movieDataService;

	@Autowired
	private MovieRepository movieRepository;

	@Transactional
	public void forceSync() {
		//TODO: implement this to sync movie into repository
		MoviesResponse data = movieDataService.fetchAll();
		for(int i=0;i<data.size();i++){
			Movie movie = new Movie(data.get(i).getTitle());
			movie.setActors(data.get(i).getCast());
			movieRepository.save(movie);
		}
	}
}
