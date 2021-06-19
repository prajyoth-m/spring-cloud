package com.example.movies.bean;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Movies {
	@Id
	private String name;
	private String description;
	private String image;
	private String genre;
	private float rating;
	private String director;
}
