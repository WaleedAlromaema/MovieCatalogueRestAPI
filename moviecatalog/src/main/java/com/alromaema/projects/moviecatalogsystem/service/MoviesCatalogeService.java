package com.alromaema.projects.moviecatalogsystem.service;

import java.util.Collection;
import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.alromaema.projects.moviecatalogsystem.domain.Actor;
import com.alromaema.projects.moviecatalogsystem.domain.Director;
import com.alromaema.projects.moviecatalogsystem.domain.Movie;
/**
 *  Movie Catalogue Service Interface  
 *
 * @author Waleed Alromaema
 */
public interface MoviesCatalogeService {
	
	 
    Collection<Movie> getMoviesCataloge() throws DataAccessException;    
	Movie getMovieById(Integer id) throws DataAccessException;
	Collection<Movie> getAllMoviesByActor(Actor actor) throws DataAccessException;
	Collection<Movie> getAllMoviesByDirector(Director director) throws DataAccessException;
	Collection<Movie> getAllMoviesByGenre(String genre) throws DataAccessException;
   	void saveMovie(Movie movies) throws DataAccessException;
	void saveMovies(Set<Movie> movies) throws DataAccessException;
	void deleteMovie(Movie movie) throws DataAccessException;
	void deleteMovies(Set<Movie> movies) throws DataAccessException;

}
