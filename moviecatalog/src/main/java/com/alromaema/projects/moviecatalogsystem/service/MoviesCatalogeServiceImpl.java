package com.alromaema.projects.moviecatalogsystem.service;

import java.util.Collection;
import java.util.Set;

//import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alromaema.projects.moviecatalogsystem.dao.JdbcActorDao;
import com.alromaema.projects.moviecatalogsystem.dao.JdbcActorMovieDao;
import com.alromaema.projects.moviecatalogsystem.dao.JdbcDirectorDao;
import com.alromaema.projects.moviecatalogsystem.dao.JdbcDirectorMovieDao;
import com.alromaema.projects.moviecatalogsystem.dao.JdbcMovieDao;
import com.alromaema.projects.moviecatalogsystem.domain.Actor;
import com.alromaema.projects.moviecatalogsystem.domain.Director;
import com.alromaema.projects.moviecatalogsystem.domain.Movie;

import org.springframework.transaction.annotation.Transactional;

/**
 *  Movie Catalogue Service Implementation  
 *
 * @author Waleed Alromaema
 */
@Service
public class MoviesCatalogeServiceImpl implements MoviesCatalogeService{
	
	private JdbcMovieDao jdbcMovieDao;
	
	private JdbcActorDao jdbcActorDao;
	
	private JdbcDirectorDao jdbcDirectorDao;
	
	//private JdbcDirectorMovieDao jdbcDirectorMovieDao;
	
    //private JdbcActorMovieDao jdbcActorMovieDao;
	
	
	
	public MoviesCatalogeServiceImpl(JdbcMovieDao jdbcMovieDao,JdbcActorDao jdbcActorDao,JdbcDirectorDao jdbcDirectorDao)//,JdbcDirectorMovieDao jdbcDirectorMovieDao,JdbcActorMovieDao jdbcActorMovieDao)
	{
		this.jdbcMovieDao=jdbcMovieDao;
		this.jdbcActorDao=jdbcActorDao;
		//this.jdbcActorMovieDao=jdbcActorMovieDao;
		this.jdbcDirectorDao=jdbcDirectorDao;
		//this.jdbcDirectorMovieDao=jdbcDirectorMovieDao;
	    
	}
	

	
	@Override
	@Transactional(readOnly = true)
	public Collection<Movie> getMoviesCataloge() throws DataAccessException
	  {
		  return jdbcMovieDao.findAll();	
	  }
    
	@Override
	@Transactional(readOnly = true)
	public Movie getMovieById(Integer id) throws DataAccessException
	  {
		return jdbcMovieDao.findById(id);
	  }
	
	@Override
	@Transactional(readOnly = true)
	public Collection<Movie> getAllMoviesByActor(Actor actor) throws DataAccessException
	{
		return jdbcMovieDao.findAllByActor(actor);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<Movie> getAllMoviesByDirector(Director director) throws DataAccessException
	{
		return jdbcMovieDao.findAllByDirector(director);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<Movie> getAllMoviesByGenre(String genre) throws DataAccessException
	{
		return jdbcMovieDao.findAllByGenre(genre);
	}
	@Override
	@Transactional
	public void saveMovie(Movie movie) throws DataAccessException
	{
		this.jdbcMovieDao.save(movie);
		//this.jdbcActorDao.save(movie.getActors());
		//this.jdbcDirectorDao.save(movie.getDirectors());
		//this.jdbcActorMovieDao.save(movie);
		//this.jdbcDirectorMovieDao.save(movie);
	}
	
	@Override
	@Transactional
	public void saveMovies(Set<Movie> movies) throws DataAccessException
	{   
		if(movies!=null)
		{
		   for(Movie movie :movies)
		   {
			   this.saveMovie(movie);
		   }
		}
		
	}
	
	@Override
	@Transactional
	public void deleteMovie(Movie movie) throws DataAccessException
	{
		
			jdbcMovieDao.delete(movie);
		
	}
	
	@Override
	@Transactional
	public void deleteMovies(Set<Movie> movies) throws DataAccessException
	{
		jdbcMovieDao.delete(movies);
	}
}
