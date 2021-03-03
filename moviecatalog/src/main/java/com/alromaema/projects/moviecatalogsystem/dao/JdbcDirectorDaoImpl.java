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
import org.springframework.stereotype.Repository;

import com.alromaema.projects.moviecatalogsystem.domain.Director;

/**
 *  Director Data Access Object implementation to JDBC  
 *
 * @author Waleed Alromaema
 */
@Repository
@Profile("jdbc")
public class JdbcDirectorDaoImpl implements JdbcDirectorDao{
	
	 private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	 private SimpleJdbcInsert insertDirector;

 @Autowired
 public JdbcDirectorDaoImpl(DataSource dataSource) {

	 this.insertDirector = new SimpleJdbcInsert(dataSource)
	            .withTableName("Director")
	            .usingGeneratedKeyColumns("ID");    
     this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

 }

 @Override
 public Collection<Director> findByLastName(String lastName) throws DataAccessException {
     Map<String, Object> params = new HashMap<>();
     params.put("lastName", lastName + "%");
     List<Director> directors = this.namedParameterJdbcTemplate.query(
         "SELECT * FROM Director WHERE LAST_NAME like :lastName",
         params,
         BeanPropertyRowMapper.newInstance(Director.class)
     );
     return directors;
 }

 @Override
 public Director findById(int id) throws DataAccessException {
     Director director=null;
     try {
         Map<String, Object> params = new HashMap<>();
         params.put("id", id);
         director = this.namedParameterJdbcTemplate.queryForObject(
             "SELECT * FROM Director WHERE ID= :id",
             params,
             BeanPropertyRowMapper.newInstance(Director.class)
         );
     } catch (EmptyResultDataAccessException ex) {
         //throw new ObjectRetrievalFailureException(Director.class, id);
     }
     return director;
 }

 

 @Override
 public void save(Director director) throws DataAccessException {
     BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(director);
     if (director.isNew()) {
         Number newKey = this.insertDirector.executeAndReturnKey(parameterSource);
         director.setId(newKey.intValue());
     } else {
    	 this.namedParameterJdbcTemplate.update(
                 "UPDATE Director SET FIRST_NAME=:firstName, LAST_NAME=:lastName, NUMBER_Directed_MOVIES=:number_directed_movies, " +
                     "START_YEAR=:startYear, BIRTH_DATE=:birthDate, NATIONALITY=:nationality, GANDER=:gander WHERE ID=:id",
                 parameterSource);
     }
         
         
     
 }
 @Override
 public void save(Set<Director> directors) throws DataAccessException 
 {
 	for(Director director :directors)
 	{
 		if(director!=null) 
 		{
 			BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(director);
 		     if (director.isNew()) {
 		         Number newKey = this.insertDirector.executeAndReturnKey(parameterSource);
 		         director.setId(newKey.intValue());
 		     } else {
 		    	 this.namedParameterJdbcTemplate.update(
 		                 "UPDATE Director SET FIRST_NAME=:firstName, LAST_NAME=:lastName, NUMBER_Directed_MOVIES=:number_directed_movies, " +
 		                     "START_YEAR=:startYear, BIRTH_DATE=:birthDate, NATIONALITY=:nationality, GANDER=:gander WHERE ID=:id",
 		                 parameterSource);
 		     }
 		    
 	     }
 	}
 }
 
 	    
	@Override
	public Collection<Director> findAll() throws DataAccessException {
		List<Director> Directors = this.namedParameterJdbcTemplate.query(
	            "SELECT * FROM Director",
	            new HashMap<String, Object>(),
	            BeanPropertyRowMapper.newInstance(Director.class));
		return Directors;
	}

	@Override
	@Transactional
	public void delete(Director director) throws DataAccessException {
		Map<String, Object> director_params = new HashMap<>();
		director_params.put("id", director.getId());
     this.namedParameterJdbcTemplate.update("DELETE FROM owners WHERE id=:id", director_params);
	}



}
