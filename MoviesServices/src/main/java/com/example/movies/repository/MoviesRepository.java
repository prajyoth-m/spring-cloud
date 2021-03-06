package com.example.movies.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.movies.bean.Movies;

@Repository
public interface MoviesRepository extends CrudRepository<Movies, String> {

}
