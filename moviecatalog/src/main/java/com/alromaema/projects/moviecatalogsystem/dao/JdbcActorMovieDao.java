package com.alromaema.projects.moviecatalogsystem.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.alromaema.projects.moviecatalogsystem.domain.Movie;


public interface JdbcActorMovieDao {

	List<Integer> findAllMoviesIdsByActorId(int id) throws DataAccessException;
	List<Integer> findAllActorsIdsByMovieId(int id) throws DataAccessException;

    void save(Integer movieId,Integer actorId) throws DataAccessException;
    void save(Movie movie) throws DataAccessException;
    void deleteByMovieId(Integer movieId) throws DataAccessException;
    void deleteByActorId(Integer actorId) throws DataAccessException;
}
