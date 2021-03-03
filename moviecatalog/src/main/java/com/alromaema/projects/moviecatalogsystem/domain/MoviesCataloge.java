package com.alromaema.projects.moviecatalogsystem.domain;

import java.util.Set;
/**
 *  Movie Catalogue Object  
 *
 * @author Waleed Alromaema
 */
public class MoviesCataloge {
	private Set<Movie> movies;

	public Set<Movie> getMovies() {
		return movies;
	}

	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}

	@Override
	public String toString() {
		String allMovies=new String("/n");
		for(Movie mov:movies)
			allMovies+="/n"+mov.toString();
		
		return "MoviesCataloge [movies=" + allMovies + "]";
	}
	

}
