package com.alromaema.projects.moviecatalogsystem.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.alromaema.projects.moviecatalogsystem.domain.Movie;


public interface JdbcDirectorMovieDao {

	List<Integer> findAllMoviesIdsByDirectorId(int id) throws DataAccessException;
	List<Integer> findAllDirectorsIdsByMovieId(int id) throws DataAccessException;
    void save(Integer movieId,Integer directorId) throws DataAccessException;
    void save(Movie movie) throws DataAccessException;
    void deleteByMovieId(Integer movieId) throws DataAccessException;
    void deleteByDirectorId(Integer directorId) throws DataAccessException;
}
