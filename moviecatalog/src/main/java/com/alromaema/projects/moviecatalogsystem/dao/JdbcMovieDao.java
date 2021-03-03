package com.alromaema.projects.moviecatalogsystem.dao;

import org.springframework.dao.DataAccessException;

import com.alromaema.projects.moviecatalogsystem.domain.Actor;
import com.alromaema.projects.moviecatalogsystem.domain.Director;
import com.alromaema.projects.moviecatalogsystem.domain.Movie;

import java.util.Collection;
import java.util.Set;
/**
 *  Movie Data Access Object Interface to JDBC  
 *
 * @author Waleed Alromaema
 */
public interface JdbcMovieDao {
	Collection<Movie> findAll() throws DataAccessException;
	Movie findById(Integer id) throws DataAccessException;
	Collection<Movie> findAllByActor(Actor actor) throws DataAccessException;
	Collection<Movie> findAllByDirector(Director director) throws DataAccessException;
	Collection<Movie> findAllByGenre(String genre) throws DataAccessException;
	void save(Movie movie) throws DataAccessException;
	void save(Set<Movie> movies) throws DataAccessException;
	void delete(Movie movie) throws DataAccessException;
	void delete(Set<Movie> movies) throws DataAccessException;
	}
