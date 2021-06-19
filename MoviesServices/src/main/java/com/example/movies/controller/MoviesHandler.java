package com.example.movies.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.movies.bean.BaseResponse;
import com.example.movies.bean.Movies;
import com.example.movies.repository.MoviesRepository;

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

	@PostMapping("/movie")
	public BaseResponse insertMovie(@RequestBody Movies movie) {
		BaseResponse resp = new BaseResponse();
		Movies savedMovie = moviesRepo.save(movie);
		if (null != savedMovie) {
			resp.setSuccess(true);
			resp.setName(savedMovie.getName());
		} else {
			resp.setSuccess(false);
		}
		return resp;
	}

}
