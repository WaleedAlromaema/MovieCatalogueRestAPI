package com.alromaema.projects.moviecatalogsystem.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.alromaema.projects.moviecatalogsystem.domain.Actor;
import com.alromaema.projects.moviecatalogsystem.domain.Movie;

public class JdbcActorMovieDaoImpl implements JdbcActorMovieDao{
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcActorDao jdbcActorDao;
	
	@Autowired
    public JdbcActorMovieDaoImpl(DataSource dataSource,JdbcActorDao jdbcActorDao) {
    	this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    	this.jdbcActorDao=jdbcActorDao;
    }
	@Override
	public List<Integer> findAllMoviesIdsByActorId(int id) throws DataAccessException
	{
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		
		final List<Integer> movieActorsIds = this.namedParameterJdbcTemplate.query(
				"SELECT MOVIE_ID FROM Actor_Participate_Movie WHERE ACTOR_ID=:id",
				params,
				new BeanPropertyRowMapper<Integer>() {
					@Override
					public Integer mapRow(ResultSet rs, int row) throws SQLException {
						return rs.getInt(1);
					}
				});
		return movieActorsIds;
	}
	@Override
	public List<Integer> findAllActorsIdsByMovieId(int id) throws DataAccessException
	{
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		
		final List<Integer> actorsIds = this.namedParameterJdbcTemplate.query(
				"SELECT ACTOR_ID FROM Actor_Participate_Movie WHERE MOVIE_ID=:id",
				params,
				new BeanPropertyRowMapper<Integer>() {
					@Override
					public Integer mapRow(ResultSet rs, int row) throws SQLException {
						return rs.getInt(1);
					}
				});
		return actorsIds;
	}

	
	@Override
	public void save(Integer movieId,Integer actorId) throws DataAccessException
	{
		Map<String, Object> params = new HashMap<>();
		params.put("actor_id", actorId);
		params.put("movie_id", movieId);
		if((movieId != null)&&(actorId != null)) {
			this.namedParameterJdbcTemplate.update("INSERT INTO Actor_Participate_Movie VALUES (:actor_id, :movie_id )", params);
		                           }
	}
	@Override
	@Transactional
	public void save(Movie movie) throws DataAccessException
	{
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> params_act = new HashMap<>();
		
		if(movie.getActors()!=null)
		{
		params.put("id", movie.getId());
		this.namedParameterJdbcTemplate.update("DELETE FROM Actor_Participate_Movie WHERE MOVIE_ID=:id", params);
		for (Actor act : movie.getActors()) {
			if(act!=null)
			{   
				if(act.getId()!=null)
			{
				
				params.put("actor_id", act.getId());
				
				this.namedParameterJdbcTemplate.update("INSERT INTO Actor_Participate_Movie VALUES (:actor_id, :id)", params);
				
			}else {
				params_act.put("lastname", act.getLastName());
				List<Actor> a=null;
				a=(List<Actor>) this.jdbcActorDao.findByLastName(act.getLastName());
			    params.put("actor_id", a.get(0).getId());
				if(!(act.getLastName() == null)) {
					this.namedParameterJdbcTemplate.update("INSERT INTO Actor_Participate_Movie VALUES (:actor_id, :id)", params);
				}
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
		this.namedParameterJdbcTemplate.update("DELETE FROM Actor_Participate_Movie WHERE MOVIE_ID=:id", params);
		
	}
	@Override
	public void deleteByActorId(Integer actorId) throws DataAccessException
	{
		Map<String, Object> params = new HashMap<>();
		params.put("id", actorId);
		this.namedParameterJdbcTemplate.update("DELETE FROM Actor_Participate_Movie WHERE ACTOR_ID=:id", params);
		
	}
}
