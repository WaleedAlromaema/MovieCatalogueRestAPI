package com.alromaema.projects.moviecatalogsystem.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.alromaema.projects.moviecatalogsystem.domain.Director;
import com.alromaema.projects.moviecatalogsystem.domain.Movie;

public class JdbcDirectorMovieDaoImpl implements JdbcDirectorMovieDao{
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcDirectorDao jdbcDirectorDao;
	
	@Autowired
    public JdbcDirectorMovieDaoImpl(DataSource dataSource,JdbcDirectorDao jdbcDirectorDao) {
    	this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    	this.jdbcDirectorDao=jdbcDirectorDao;
    }
	
	@Override
	public List<Integer> findAllMoviesIdsByDirectorId(int id) throws DataAccessException
	{
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		
		final List<Integer> moviesIds = this.namedParameterJdbcTemplate.query(
				"SELECT MOVIE_ID FROM Director_Participate_Movie WHERE DIRECTOR_ID=:id",
				params,
				new BeanPropertyRowMapper<Integer>() {
					@Override
					public Integer mapRow(ResultSet rs, int row) throws SQLException {
						return rs.getInt(1);
					}
				});
		return moviesIds;
	}
	
	@Override
	public List<Integer> findAllDirectorsIdsByMovieId(int id) throws DataAccessException
	{
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		
		final List<Integer> directorsIds = this.namedParameterJdbcTemplate.query(
				"SELECT DIRECTOR_ID FROM Director_Participate_Movie WHERE MOVIE_ID=:id",
				params,
				new BeanPropertyRowMapper<Integer>() {
					@Override
					public Integer mapRow(ResultSet rs, int row) throws SQLException {
						return rs.getInt(1);
					}
				});
		return directorsIds;
	}

    @Override
	public void save(Integer movieId,Integer directorId) throws DataAccessException
	{
		Map<String, Object> params = new HashMap<>();
		params.put("director_id", directorId);
		params.put("movie_id", movieId);
		if((movieId != null)&&(directorId != null)) {
			this.namedParameterJdbcTemplate.update("INSERT INTO Director_Participate_Movie VALUES (:director_id, :movie_id )", params);
		                           }
	}
    
	@Override
	public void save(Movie movie) throws DataAccessException
	{
		Map<String, Object> params = new HashMap<>();
		
		if(movie.getDirectors()!=null)
		{
		params.put("id", movie.getId());
		this.namedParameterJdbcTemplate.update("DELETE FROM Director_Participate_Movie WHERE MOVIE_ID=:id", params);
		for (Director direct : movie.getDirectors()) {
			if(direct!=null)
			{
				
			params.put("director_id", direct.getId());
			if(!(direct.getId() == null)) {
				this.namedParameterJdbcTemplate.update("INSERT INTO Director_Participate_Movie VALUES (:director_id, :id)", params);
			}
			}
		}
		}
	}
	@Override
	public void deleteByMovieId(Integer movieId) throws DataAccessException
	{
		Map<String, Object> params = new HashMap<>();
		params.put("id", movieId);
		this.namedParameterJdbcTemplate.update("DELETE FROM Director_Participate_Movie WHERE MOVIE_ID=:id", params);
		
	}
	@Override
	public void deleteByDirectorId(Integer directorId) throws DataAccessException
	{
		Map<String, Object> params = new HashMap<>();
		params.put("id", directorId);
		this.namedParameterJdbcTemplate.update("DELETE FROM Director_Participate_Movie WHERE DIRECTOR_ID=:id", params);
		
	}
}
