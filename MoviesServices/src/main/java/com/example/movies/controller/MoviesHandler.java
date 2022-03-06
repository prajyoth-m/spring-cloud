package com.example.movies.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.movies.bean.BaseResponse;
import com.example.movies.bean.Movies;
import com.example.movies.repository.MoviesRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@RestController
public class MoviesHandler {

	@Autowired
	MoviesRepository moviesRepo;
	
	@GetMapping("/movies/{name}")
	public Movies moviesByName(@PathVariable String name) {
		return moviesRepo.findById(name).orElse(null);
	}

	@GetMapping("/movies")
	public List<Movies> allMovies() {
		return StreamSupport.stream(moviesRepo.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@PostMapping("/movie/{movie}")
	public BaseResponse insertMovie(@PathVariable String movie) {
		BaseResponse resp = new BaseResponse();
		Movies imdbMovies = getMovieFromIMDB(movie);
		Movies savedMovie = moviesRepo.save(imdbMovies);
		if (null != savedMovie) {
			resp.setSuccess(true);
			resp.setName(savedMovie.getName());
		} else {
			resp.setSuccess(false);
		}
		return resp;
	}
	
	private Movies getMovieFromIMDB(String query) {
		String URL = "http://www.omdbapi.com/?apikey=bcff06d7&t="+query;
		RestTemplate restTemplate = new RestTemplate();
		String responseIMDB = restTemplate.getForEntity(URL, String.class).getBody();
		JsonObject json = new Gson().fromJson(responseIMDB, JsonObject.class);
		Movies movie = new Movies();
		movie.setDescription(json.get("Plot").getAsString());
		movie.setName(json.get("Title").getAsString());
		movie.setDirector(json.get("Director").getAsString());
		movie.setGenre(json.get("Genre").getAsString());
		movie.setImage(json.get("Poster").getAsString());
		movie.setRating(json.get("imdbRating").getAsFloat());
		return movie;
	}

}
