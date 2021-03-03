package com.alromaema.projects.moviecatalogsystem.dao;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import com.alromaema.projects.moviecatalogsystem.domain.Actor;
/**
 *  Actor Data Access Object Implementation  
 *
 * @author Waleed Alromaema
 */
@Repository
@Profile("jdbc")
public class JdbcActorDaoImpl implements JdbcActorDao{

	
	 private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	 private SimpleJdbcInsert insertActor;

	   	@Autowired
	    public JdbcActorDaoImpl(DataSource dataSource) {
	   		this.insertActor = new SimpleJdbcInsert(dataSource)
	   	            .withTableName("Actor")
	   	            .usingGeneratedKeyColumns("ID");
	        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	        

	    }

	    @Override
	    public List<Actor> findByLastName(String lastName) throws DataAccessException {
	        Map<String, Object> params = new HashMap<>();
	        params.put("lastName", lastName + "%");
	        List<Actor> actors =null;
	        actors = this.namedParameterJdbcTemplate.query(
	            "SELECT * FROM Actor WHERE LAST_NAME like :lastName",
	            params,
	            BeanPropertyRowMapper.newInstance(Actor.class)
	        );
	        return actors;
	    }

	    @Override
	    public Actor findById(int id) throws DataAccessException {
	        Actor actor=null;
	        try {
	            Map<String, Object> params = new HashMap<>();
	            params.put("id", id);
	            actor = this.namedParameterJdbcTemplate.queryForObject(
	                "SELECT * FROM Actor WHERE ID= :id",
	                params,
	                BeanPropertyRowMapper.newInstance(Actor.class)
	            );
	        } catch (EmptyResultDataAccessException ex) {
	           // throw new ObjectRetrievalFailureException(Actor.class, id);
	        }
	        return actor;
	    }

	    

	    @Override
	    public void save(Actor actor) throws DataAccessException {
	    	BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(actor);
	    	if (actor.isNew()) {
	            Number newKey = this.insertActor.executeAndReturnKey(parameterSource);
	            actor.setId(newKey.intValue());
	        } else {
            	this.namedParameterJdbcTemplate.update(
		                "UPDATE Actor SET FIRST_NAME=:firstName, LAST_NAME=:lastName, NUMBER_MOVIES=:number_movies, " +
		                    "START_YEAR=:startYear, BIRTH_DATE=:birthDate, NATIONALITY=:nationality, GANDER=:gander WHERE ID=:id" ,
		                parameterSource);
	        }
	               
	    }
	    @Override
	    public void save(Set<Actor> actors) throws DataAccessException 
	    {
	    	for(Actor actor :actors)
	    	{
	    		if(actor!=null) 
	    		{
	    	      BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(actor);
	              if (actor.isNew()) 
	              {
	                Number newKey = this.insertActor.executeAndReturnKey(parameterSource);
	                actor.setId(newKey.intValue());
	              } else {
	        	          this.namedParameterJdbcTemplate.update(
		                  "UPDATE Actor SET FIRST_NAME=:firstName, LAST_NAME=:lastName, NUMBER_MOVIES=:number_movies, " +
		                   "START_YEAR=:startYear, BIRTH_DATE=:birthDate, NATIONALITY=:nationality, GANDER=:gander WHERE ID=:id",
		                   parameterSource);
	                      }
	            
	    	     }
	    	}
	    }
	    	    
		@Override
		public Collection<Actor> findAll() throws DataAccessException {
			List<Actor> actors = this.namedParameterJdbcTemplate.query(
		            "SELECT * FROM Actor",
		            new HashMap<String, Object>(),
		            BeanPropertyRowMapper.newInstance(Actor.class));
			System.out.println("Actors from database is ");
			System.out.println(actors);
	        
	        
			return actors;
		}

		@Override
		@Transactional
		public void delete(Actor actor) throws DataAccessException {
			Map<String, Object> actor_params = new HashMap<>();
			actor_params.put("id", actor.getId());
	        this.namedParameterJdbcTemplate.update("DELETE FROM Actor WHERE id=:id", actor_params);
		}



}
